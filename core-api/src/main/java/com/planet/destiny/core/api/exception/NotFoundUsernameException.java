package com.planet.destiny.core.api.exception;

import com.planet.destiny.core.api.constant.ErrorCode;

public class NotFoundUsernameException extends NotFoundException {

    public NotFoundUsernameException() {
        super(ErrorCode.NOT_FOUND_USER, "회원 정보를 찾을 수 없습니다. 다시 확인 해주세요.");
    }

    /**
     * @param username idx/username
     */
    public NotFoundUsernameException(String username) {
        super(ErrorCode.NOT_FOUND_USER, "회원 정보를 찾을 수 없습니다. 다시 확인 해주세요", String.format(ErrorCode.NOT_FOUND_USER.getDetailMessage(), username));
    }
}
