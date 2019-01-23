package com.xlizy.middleware.cc.server.common.utils;


import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import com.xlizy.middleware.cc.server.common.base.EnumCombo;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举相关工具类
 * @author xlizy
 * @date 2018/3/10
 */
public class EnumUtil {

    public static <E extends Enum<?> & BaseValEnum > List<EnumCombo> getCombos(Class<E> enumClass) {
        List<EnumCombo> list = new ArrayList<>();

        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            list.add(new EnumCombo(e.name(),e.getDes()));
        }

        return list;
    }

    /**
     * 支持mybatis枚举字段使用数值类型
     * */
    public static <E extends Enum<?> & BaseValEnum> E valOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getVal() == code){
                return e;
            }
        }
        return null;
    }


}
