package com.planet.destiny.auth.service.exception.token;

import com.planet.destiny.core.api.constant.ErrorCode;
import com.planet.destiny.core.api.exception.BusinessException;

public class TokenException extends BusinessException {

    public TokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public TokenException(String message) {
        this(ErrorCode.TOKEN_ILLEGAL, message);
    }

    public TokenException(ErrorCode errorCode) {
        this(errorCode, "문제가 발생 했습니다. 다시 로그인 해주세요.");
    }

    public TokenException() {
        this(ErrorCode.TOKEN_ILLEGAL, "문제가 발생 했습니다. 다시 로그인 해주세요.");
    }
}
