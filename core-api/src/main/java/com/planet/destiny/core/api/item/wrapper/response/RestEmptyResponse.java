package com.planet.destiny.core.api.item.wrapper.response;


import com.planet.destiny.core.api.constant.ErrorCode;
import com.planet.destiny.core.api.constant.ResultCode;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestEmptyResponse<T> extends RestResponseBase<RestEmptyResponse<T>> implements Serializable  {

    private RestEmptyResponse(ResultCode resultCode, String message, ErrorSet error) {
        super.resultCode = resultCode;
        super.message = message;
        super.error = error;
    }

    public static RestEmptyResponse success(String message) {
        return new RestEmptyResponse(ResultCode.SUCCESS, message, null);
    }

    public static RestEmptyResponse success() {
        return new RestEmptyResponse(ResultCode.SUCCESS, "성공", null);
    }

    /**
     * 에러는 아니고 실패함
     * @param message 실패 사유
     * @return
     */
    public static RestEmptyResponse fail(String message) {
        return new RestEmptyResponse(ResultCode.FAIL, message, null);
    }

    /**
     * ERROR
     * @param message : 경고창에 보여줄 메시지
     * @param error
     * @return
     * @param <T>
     */
    public static <T extends Serializable> RestEmptyResponse<T> error(String message, ErrorSet<T> error) {
        return new RestEmptyResponse<>(ResultCode.ERROR, message, error);
    }

    @Override
    public RestEmptyResponse<T> message(String message) {
        super.message = message;
        return this;
    }
}
