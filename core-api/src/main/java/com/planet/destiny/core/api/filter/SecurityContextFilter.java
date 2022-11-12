package com.planet.destiny.core.api.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SecurityContextFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_ID__HEADER = "X-Authorization-Id";
    private final String AUTHORIZATION_ROLE_HEADER = "X-Authorization-Role";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String memberId = resolveHeader(AUTHORIZATION_ID__HEADER, request);
        String role = resolveHeader(AUTHORIZATION_ROLE_HEADER, request);

        if(StringUtils.hasText(memberId) && StringUtils.hasText(role)) {
            // SecurityContextHolder에 인증정보 저장
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(role.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            UserDetails principal = new User(memberId, "", authorities);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, "", authorities));
        }
    }

    /**
     * Header에서 특정 값 꺼내오기
     * @param header
     * @param request
     * @return
     */
    private String resolveHeader(String header, HttpServletRequest request) {
        return request.getHeader(header);
    }
}
