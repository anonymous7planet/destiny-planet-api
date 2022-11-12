package com.planet.destiny.auth.service.utils;


import com.planet.destiny.core.api.constant.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SecurityUtils {

    public static String getCurrentMemberIdx() {
        return getAuthentication().getName();
    }

    public static List<RoleType> getCurrentMemberRoles() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        return authorities.stream()
                .map(item -> RoleType.create(item.getAuthority()))
                .collect(Collectors.toList());
    }


    private static Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null || authentication.getAuthorities() == null) {
            return null;
        }

        return authentication;

    }
}
