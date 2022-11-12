package com.planet.destiny.core.api.constant;

public enum ResultCode {

    SUCCESS (0, "성공")
    , ERROR(-1, "에러")
    , FAIL(-2, "실패")
    ;

    private final Integer code;
    private final String desc;

    ResultCode(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getName() {
        return this.name();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
