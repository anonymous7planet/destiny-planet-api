package com.planet.destiny.core.api.utils;

import com.planet.destiny.core.api.exception.BusinessException;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;

import javax.servlet.http.HttpServletRequest;

public class ErrorSetUtils {
    public static RestEmptyResponse makeRestEmptyResponse(BusinessException e, HttpServletRequest request) {
        return RestEmptyResponse.error(e.getMessage(), makeErrorSet(e, request));
    }

    public static ErrorSet makeErrorSet(BusinessException e, HttpServletRequest request) {
        return ErrorSet.builder()
                .errorCode(e.getErrorCode())
                .detailMessage(e.getDetailMessage())
                .path(request.getRequestURI())
                .build();
    }
}
