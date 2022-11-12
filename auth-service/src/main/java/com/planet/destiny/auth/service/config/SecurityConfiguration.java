package com.planet.destiny.auth.service.config;

import com.planet.destiny.core.api.config.security.AccessDeniedHandler;
import com.planet.destiny.core.api.config.security.AuthenticationEntryPoint;
import com.planet.destiny.core.api.filter.SecurityContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfiguration(AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 스프링 2.X 버전 부터는 Security설정을 Bean으로 등록하는걸로 변경됨
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * Cross-Site Request Forgery 방지 기능 off
         * 인증된 사용자의 계정을 사용해 악의적인 변경 요청을 만들어 보내는 기법을 방지
         * form기반 서비스가 아닌 REST API 서비스에서는 Disable로 설정
         */
        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()

                // X-Frame-Options이 Http hedaer 추가되어 iframe 외부 프레임 접근이 가능하도록 외부프레임 거부를 해제
                .headers().frameOptions().disable()
                .and()
                .headers().frameOptions().sameOrigin().disable()

                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll로 설정
                .and()
                .authorizeRequests()
                .antMatchers("/v1/api/signin", "/v1/api/signup").permitAll()
                .anyRequest().authenticated() // 나머지 API는 전부 인증 필요

                // Exception Handling
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                // UsernamePasswordAuthenticationFilter.class 전에 CustomFilter를 위치시킨다.
                .addFilterBefore(new SecurityContextFilter(), UsernamePasswordAuthenticationFilter.class)
                ;
        return http.build();
    }

    // TODO: WebConfiguration 설정
}
