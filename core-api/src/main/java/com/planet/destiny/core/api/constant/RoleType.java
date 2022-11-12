package com.planet.destiny.core.api.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.planet.destiny.core.api.exception.NotFoundEnumTypeException;
import com.planet.destiny.core.api.utils.StringUtils;

public enum RoleType implements LegacyType {

    GUEST("GUEST", "로그인 안한 사용자")
    , MEMBER("ROLE_MEMBER", "일반 사용자")
    , MANAGER("ROLE_MANAGER", "매니저")
    , ADMIN("ROLE_ADMIN", "관리자")
    , SUPER_ADMIN("ROLE_SUPER_ADMIN", "최고 관리자")
    ;

    private final String code;
    private final String desc;

    RoleType(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @JsonCreator
    public static RoleType create(String value) {
        if(value.isEmpty()) {
            throw new NotFoundEnumTypeException(value, RoleType.class.getSimpleName());
        }
        for(RoleType roleType : RoleType.values()) {
            if(roleType.name().equalsIgnoreCase(value) || roleType.getCode().equalsIgnoreCase(value)) {
                return roleType;
            }
        }
        throw new NotFoundEnumTypeException(value, RoleType.class.getSimpleName(), String.valueOf(StringUtils.getEnumValues(RoleType.class)));
    }
}
