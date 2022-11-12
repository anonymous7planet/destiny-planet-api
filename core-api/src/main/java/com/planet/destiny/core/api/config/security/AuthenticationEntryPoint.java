package com.planet.destiny.core.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planet.destiny.core.api.constant.ErrorCode;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(ErrorCode.UNAUTHORIZED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try(OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os,
                    RestEmptyResponse.error("다시 로그인 해주세요."
                            , ErrorSet
                                    .builder()
                                    .errorCode(ErrorCode.UNAUTHORIZED)
                                    .detailMessage("로그인 정보가 없습니다. 다시 로그인 해주세요.")
                                    .path(request.getRequestURI())
                                    .build()
                    )
            );
            os.flush();
        }

    }
}
