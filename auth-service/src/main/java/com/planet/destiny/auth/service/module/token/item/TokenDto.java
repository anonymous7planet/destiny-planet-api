package com.planet.destiny.auth.service.module.token.item;

import com.planet.destiny.core.api.constant.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

public class TokenDto {


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TokenIssueReq implements Serializable {
        private Long memberIdx;
        private List<RoleType> roles;

        @Builder
        public TokenIssueReq(Long memberIdx, List<RoleType> roles) {
            this.memberIdx = memberIdx;
            this.roles = roles;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TokenReissueReq implements Serializable {
        private String refreshToken;

        @Builder
        public TokenReissueReq(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TokenRes implements Serializable {

        private String grantType;
        private String accessToken;
        private Long accessTokenExpiresIn;
        private String refreshToken;
        private Long refreshTokenExpiresIn;

        @Builder
        public TokenRes(String grantType, String accessToken, Long accessTokenExpiresIn, String refreshToken, Long refreshTokenExpiresIn) {
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
            this.refreshToken = refreshToken;
            this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        }

        public TokenRes setAccessToken(String accessToken, Long accessTokenExpiresIn) {
            this.accessToken = accessToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
            return this;
        }

        public TokenRes setRefreshToken(String refreshToken, Long refreshTokenExpiresIn) {
            this.refreshToken = refreshToken;
            this.refreshTokenExpiresIn = refreshTokenExpiresIn;
            return this;
        }
    }
}
