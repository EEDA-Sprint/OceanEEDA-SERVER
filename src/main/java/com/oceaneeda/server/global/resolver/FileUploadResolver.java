//package com.oceaneeda.server.global.resolver;
//
//import graphql.schema.DataFetchingEnvironment;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//public class FileUploadResolver {
//
//    private static final String BASE_UPLOAD_PATH = "/absolute/path/to/upload/directory";
//
//    public Map<String, String> uploadFile(MultipartFile file, DataFetchingEnvironment env) {
//        try {
//            // 업로드 디렉토리 존재 여부 확인
//            File uploadDir = new File(BASE_UPLOAD_PATH);
//            if (!uploadDir.exists()) {
//                boolean isDirCreated = uploadDir.mkdirs(); // 디렉토리가 없으면 생성
//                if (!isDirCreated) {
//                    throw new RuntimeException("Failed to create upload directory");
//                }
//            }
//
//            // 파일 저장 설정
//            String fileName = file.getOriginalFilename();
//            String filePath = BASE_UPLOAD_PATH + File.separator + fileName;
//            GraphQLUploadResolver
//            // 파일 저장
//            file.transferTo(new File(filePath));
//
//            return Map.of("name", fileName, "path", filePath);
//        } catch (IOException e) {
//            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
//        }
//    }
//}