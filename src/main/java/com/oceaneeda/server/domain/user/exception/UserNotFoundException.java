package com.oceaneeda.server.domain.user.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends OceaneedaException {

    public UserNotFoundException(String email) {
        super(HttpStatus.NOT_FOUND, "USER-404-1", email + "이 이메일인 유저가 존재하지 않습니다.");
    }

    public UserNotFoundException(ObjectId userId) {
        super(HttpStatus.NOT_FOUND, "USER-404-2", userId + "이 아이디인 유저가 존재하지 않습니다.");
    }
}
