package com.planet.destiny.core.api.constant;

public enum ServiceType implements LegacyType{

    DESTINY("D", "소개팅")
    , SHOP("S", "쇼핑몰")
    ;

    private final String code;
    private final String desc;

    ServiceType(final String code, final String desc) {
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
}
