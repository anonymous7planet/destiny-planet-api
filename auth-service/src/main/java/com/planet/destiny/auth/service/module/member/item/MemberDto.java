package com.planet.destiny.auth.service.module.member.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class MemberDto implements Serializable {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LoginReq implements Serializable {

        private String memberId;
        private String password;

        @Builder
        public LoginReq(String memberId, String password) {
            this.memberId = memberId;
            this.password = password;
        }
    }
}
