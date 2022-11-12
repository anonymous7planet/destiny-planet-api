package com.planet.destiny.core.api.handler;


import com.planet.destiny.core.api.constant.ErrorCode;
import com.planet.destiny.core.api.exception.BusinessException;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;
import com.planet.destiny.core.api.utils.ErrorSetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<RestEmptyResponse> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("[ handleBusinessException ] e : {}", e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorSetUtils.makeRestEmptyResponse(e, request));
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<RestEmptyResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("[ handlerException ] e : {}", e);
        final String defaultMessage = "문제가 발생 했습니다. 계속 문제가 발생 할 경우 고객센터에 문의해주세요";
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(
                RestEmptyResponse.error(defaultMessage
                        , ErrorSet
                                .builder()
                                        .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                                        .detailMessage(defaultMessage)
                                        .path(request.getRequestURI())
                                .build()
                )
        );
    }
}
