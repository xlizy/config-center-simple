package com.xlizy.middleware.cc.server.enums;

import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置推送类型
 * @author xlizy
 * @date 2018/6/7
 */
@Getter
@AllArgsConstructor
public enum SendConfigType implements BaseValEnum {

    /** 配置推送类型 */
    SCHEDULED(0,"定时推送配置"),
    EDIT(1,"配置有修改时推送"),
    INITIATIVE(2,"客户端主动获取配置");

    private int val;
    private String des;

    @Override
    public String toString() {
        return des;
    }
}
