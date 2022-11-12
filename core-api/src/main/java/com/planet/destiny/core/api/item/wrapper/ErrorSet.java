package com.planet.destiny.core.api.item.wrapper;

import com.planet.destiny.core.api.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorSet<T extends Serializable> implements Serializable {

    private String name;            // 에러 이름

    private String code;            // Custom Code

    private String message;         // Error 기본 메시지

    private String detailMessage;   // Error 상세 메시지

    private String path;            // 요청 Path

    private T data;                 // 요청 온 데이터

//    public ErrorSet(String name, String code, String message, String detailMessage, String path) {
//        this.name = name;
//        this.code = code;
//        this.message = message;
//        this.detailMessage = detailMessage;
//        this.path = path;
//    }

    @Builder
    public ErrorSet(ErrorCode errorCode, String detailMessage, String path) {
        this.name = errorCode.name();
        this.code = errorCode.getCode();
        this.message = errorCode.getBaseMessage();
        this.detailMessage = detailMessage;
        this.path = path;
    }
}
