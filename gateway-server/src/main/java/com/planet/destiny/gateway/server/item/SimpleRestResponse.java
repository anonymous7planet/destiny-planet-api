package com.planet.destiny.gateway.server.item;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleRestResponse implements Serializable {

    private Integer resultCode; // 결과 코드(0 : 성공, -1 : 실패)
    private String message;     // 결과 메시지
    private Long timestamp;     // 시간
    private ErrorSet error;     // Error 내용

    private SimpleRestResponse(Integer resultCode, String message, ErrorSet error) {
        this.resultCode = resultCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.error = error;
    }

    public static SimpleRestResponse error(String message, ErrorSet error) {
        return new SimpleRestResponse(-1, message, error);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ErrorSet implements Serializable {

        private String error;                   // Error 이름
        private String errorCode;               // Error 코드
        private String errorMessage;            // Error 메시지
        private String path;                    // 요청 URL

        @Builder
        public ErrorSet(String error, String errorCode, String errorMessage, String path) {
            this.error = error;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.path = path;
        }
    }
}
