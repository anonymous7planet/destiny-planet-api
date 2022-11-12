package com.planet.destiny.auth.service.exception.token;

import com.planet.destiny.core.api.constant.ErrorCode;

public class TokenIllegalArgumentException extends TokenException {

    public TokenIllegalArgumentException() {
        super(ErrorCode.TOKEN_ILLEGAL);
    }

    public TokenIllegalArgumentException(String message) {
        super(ErrorCode.TOKEN_ILLEGAL, message);

    }
}
