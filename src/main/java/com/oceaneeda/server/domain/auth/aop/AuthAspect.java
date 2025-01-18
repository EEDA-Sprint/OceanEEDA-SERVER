package com.oceaneeda.server.domain.auth.aop;

import com.oceaneeda.server.domain.auth.annotation.AdminOnly;
import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.annotation.CheckOwnership;
import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.global.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final AuthRepository authRepository;
    private final MarkingRepository markingRepository;

    @Before("@annotation(authenticated)")
    public void checkAuthenticated(Authenticated authenticated) {
        authRepository.getCurrentUser();
    }

    @Before("@annotation(checkOwnership)")
    public void checkOwnership(JoinPoint joinPoint, CheckOwnership checkOwnership) {
        User currentUser = authRepository.getCurrentUser();
        ObjectId markingId = (ObjectId) joinPoint.getArgs()[0];
        log.warn("체크 오너쉽 : "+markingId);
        log.warn("현재 유저 : "+currentUser.getId());

        if (currentUser.getRole() != Role.ROLE_ADMIN && !isOwner(markingId, currentUser.getId().toHexString())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    @Before("@annotation(adminOnly)")
    public void checkAdmin(AdminOnly adminOnly) {
        User currentUser = authRepository.getCurrentUser();

        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }
    }

    private boolean isOwner(ObjectId markingId, String userId) {
        return markingRepository.existsByIdAndUserId(markingId, userId);
    }
}
