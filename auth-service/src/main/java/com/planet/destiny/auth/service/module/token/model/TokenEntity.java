package com.planet.destiny.auth.service.module.token.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Getter
@Entity
@Table(schema="destiny_planet_user", name="tb_auth_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenEntity implements Serializable {

    @Id
    @Column(name = "idx", columnDefinition = "VARCHAR(50)", nullable = false, updatable = false)
    private Long idx;

    @Column(name = "member_idx", columnDefinition = "VARCHAR(50)", nullable = false)
    private Long memberIdx;

    @Column(name = "refresh_token", columnDefinition = "VARCHAR(200)",  nullable = false)
    private String refreshToken;

    @Column(name = "refresh_token_expire_time", columnDefinition = "INT")
    private Integer refreshTokenExpireTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Column(name = "refresh_token_expire_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime refreshTokenExpiresIn;

    @Column(name = "creator_idx", columnDefinition="INT")
    private Long creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.INSERT)
    @Column(name = "created_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createDateTime;

    @Column(name = "modifier_idx", columnDefinition="INT")
    private Long modifier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "modified_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDateTime;

    @Builder
    public TokenEntity(Long idx, Long memberIdx, String refreshToken
            , Integer refreshTokenExpireTime, Long refreshTokenExpiresIn, Long creator
            , LocalDateTime createDateTime, Long modifier, LocalDateTime modifyDateTime) {
        this.idx = idx;
        this.memberIdx = memberIdx;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.refreshTokenExpiresIn = LocalDateTime.ofInstant(Instant.ofEpochMilli(refreshTokenExpiresIn), ZoneId.of("Asia/Seoul"));
        this.creator = creator;
        this.createDateTime = createDateTime;
        this.modifier = modifier;
        this.modifyDateTime = modifyDateTime;
    }

    public TokenEntity updateEntity(String refreshToken, Long refreshTokenExpiresIn) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = LocalDateTime.ofInstant(Instant.ofEpochMilli(refreshTokenExpiresIn), ZoneId.of("Asia/Seoul"));
        return this;
    }

}
