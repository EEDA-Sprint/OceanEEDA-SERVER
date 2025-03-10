package com.oceaneeda.server.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exceptions")
public class ExceptionDocController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getExceptionDocsAsHtml() {
        // 1. 모든 예외 클래스 수집
        List<ExceptionInfo> exceptions = collectExceptions();

        // 2. 카테고리별로 그룹화
        Map<String, List<ExceptionInfo>> categorizedExceptions = exceptions.stream()
                .collect(Collectors.groupingBy(ExceptionInfo::category));

        // 3. HTML 생성
        String html = generateHtml(categorizedExceptions);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    private List<ExceptionInfo> collectExceptions() {
        List<ExceptionInfo> result = new ArrayList<>();

        // 모든 예외 클래스 스캔
        Set<Class<? extends OceaneedaException>> exceptionClasses =
                ExceptionScanner.scanExceptions("com.oceaneeda.server");

        // 각 예외 클래스에서 정보 추출
        for (Class<? extends OceaneedaException> exceptionClass : exceptionClasses) {
            try {
                // 기본 생성자 호출을 시도하여 예외 인스턴스 생성
                OceaneedaException instance = createInstance(exceptionClass);
                if (instance != null) {
                    String category = extractCategory(instance.getErrorCode());

                    result.add(new ExceptionInfo(
                            category,
                            instance.getErrorCode(),
                            instance.getStatus(),
                            instance.getMessage(),
                            exceptionClass.getSimpleName()
                    ));
                }
            } catch (Exception e) {
                System.err.println("예외 정보 추출 실패: " + exceptionClass.getName() + ", " + e.getMessage());
            }
        }

        addStandardExceptions(result);

        return result;
    }

    private OceaneedaException createInstance(Class<? extends OceaneedaException> clazz) {
        // 여러 생성자 호출 시도
        try {
            // 1. 기본 생성자 시도
            Constructor<? extends OceaneedaException> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            try {
                // 2. String 파라미터 생성자 시도 (ID가 필요한 경우)
                Constructor<? extends OceaneedaException> constructor =
                        clazz.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                return constructor.newInstance("{identifier}");
            } catch (Exception e2) {
                // 3. Long 파라미터 생성자 시도
                try {
                    Constructor<? extends OceaneedaException> constructor =
                            clazz.getDeclaredConstructor(Long.class);
                    constructor.setAccessible(true);
                    return constructor.newInstance(1L);
                } catch (Exception e3) {
                    // 더 많은 생성자 패턴을 여기에 추가할 수 있음
                    return null;
                }
            }
        }
    }

    private String extractCategory(String errorCode) {
        if (errorCode == null || !errorCode.contains("-")) {
            return "UNKNOWN";
        }
        return errorCode.split("-")[0];
    }

    private String generateHtml(Map<String, List<ExceptionInfo>> categorizedExceptions) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("    <title>OceanEEDA 예외 명세서</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: 'Noto Sans KR', sans-serif; margin: 40px; line-height: 1.6; }\n");
        html.append("        h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }\n");
        html.append("        h2 { color: #3498db; margin-top: 40px; border-bottom: 2px solid #3498db; padding-bottom: 10px; }\n");
        html.append("        table { border-collapse: collapse; width: 100%; margin-bottom: 30px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }\n");
        html.append("        th { background-color: #3498db; color: white; text-align: left; padding: 15px; }\n");
        html.append("        td { border: 1px solid #ddd; padding: 15px; }\n");
        html.append("        tr:nth-child(even) { background-color: #f8f9fa; }\n");
        html.append("        tr:hover { background-color: #e9ecef; }\n");
        html.append("        .code { font-family: monospace; font-weight: bold; }\n");
        html.append("        .status-200 { color: #2ecc71; }\n");
        html.append("        .status-400 { color: #e67e22; }\n");
        html.append("        .status-401, .status-403 { color: #e74c3c; }\n");
        html.append("        .status-404 { color: #f39c12; }\n");
        html.append("        .status-500 { color: #c0392b; }\n");
        html.append("        .class-name { color: #7f8c8d; font-size: 0.8em; font-style: italic; }\n");
        html.append("        pre { background-color: #f8f9fa; padding: 15px; border-radius: 5px; overflow-x: auto; }\n");
        html.append("        .response-format { background-color: #f5f5f5; padding: 20px; border-radius: 5px; margin-top: 50px; }\n");
        html.append("        .response-example { background-color: #f0f8ff; padding: 20px; border-radius: 5px; margin: 20px 0; }\n");
        html.append("        .auth-example { background-color: #fff0f0; padding: 20px; border-radius: 5px; margin: 20px 0; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <h1>OceanEEDA 예외 명세서</h1>\n");

        // 순서 지정 (알파벳 순서로 정렬)
        List<String> categories = new ArrayList<>(categorizedExceptions.keySet());
        Collections.sort(categories);

        for (String category : categories) {
            List<ExceptionInfo> exceptions = categorizedExceptions.get(category);

            html.append("    <h2>").append(category).append(" 카테고리</h2>\n");
            html.append("    <table>\n");
            html.append("        <tr>\n");
            html.append("            <th width=\"20%\">에러 코드</th>\n");
            html.append("            <th width=\"15%\">HTTP 상태</th>\n");
            html.append("            <th width=\"55%\">에러 메시지</th>\n");
            html.append("            <th width=\"10%\">예외 클래스</th>\n");
            html.append("        </tr>\n");

            // 코드 번호 순서로 정렬
            exceptions.sort(Comparator.comparing(ExceptionInfo::errorCode));

            for (ExceptionInfo exception : exceptions) {
                html.append("        <tr>\n");
                html.append("            <td class=\"code\">").append(exception.errorCode()).append("</td>\n");

                // HTTP 상태에 따라 색상 적용
                int statusCode = exception.status().value();
                String statusClass = "status-" + (statusCode / 100) * 100;
                html.append("            <td class=\"").append(statusClass).append("\">");
                html.append(exception.status().value()).append(" ").append(exception.status().name());
                html.append("</td>\n");

                html.append("            <td>").append(exception.message()).append("</td>\n");
                html.append("            <td class=\"class-name\">").append(exception.className()).append("</td>\n");
                html.append("        </tr>\n");
            }

            html.append("    </table>\n");
        }

        // 예외 반환 형식 섹션 추가
        html.append("    <div class=\"response-format\">\n");
        html.append("        <h2>예외 반환 형식</h2>\n");
        html.append("        <p>OceanEEDA API는 GraphQL 형식의 에러 응답을 반환합니다. 모든 에러는 다음과 같은 구조로 반환됩니다.</p>\n");
        html.append("        <pre>{\n");
        html.append("  \"errors\": [\n");
        html.append("    {\n");
        html.append("      \"message\": \"에러 메시지\",\n");
        html.append("      \"locations\": [],\n");
        html.append("      \"path\": [\"요청 경로\"],\n");
        html.append("      \"extensions\": {\n");
        html.append("        \"httpStatusMessage\": \"HTTP 상태 메시지\",\n");
        html.append("        \"errorCode\": \"에러 코드\",\n");
        html.append("        \"httpStatus\": HTTP 상태 코드,\n");
        html.append("        \"classification\": \"예외 분류\"\n");
        html.append("      }\n");
        html.append("    }\n");
        html.append("  ],\n");
        html.append("  \"data\": null\n");
        html.append("}</pre>\n");

        // 일반 예외 반환 예시
        html.append("        <div class=\"response-example\">\n");
        html.append("            <h3>일반 예외 반환 예시</h3>\n");
        html.append("            <h4>1. 인증 예외</h4>\n");
        html.append("            <pre>{\n");
        html.append("  \"errors\": [\n");
        html.append("    {\n");
        html.append("      \"message\": \"유저가 로그인하지 않았습니다.\",\n");
        html.append("      \"locations\": [],\n");
        html.append("      \"path\": [\"createRegion\"],\n");
        html.append("      \"extensions\": {\n");
        html.append("        \"httpStatusMessage\": \"Unauthorized\",\n");
        html.append("        \"errorCode\": \"AUTH-401-5\",\n");
        html.append("        \"httpStatus\": 401,\n");
        html.append("        \"classification\": \"DataFetchingException\"\n");
        html.append("      }\n");
        html.append("    }\n");
        html.append("  ],\n");
        html.append("  \"data\": null\n");
        html.append("}</pre>\n");

        // 유효성 검증 예외 예시
        html.append("            <h4>2. 유효성 검증 예외</h4>\n");
        html.append("            <pre>{\n");
        html.append("  \"errors\": [\n");
        html.append("    {\n");
        html.append("      \"message\": \"입력 값이 올바르지 않습니다: org.springframework.graphql.data.GraphQlArgumentBinder$ArgumentsBindingResult: 1 errors\\nField error in object 'markingInput' on field '$.regionId': rejected value [ㅇ]; codes [typeMismatch.markingInput,typeMismatch]; arguments []; default message [Failed to convert argument value]\",\n");
        html.append("      \"locations\": [],\n");
        html.append("      \"path\": [\"createMarking\"],\n");
        html.append("      \"extensions\": {\n");
        html.append("        \"httpStatusMessage\": \"Bad Request\",\n");
        html.append("        \"errorCode\": \"INVALID_INPUT\",\n");
        html.append("        \"httpStatus\": 400,\n");
        html.append("        \"classification\": \"ValidationError\"\n");
        html.append("      }\n");
        html.append("    }\n");
        html.append("  ],\n");
        html.append("  \"data\": null\n");
        html.append("}</pre>\n");
        html.append("        </div>\n");

        // 인증/인가 예외 반환 섹션
        html.append("        <div class=\"auth-example\">\n");
        html.append("            <h3>인증/인가 관련 예외 반환 예시</h3>\n");
        html.append("            <pre>{\n");
        html.append("  \"errors\": [\n");
        html.append("    {\n");
        html.append("      \"message\": \"토큰이 유효하지 않습니다.\",\n");
        html.append("      \"extensions\": {\n");
        html.append("        \"httpStatus\": 401,\n");
        html.append("        \"errorCode\": \"AUTH-401-2\",\n");
        html.append("        \"httpStatusMessage\": \"Unauthorized\",\n");
        html.append("        \"classification\": \"DataFetchingException\"\n");
        html.append("      }\n");
        html.append("    }\n");
        html.append("  ],\n");
        html.append("  \"data\": null\n");
        html.append("}</pre>\n");
        html.append("            <p>인증/인가 관련 예외의 경우 locations 및 path가 생략될 수 있습니다.</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");

        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }

    private void addStandardExceptions(List<ExceptionInfo> exceptionsList) {
        // StandardExceptionMapping에서 매핑 정보 가져오기
        Map<Class<? extends Exception>, StandardExceptionMapping.ExceptionMappingInfo> mappings =
                StandardExceptionMapping.getMappings();

        // 매핑 정보를 ExceptionInfo 객체로 변환하여 목록에 추가
        for (Map.Entry<Class<? extends Exception>, StandardExceptionMapping.ExceptionMappingInfo> entry : mappings.entrySet()) {
            Class<? extends Exception> exceptionClass = entry.getKey();
            StandardExceptionMapping.ExceptionMappingInfo mappingInfo = entry.getValue();

            exceptionsList.add(new ExceptionInfo(
                    mappingInfo.category(),
                    mappingInfo.errorCode(),
                    mappingInfo.status(),
                    mappingInfo.message(),
                    exceptionClass.getSimpleName()
            ));
        }
    }

    private record ExceptionInfo(
            String category,
            String errorCode,
            HttpStatus status,
            String message,
            String className
    ) {
    }
}