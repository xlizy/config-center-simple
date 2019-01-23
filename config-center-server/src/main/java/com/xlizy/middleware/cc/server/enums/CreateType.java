package com.xlizy.middleware.cc.server.enums;

import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import com.xlizy.middleware.cc.server.common.base.EnumCombo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建类型
 * @author xlizy
 * @date 2018/6/6
 */
@Getter
@AllArgsConstructor
public enum CreateType implements BaseValEnum {

    /** 创建类型 */
    AUTOMATIC(0,"自动"),
    MANUALLY(1,"手动");

    private int val;
    private String des;

    public static List<EnumCombo> getCreateTypes(){
        List<EnumCombo> list = new ArrayList<>();
        for (CreateType type : values()) {
            list.add(new EnumCombo(type.name(),type.getDes()));
        }
        return list;
    }

    @Override
    public String toString() {
        return des;
    }
}
