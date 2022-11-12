package com.planet.destiny.auth.service.exception.token;

import com.planet.destiny.core.api.constant.ErrorCode;

public class TokenUnsupportedException extends TokenException {
    public TokenUnsupportedException() {
        super(ErrorCode.TOKEN_UNSUPPORTED);
    }

    public TokenUnsupportedException(String message) {
        super(ErrorCode.TOKEN_UNSUPPORTED, message);
    }
}
