package com.planet.destiny.gateway.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planet.destiny.gateway.server.item.SimpleRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpRequest request  = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        SimpleRestResponse error = null;

        if(ex.getClass() == NotFoundException.class) {
          error = SimpleRestResponse.error("[Gateway] API 정보를 찾을 수 없습니다."
                  , SimpleRestResponse.ErrorSet.builder()
                                  .error(HttpStatus.NOT_FOUND.name())
                                  .errorCode("C401")
                                  .errorMessage("정보를 찾을 수 없습니다.")
                                  .path(request.getPath().value())
                          .build()
          );
          response.setStatusCode(HttpStatus.NOT_FOUND);
        } else {
            error = SimpleRestResponse.error("[Gateway] 알 수 없는 오류가 발생했습니다."
                    , SimpleRestResponse.ErrorSet.builder()
                                    .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                                    .errorCode("S001")
                                    .errorMessage("서버에 에러가 발생 했습니다.")
                                    .path(request.getPath().value())
                            .build()
            );
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        DataBuffer buffer = null;
        try{
            buffer = response.bufferFactory().wrap(new ObjectMapper().writeValueAsBytes(error));

        }catch(Exception e) {
            e.printStackTrace();
        }
        return response.writeWith(Mono.just(buffer));
    }
}
