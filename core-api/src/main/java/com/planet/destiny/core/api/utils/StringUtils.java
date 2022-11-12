package com.planet.destiny.core.api.utils;

import com.planet.destiny.core.api.constant.LegacyType;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * Enum타입 찾는중 에러 발생시 해당 Enum값 목록 추출
     * @param e
     * @param <E>
     * @return
     */
    public static <E extends LegacyType> List<String> getEnumValues(Class<E> e) {
        List<String> list = new ArrayList<>();
        for(LegacyType value : e.getEnumConstants()) {
            list.add(value.getCode() + "(" + value.getName() + ")");
        }
        return list;
    }

    public static <E extends Enum> List<String> getNormalEnumValues(Class<E> e) {
        List<String> list = new ArrayList<>();
        for(Enum enu : e.getEnumConstants()) {
            list.add(enu.name());
        }
        return list;
    }

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str) || str.length() == 0);
    }
}
