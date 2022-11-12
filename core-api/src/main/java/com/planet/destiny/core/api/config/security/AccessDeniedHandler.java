package com.planet.destiny.core.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planet.destiny.core.api.constant.ErrorCode;
import com.planet.destiny.core.api.item.wrapper.ErrorSet;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;
import com.planet.destiny.core.api.utils.ErrorSetUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 필요한 권한이 없이 접근하려 할때 403
        response.setStatus(ErrorCode.FORBIDDEN.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try(OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os,
                    RestEmptyResponse.error("접근 권한이 없습니다."
                            , ErrorSet
                                    .builder()
                                    .errorCode(ErrorCode.FORBIDDEN)
                                    .detailMessage("해당 요청에 대해서 접근 권한이 없습니다.")
                                    .path(request.getRequestURI())
                                    .build()
                    )

            );
            os.flush();
        }
    }
}
