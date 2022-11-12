package com.planet.destiny.core.api.exception;

import com.planet.destiny.core.api.constant.ErrorCode;

public class NotFoundEnumTypeException extends NotFoundException {

    public NotFoundEnumTypeException() {
        super(ErrorCode.NOT_FOUND_ENUM_VALUE);
    }


    public NotFoundEnumTypeException(String message) {
        super(ErrorCode.NOT_FOUND_ENUM_VALUE, message);
    }

    /**
     * @param value1 요청 값
     * @param value2 Enum 클래스 명
     * @param allowValues value2에 해당하는 enum 클래스 값 목록
     */
    public NotFoundEnumTypeException(String value1, String value2, String allowValues) {
        super(ErrorCode.NOT_FOUND_ENUM_VALUE, "", String.format(ErrorCode.NOT_FOUND_ENUM_VALUE.getDetailMessage(), value1, value2, "허용 값 목록 : " + allowValues));
    }

    /**
     * @param value1 요청 값
     * @param value2 Enum 클래스 명
     */
    public NotFoundEnumTypeException(String value1, String value2) {
        super(ErrorCode.NOT_FOUND_ENUM_VALUE, "", String.format(ErrorCode.NOT_FOUND_ENUM_VALUE.getDetailMessage(), value1, value2, ""));
    }
}
