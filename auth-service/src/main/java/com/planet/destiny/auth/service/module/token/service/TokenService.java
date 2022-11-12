package com.planet.destiny.auth.service.module.token.service;


import com.planet.destiny.auth.service.exception.token.TokenException;
import com.planet.destiny.auth.service.module.member.model.MemberEntity;
import com.planet.destiny.auth.service.module.member.repository.MemberRepository;
import com.planet.destiny.auth.service.module.token.item.TokenDto;
import com.planet.destiny.auth.service.module.token.model.TokenEntity;
import com.planet.destiny.auth.service.module.token.repository.TokenRepository;
import com.planet.destiny.auth.service.utils.JwtTokenProvider;
import com.planet.destiny.core.api.constant.RoleType;
import com.planet.destiny.core.api.exception.NotFoundException;
import com.planet.destiny.core.api.exception.NotFoundUsernameException;
import com.planet.destiny.core.api.item.wrapper.response.RestEmptyResponse;
import com.planet.destiny.core.api.item.wrapper.response.RestSingleResponse;
import com.planet.destiny.core.api.utils.ContextUtils;
import com.planet.destiny.core.api.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service(value = "tokenService")
@RequiredArgsConstructor
public class TokenService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private TokenEntity saveToken(TokenEntity tokenEntity) {
        return tokenRepository.save(tokenEntity);
    }

    public RestSingleResponse tokenIssue(TokenDto.TokenIssueReq reqDto) {
        return RestSingleResponse.success("Token 발급에 성공했습니다.", tokenIssueProc(reqDto));
    }

    public TokenDto.TokenRes tokenIssueProc(TokenDto.TokenIssueReq reqDto) {
        LocalDateTime currentTime = LocalDateTime.now();

        TokenDto.TokenRes token = TokenDto.TokenRes.builder().grantType("Bearer").build();
        // AccessToken은 무조건 발급
        jwtTokenProvider.generateAccessToken(Timestamp.valueOf(currentTime).getTime(), reqDto.getMemberIdx(), List.of("ADMIN"), token);

        // TODO: 현재는 중복로그인이 되지 않는데, N개 동시 로그인 가능하도록 변경
        TokenEntity tokenEntity = tokenRepository.findByMemberIdx(reqDto.getMemberIdx()).orElse(null).get(0);
        if(tokenEntity != null && ChronoUnit.DAYS.between(currentTime, tokenEntity.getRefreshTokenExpiresIn()) > 7) {
            return token;
        }

        // refreshToken 만료기간이 7일 이내일 경우우
        jwtTokenProvider.generateRefreshToken(Timestamp.valueOf(currentTime).getTime(), token);

        saveToken(TokenEntity.builder()
                .memberIdx(reqDto.getMemberIdx())
                .refreshToken(token.getRefreshToken())
                .refreshTokenExpiresIn(token.getRefreshTokenExpiresIn())
                .build());
        // RefreshToken httpOnly Cookie 에 저장 시작
        CookieUtils.addCookie(ContextUtils.getResponse(), "refreshToken", token.getRefreshToken(), Integer.parseInt(String.valueOf(jwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME/1000)));

        return token;
    }

    public RestSingleResponse<TokenDto.TokenRes> tokenReIssue(String refreshToken) {
        // 1차 token validation
        jwtTokenProvider.validateToken(refreshToken);

        // 2차 현재 사용 가능 한 token인지 확인(중복 로그인 방지)
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> {
            log.error("다른 기기에서 로그인해서 현재 사용 불가한 refresh token");
            return new TokenException("사용 불가한 RefreshToken 입니다. 다시 로그인 해주세요.");
        });

        MemberEntity member = memberRepository.findByIdx(tokenEntity.getMemberIdx()).orElseThrow(() -> new NotFoundUsernameException());

        return RestSingleResponse.success("Token 재발급에 성공했습니다.", tokenIssueProc(TokenDto.TokenIssueReq.builder().memberIdx(member.getIdx()).roles(List.of(RoleType.ADMIN)).build()));
    }

    public RestEmptyResponse tokenExpires(Long memberIdx) {
//        saveToken(tokenRepository.findByMemberIdx(memberIdx).orElseThrow(() -> new NotFoundException("refreshToken 정보")).updateEntity("", 0L));
        return RestEmptyResponse.success("해당 회원의 Refresh Token을 만료 시켰습니다.");
    }














//
//
//    /**
//     * 로그인시 토큰 발급 받기
//     * @param reqDto
//     * @return
//     */
//    public RestSingleResponse<TokenDto.TokenRes> adminTokenIssue(TokenDto.TokenIssueReq reqDto) {
//        // TODO: 구현해야함
//        return null;
//    }
//
//
//    public RestSingleResponse<TokenDto.TokenRes> adminTokenReissue(TokenDto.TokenReissueReq reqDto) {
//        // 1. token validation(refresh token) - 서명, 기한
//        jwtTokenProvider.validateToken(reqDto.getRefreshToken());
//
//        // 2. DB에 저장되어있는 유효한 토큰인지 검사
//        AdminToken adminToken = adminTokenRepository.findByRefreshToken(reqDto.getRefreshToken()).orElseThrow(() -> {
//            log.error("다른 기기에서 로그인해서 현재 사용 불가능한 Token입니다.");
//            return new TokenIllegalArgumentException();
//        });
//
//        // 3. 회원 정보 검색
//        Admin admin = adminRepository.findByIdx(adminToken.getMemberIdx()).orElseThrow(()-> new NotFoundUsernameException());
//
//        LocalDateTime currentTime = LocalDateTime.now();
//        TokenDto.TokenRes token = TokenDto.TokenRes.builder().grantType(JwtTokenProvider.GRANT_TYPE).build();
//
//        // 4. AccessToken 발급
//        jwtTokenProvider.generateAccessToken(Timestamp.valueOf(currentTime).getTime(), admin.getIdx(), List.of("ADMIN"), token);
//
//        // 5. refresh토큰 갱신 여부(7일 이내로 남았을 경우만 갱신) - 중복로그인 가능
//        if(ChronoUnit.DAYS.between(currentTime, adminToken.getRefreshTokenExpiresIn()) > 7) {
//            return RestSingleResponse.success("토큰 발급에 성공 했습니다", token);
//        }
//
//        // RefreshToken 재발급
//        // refreshToken 만료기간이 7일 이내일 경우
//        jwtTokenProvider.generateRefreshToken(Timestamp.valueOf(currentTime).getTime(), token);
//
//        saveAdminToken(AdminToken.builder().build());
//
//        // HttpOnly Cookie에 refreshToken 저장
//        CookieUtils.addCookie(ContextUtils.getResponse(), "refreshToken", token.getRefreshToken(), Integer.parseInt(String.valueOf(jwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME/1000)));
//
//        return RestSingleResponse.success("토큰 발급에 성공 했습니다.", token);
//    }
//
//    private AdminToken saveAdminToken(AdminToken adminToken) {
//        return adminTokenRepository.save(adminToken);
//    }
}
