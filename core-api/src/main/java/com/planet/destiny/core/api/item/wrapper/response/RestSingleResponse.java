package com.planet.destiny.core.api.item.wrapper.response;


import com.planet.destiny.core.api.constant.ResultCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestSingleResponse<T extends Serializable> extends RestResponseBase implements Serializable {

    private T data;

    private RestSingleResponse(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public static <T extends Serializable> RestSingleResponse<T> success(String message, T clazz) {
        return new RestSingleResponse<T>(ResultCode.SUCCESS, message).<T>add(clazz);
    }

    public static <T extends Serializable> RestSingleResponse<T> success(T clazz) {
        return new RestSingleResponse<T>(ResultCode.SUCCESS, "성공").<T>add(clazz);
    }

    public static <T extends Serializable> RestSingleResponse<T> fail(String message, T clazz) {
        return new RestSingleResponse<T>(ResultCode.FAIL, message).<T>add(clazz);
    }

    public static <T extends Serializable> RestSingleResponse<T> fail(T clazz) {
        return new RestSingleResponse<T>(ResultCode.FAIL, "실패").<T>add(clazz);
    }

    private RestSingleResponse<T> add(T data) {
        if(data == null) {
            return this;
        }
        this.data = data;
        return this;
    }

    @Override
    public RestSingleResponse message(String message) {
        super.message = message;
        return this;
    }


}
