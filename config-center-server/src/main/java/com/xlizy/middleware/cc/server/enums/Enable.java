package com.xlizy.middleware.cc.server.enums;

import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import com.xlizy.middleware.cc.server.common.base.EnumCombo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 启用状态
 * @author xlizy
 * @date 2018/5/19
 */
@Getter
@AllArgsConstructor
public enum Enable implements BaseValEnum {

    /** 启用状态 */
    YES(1,"启用"),
    NO(0,"未启用");

    private int val;
    private String des;

    public static List<EnumCombo> getEnables(){
        List<EnumCombo> list = new ArrayList<>();
        for (Enable enable : values()) {
            list.add(new EnumCombo(enable.name(),enable.getDes()));
        }
        return list;
    }

    @Override
    public String toString() {
        return des;
    }
}
