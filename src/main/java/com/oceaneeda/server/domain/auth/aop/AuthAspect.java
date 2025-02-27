package com.oceaneeda.server.domain.auth.aop;

import com.oceaneeda.server.domain.auth.annotation.AdminOnly;
import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.annotation.OwnerOnly;
import com.oceaneeda.server.domain.auth.annotation.PublicOrOwner;
import com.oceaneeda.server.domain.auth.exception.UserNotAdminException;
import com.oceaneeda.server.domain.auth.registry.AccessCheckerRegistry;
import com.oceaneeda.server.domain.auth.registry.OwnerCheckerRegistry;
import com.oceaneeda.server.domain.auth.service.core.AccessChecker;
import com.oceaneeda.server.domain.auth.service.core.OwnerChecker;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final AuthReader authReader;
    private final OwnerCheckerRegistry ownerCheckerRegistry;
    private final AccessCheckerRegistry accessCheckerRegistry;

    @Before("@annotation(authenticated)")
    public void checkAuthenticated(Authenticated authenticated) {
        authReader.getCurrentUser();
    }

    @Before("@annotation(ownerOnly)")
    public void checkOwner(JoinPoint joinPoint, OwnerOnly ownerOnly) {
        User currentUser = authReader.getCurrentUser();
        Object resourceId = joinPoint.getArgs()[0];

        OwnerChecker<Object> ownerChecker = ownerCheckerRegistry.getChecker(ownerOnly.resourceType());

        ownerChecker.checkOwner(resourceId, currentUser);
    }

    @Before("@annotation(publicOrOwner)")
    public void checkPublicOrOwner(JoinPoint joinPoint, PublicOrOwner publicOrOwner) {
        User currentUser = authReader.getCurrentUser();
        Object resourceId = joinPoint.getArgs()[0];

        AccessChecker<Object> accessChecker = accessCheckerRegistry.getChecker(publicOrOwner.resourceType());

        accessChecker.checkAccess(resourceId, currentUser);
    }


    @Before("@annotation(adminOnly)")
    public void checkAdmin(AdminOnly adminOnly) {
        User currentUser = authReader.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            throw new UserNotAdminException();
        }
    }
}
