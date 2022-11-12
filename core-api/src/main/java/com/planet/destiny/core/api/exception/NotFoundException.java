package com.planet.destiny.core.api.exception;

import com.planet.destiny.core.api.constant.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode, String message, String detailMessage) {
        super(errorCode, message, detailMessage);
    }

    public NotFoundException(ErrorCode errorCode, String message) {
        this(errorCode, message, "");
    }

    public NotFoundException(String message) {
        this(ErrorCode.NOT_FOUND, message, "");
    }
    public NotFoundException(ErrorCode errorCode) {
        this(errorCode, "", "");
    }

    public NotFoundException() {
        this(ErrorCode.NOT_FOUND, "정보를 찾을 수 없습니다.", "");
    }
}
