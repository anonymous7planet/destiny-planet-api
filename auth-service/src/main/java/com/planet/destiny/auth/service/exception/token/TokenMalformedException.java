package com.planet.destiny.auth.service.exception.token;

import com.planet.destiny.core.api.constant.ErrorCode;

public class TokenMalformedException extends TokenException{

    public TokenMalformedException() {
        super(ErrorCode.TOKEN_MALFORMED);
    }

    public TokenMalformedException(String message) {
        super(ErrorCode.TOKEN_MALFORMED, message);
    }
}
