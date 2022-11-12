package com.planet.destiny.core.api.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.planet.destiny.core.api.exception.NotFoundEnumTypeException;
import com.planet.destiny.core.api.utils.StringUtils;

public enum YesNoType implements LegacyType{

    YES("Y", "YES")
    , NO("N", "NO")
    ;

    private final String code;
    private final String desc;

    YesNoType(final String code, final String desc) {
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
    public static YesNoType create(String value) {
        if(StringUtils.isEmpty(value)) {
            throw new NotFoundEnumTypeException(value, YesNoType.class.getSimpleName());
        }

        for(YesNoType yesNoType : YesNoType.values()) {
            if(yesNoType.name().equalsIgnoreCase(value) || yesNoType.getCode().equalsIgnoreCase(value)) {
                return yesNoType;
            }
        }
        throw new NotFoundEnumTypeException(value, YesNoType.class.getSimpleName(), String.valueOf(StringUtils.getEnumValues(YesNoType.class)));
    }
}
