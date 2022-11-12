package com.planet.destiny.gateway.server.util;


import com.planet.destiny.gateway.server.item.SimpleRestResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "role";
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken Validation
     * @param accessToken
     * @return
     */
    public Map<String, String> accessTokenValidation(String accessToken) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return null;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
            errorMap.put("message", "잘못된 JWT 서명입니다. 다시 로그인 해주세요");
            errorMap.put("error", e.getClass().getSimpleName());
            errorMap.put("errorCode", "C002");
            errorMap.put("errorMessage", "해당 토큰은 잘못된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            errorMap.put("message", "만료된 JWT 토큰입니다.");
            errorMap.put("error", e.getClass().getSimpleName());
            errorMap.put("errorCode", "C003");
            errorMap.put("errorMessage", "토큰이 만료 되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
            errorMap.put("message", "지원 되지 않는 JWT 토큰입니다.");
            errorMap.put("error", e.getClass().getSimpleName());
            errorMap.put("errorCode", "C005");
            errorMap.put("errorMessage", "해당 토큰은 지원되지 않는 JWT 토큰입니다..");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
            errorMap.put("message", "JWT 토큰이 잘못되었습니다.");
            errorMap.put("error", e.getClass().getSimpleName());
            errorMap.put("errorCode", "C004");
            errorMap.put("errorMessage", "해당 토큰은 잘못된 JWT 서명입니다.");
        }
        return errorMap;
    }

    /**
     * Claims에서 MemberIdx 추출
     * @param accessToken
     * @return
     */
    public String getMemberIdx(String accessToken) {
        return this.parseClaims(accessToken).getSubject();
    }

    /**
     * Claims에서 Role 추출
     * @param accessToken
     * @return
     */
    public String getRole(String accessToken) {
        // fixme: AUTHORITIES_KEY 값이 존재 하지 않을시 toString과정에서 NullPointerException 발생
        return this.parseClaims(accessToken).get(AUTHORITIES_KEY).toString();
    }


    /**
     * AccessToken에서 Body 추출
     * @param accessToken
     * @return
     */
    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
