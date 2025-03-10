//package com.oceaneeda.server.domain.auth.interceptor;
//
//import com.oceaneeda.server.domain.auth.annotation.CustomChecker;
//import com.oceaneeda.server.domain.auth.annotation.LoginOrNot;
//import com.oceaneeda.server.domain.auth.service.implementation.AuthUpdater;
//import com.oceaneeda.server.domain.auth.util.BearerTokenExtractor;
//import com.oceaneeda.server.domain.auth.util.JwtParser;
//import com.oceaneeda.server.domain.user.domain.User;
//import com.oceaneeda.server.domain.user.service.implementation.UserReader;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.types.ObjectId;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class AuthInterceptor implements HandlerInterceptor {
//
//    private final UserReader userReader;
//    private final AuthUpdater authUpdater;
//    private final JwtParser jwtParser;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        System.out.println("인터셉터 시작");
//        System.out.println("핸들러 클래스: " + handler.getClass().getName());
//        if (handler instanceof HandlerMethod hm) {
//            System.out.println("핸들러 도착");
//            if (hasCustomCheckerAnnotation(hm.getMethod()) || hm.hasMethodAnnotation(LoginOrNot.class)) {
//                String bearer = request.getHeader(AUTHORIZATION);
//
//                if (bearer == null) {
//                    authUpdater.updateCurrentUser(null);
//                } else {
//                    String jwt = BearerTokenExtractor.extract(bearer);
//                    ObjectId userId = jwtParser.getIdFromJwt(jwt);
//
//                    User user = userReader.read(userId);
//
//                    authUpdater.updateCurrentUser(user);
//                }
//            }
//        }
//        return true;
//    }
//
//    private boolean hasCustomCheckerAnnotation(Method method) {
//        for (Annotation annotation : method.getAnnotations()) {
//            System.out.println("인터셉터 어노테이션 : " + annotation.annotationType().getSimpleName());
//            if (AnnotationUtils.findAnnotation(annotation.annotationType(), CustomChecker.class) != null) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
