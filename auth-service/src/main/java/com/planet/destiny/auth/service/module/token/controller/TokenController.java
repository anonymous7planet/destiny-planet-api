package com.planet.destiny.auth.service.module.token.controller;


import com.planet.destiny.auth.service.exception.token.TokenException;
import com.planet.destiny.auth.service.module.token.item.TokenDto;
import com.planet.destiny.auth.service.module.token.service.TokenService;
import com.planet.destiny.core.api.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.StringUtils;

@Slf4j
@RequestMapping(value = "/v1/api/token")
@RestController(value = "tokenController")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;


    // TODO: CookieValue를 Dto로 받을 수 있는지 확인 필요
    @PostMapping("/admin/re-issue")
    public ResponseEntity tokenReissue(@CookieValue(value = "refreshToken")TokenDto.TokenReissueReq reqDto) {
        if(StringUtils.isEmpty(reqDto.getRefreshToken())) {
            throw new TokenException(ErrorCode.TOKEN_NOT_EXISTS);
        }

        return ResponseEntity.ok(tokenService.adminTokenReissue(reqDto));
    }

}
