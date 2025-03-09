package com.oceaneeda.server.domain.auth.aop;

import com.oceaneeda.server.domain.auth.annotation.*;
import com.oceaneeda.server.domain.auth.exception.UserNotAdminException;
import com.oceaneeda.server.domain.auth.registry.AccessCheckerRegistry;
import com.oceaneeda.server.domain.auth.registry.FieldCheckerRegistry;
import com.oceaneeda.server.domain.auth.registry.OwnerCheckerRegistry;
import com.oceaneeda.server.domain.auth.service.core.AccessChecker;
import com.oceaneeda.server.domain.auth.service.core.FieldChecker;
import com.oceaneeda.server.domain.auth.service.core.OwnerChecker;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.auth.util.JoinPointArgumentExtractor;
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
    private final FieldCheckerRegistry fieldCheckerRegistry;

    @Before("@annotation(authenticated)")
    public void checkAuthenticated(Authenticated authenticated) {
        authReader.getCurrentUser();
    }

    @Before("@annotation(ownerOnly)")
    public void checkOwner(JoinPoint joinPoint, OwnerOnly ownerOnly) {
        User currentUser = authReader.getCurrentUser();
        Object resourceId = JoinPointArgumentExtractor.findArgumentByObjectId(joinPoint);

        OwnerChecker<Object> ownerChecker = ownerCheckerRegistry.getChecker(ownerOnly.resourceType());

        ownerChecker.checkOwner(resourceId, currentUser);
    }

    @Before("@annotation(publicOrOwner)")
    public void checkPublicOrOwner(JoinPoint joinPoint, PublicOrOwner publicOrOwner) {
        User currentUser = authReader.getCurrentUser();
        Object resourceId = JoinPointArgumentExtractor.findArgumentByObjectId(joinPoint);

        AccessChecker<Object> accessChecker = accessCheckerRegistry.getChecker(publicOrOwner.resourceType());

        accessChecker.checkAccess(resourceId, currentUser);
    }

    @Before("@annotation(fieldPermission)")
    public void checkFieldPermission(JoinPoint joinPoint, FieldPermission fieldPermission) {
        User currentUser = authReader.getCurrentUser();
        Object inputObject = JoinPointArgumentExtractor.findArgumentByType(joinPoint, fieldPermission.inputType());

        FieldChecker<Object> checker = fieldCheckerRegistry.getChecker(fieldPermission.inputType(), fieldPermission.fieldName());

        checker.checkField(inputObject, currentUser);
    }


    @Before("@annotation(adminOnly)")
    public void checkAdmin(AdminOnly adminOnly) {
        User currentUser = authReader.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            throw new UserNotAdminException();
        }
    }
}
