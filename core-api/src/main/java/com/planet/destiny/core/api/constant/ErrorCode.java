package com.planet.destiny.core.api.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Client Error
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청입니다.", "")

    , TOKEN_NOT_EXISTS(HttpStatus.BAD_REQUEST, "C002", "토큰이 존재 하지 않습니다.", "다시 로그인해주세요.")
    , TOKEN_ILLEGAL(HttpStatus.BAD_REQUEST, "C003", "해당 토큰은 잘못 된 토큰입니다.", "다시 로그인해주세요.")
    , TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "C004", "해당 토큰은 만료된 토큰입니다.", "다시 로그인해주세요.")
    , TOKEN_MALFORMED(HttpStatus.BAD_REQUEST, "C005", "해당 토큰은 잘못된 JWT 서명입니다.", "다시 로그인해주세요.")
    , TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, "C006", "해당 토큰은 지원 되지 않는 JWT 토큰입니다.", "다시 로그인해주세요.")

    , UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C101", "인증이 필한 요청입니다", "")

    , FORBIDDEN(HttpStatus.FORBIDDEN, "C201", "권한이 없는 요청입니다.", "")

    , NOT_FOUND(HttpStatus.NOT_FOUND, "C401", "정보를 찾을 수 없습니다.", "")
    , NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "C402", "자원 정보를 찾을 수 없습니다.", "%s 정보를 찾을 수 없습니다.")
    , NOT_FOUND_USER(HttpStatus.NOT_FOUND, "C402", "회원 정보를 찾을 수 없습니다.", "[%s] 회원 정보를 찾을 수 없습니다.")
    , NOT_FOUND_ENUM_VALUE(HttpStatus.NOT_FOUND, "C403", "타입 정보를 찾을 수 없습니다.", "%s는 %s 에서 지원 하지 않는 값입니다. %s")

    , METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C501", "허용 하지 않는 요청입니다.", "")

    // Server Error
    , INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버에 문제가 발생했습니다.", "")

    , BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "S201", "잘못된 응답을 수신했습니다.", "")

    , SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "S301", "서버 작업을 진행 하고 있습니다.", "")

    , GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "S401", "", "")

    ;

    private final HttpStatus status;
    private final String code;
    private final String baseMessage;
    private final String detailMessage;


    ErrorCode(final HttpStatus status, final String code, final String baseMessage, final String detailMessage) {
        this.status = status;
        this.code = code;
        this.baseMessage = baseMessage;
        this.detailMessage = detailMessage;
    }

    public String getName() {
        return this.name();
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getBaseMessage() {
        return this.baseMessage;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }


}
