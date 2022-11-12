package com.planet.destiny.core.api.item.wrapper.response;


import com.planet.destiny.core.api.constant.ResultCode;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import lombok.Getter;

@Getter
public abstract class RestResponseBase<T> {
    protected ResultCode resultCode;

    protected String message;   // 에러 일경우, Alert 창에 나타낼 메시지

    protected Long timestamp = System.currentTimeMillis();

    protected ErrorSet error;

    public abstract T message(String message);

}
