package com.oceaneeda.server.domain.region.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

public class RegionNotFoundException extends OceaneedaException {
    public RegionNotFoundException(ObjectId regionId) {
        super(HttpStatus.NOT_FOUND, "REGION-404-1", regionId + "이 아이디인 지역이 존재하지 않습니다.");
    }
}
