package com.planet.destiny.auth.service.handler;

import com.planet.destiny.auth.service.exception.token.TokenException;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;
import com.planet.destiny.core.api.utils.ErrorSetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = TokenException.class)
    protected ResponseEntity<RestEmptyResponse> handleJwtTokenException(TokenException e, HttpServletRequest request) {
        log.error("[ handleJwtTokenException ] e : {}", e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorSetUtils.makeRestEmptyResponse(e, request));
    }
}
