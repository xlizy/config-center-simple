package com.xlizy.middleware.cc.server.enums;

import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import com.xlizy.middleware.cc.server.common.base.EnumCombo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义动作类型
 * @author xlizy
 * @date 2018/6/7
 */
@Getter
@AllArgsConstructor
public enum OPType implements BaseValEnum {

    /** 操作类型 */
    GET_APP(1000,"查询应用信息"),
    GET_CLIENT_CONNECTION_INFO(1001,"查询客户端连接信息"),
    GET_APP_ENV_TREE(1002,"查询应用+环境树菜单"),
    GET_ENV(1003,"查询环境信息"),
    GET_ENABLE_LIST(1004,"查询启用状态列表"),
    GET_PROPERTIES_LIST_FOR_PAGE(1005,"分页查询配置信息列表"),
    GET_PROPERTIES(1006,"查询配置信息"),
    GET_APP_ENV_SNAPSHOT_TREE(1007,"查询应用+环境+快照树菜单"),
    GET_SNAPSHOT_DETAILS_FOR_PAGE(1008,"分页查询快照明细信息列表"),

    DEL_APP(2001,"删除应用"),
    DEL_ENV(2002,"删除环境"),
    DEL_PROPERTIES(2003,"删除配置"),
    DEL_SNAPSHOT(2004,"删除快照"),

    ADD_APP(3001,"添加应用"),
    ADD_ENV(3002,"添加环境"),
    ADD_PROPERTIES(3003,"添加配置"),
    ADD_SNAPSHOT(3004,"添加快照"),

    EDIT_APP(4001,"修改应用信息"),
    EDIT_ENV(4002,"修改环境信息"),
    EDIT_PROPERTIES(4003,"修改配置信息"),
    EDIT_PROPERTIES_ENABLE(4004,"修改配置启用状态"),

    CLEAR_SESSION_CACHE(5001,"清空session缓存"),
    CLEAR_DATA_CACHE(5002,"清空数据缓存"),

    UPLOAD_PROPERTIES_DATA(6001,"上传配置数据"),

    DOWNLOAD_PROPERTIES_DATA(7001,"下载配置数据"),

    OTHER_ACCESS_CONFIG_CENTER_PAGE(8001,"访问应用配置管理页面"),
    OTHER_ACCESS_CONFIG_PAGE(8002,"访问应用配置管理页面"),
    OTHER_ACCESS_PROP_TOOL_PAGE(8003,"访问配置文件转换工具页面"),
    OTHER_ACCESS_CLIENT_PAGE(8004,"客户端连接信息监控"),
    OTHER_ACCESS_SNAPSHOT_PAGE(8005,"访问快照管理页面"),
    OTHER_RESTORE_SNAPSHOT(8006,"从快照恢复配置"),

    DEFAULT(0,"默认-由某些功能触发当前操作");

    private int val;
    private String des;

    public static List<EnumCombo> getOPTypes(){
        List<EnumCombo> list = new ArrayList<>();
        for (OPType type : values()) {
            list.add(new EnumCombo(type.name(),type.getDes()));
        }
        return list;
    }

    @Override
    public String toString() {
        return des;
    }
}
