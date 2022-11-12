package com.planet.destiny.auth.service.exception.token;

import com.planet.destiny.core.api.constant.ErrorCode;

public class TokenExpiredException extends TokenException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }

    public TokenExpiredException(String message) {
        super(ErrorCode.TOKEN_EXPIRED, message);
    }
}
