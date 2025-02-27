package com.oceaneeda.server.domain.region.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class RegionNameAlreadyExistException extends OceaneedaException {
    public RegionNameAlreadyExistException(String name) {
        super(HttpStatus.CONFLICT, "REGION-409-1", name + "이 지역명인 지역이 이미 존재합니다.");
    }
}
