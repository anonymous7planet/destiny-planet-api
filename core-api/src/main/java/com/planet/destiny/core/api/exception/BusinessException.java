package com.planet.destiny.core.api.exception;

import com.planet.destiny.core.api.constant.ErrorCode;

public class BusinessException extends RuntimeException{

    protected ErrorCode errorCode;
    protected String message;       // 경고창에 보여줄 메시지
    protected String detailMessage; // ErrorSet에서 상세 메시지

    /**
     *
     * @param errorCode 에러 코드 정보
     * @param message 경고창에 보여줄 메시지
     * @param detailMessage ErrorSet에서 상세 메시지
     */
    public BusinessException(ErrorCode errorCode, String message, String detailMessage) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode, message, "");
    }

    public BusinessException(String message, String detailMessage) {
        this(ErrorCode.INTERNAL_SERVER_ERROR, message, detailMessage);
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, "문제가 발생 했습니다. 잠시 후 다시 시도해주세요");
    }

    public BusinessException(String message) {
        this(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }


    public BusinessException() {
        this(ErrorCode.INTERNAL_SERVER_ERROR, "문제가 발생 했습니다. 잠시 후 다시 시도해주세요");
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

}
