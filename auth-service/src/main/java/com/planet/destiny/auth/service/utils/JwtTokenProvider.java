package com.planet.destiny.auth.service.utils;


import com.planet.destiny.auth.service.exception.token.*;
import com.planet.destiny.auth.service.module.token.item.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component(value = "jwtTokenProvider")
public class JwtTokenProvider {

    public static final String AUTHORITIES_KEY = "role";
    public static final String GRANT_TYPE = "Bearer";

    public static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;            // AccessToken 만료 시간(30분)

    public static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30L; // RefreshToken 만료 시간(30일)

    private final Key key;


    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto.TokenRes generateAccessToken(long now, Long memberIdx, List<String> roles, TokenDto.TokenRes token) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberIdx));

        claims.put(AUTHORITIES_KEY, roles.stream().collect(Collectors.joining(",")));
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return token.setAccessToken(accessToken, expiration.getTime());
    }

    public TokenDto.TokenRes generateRefreshToken(long now, TokenDto.TokenRes token) {
        Claims claims = Jwts.claims();
        Date expiration = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return token.setRefreshToken(refreshToken, expiration.getTime());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if(claims == null || claims.get(AUTHORITIES_KEY) == null) {
            throw new TokenIllegalArgumentException();
        }

        // 클레임에서 권한 정보 가져 오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) throws TokenException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
            throw new TokenMalformedException();
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            throw new TokenExpiredException();
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
            throw new TokenUnsupportedException();
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
            throw new TokenIllegalArgumentException();
        }
    }

    public String getMemberIdx(String accessToken) {
        return this.parseClaims(accessToken).getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch(Exception e) {
            return null;
        }
    }

}
