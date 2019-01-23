package com.xlizy.middleware.cc.server.common.base;

/**
 * 如果枚举值是数字类型，可以实现此接口
 * @author xlizy
 * @date 2018/3/10
 */
public interface BaseValEnum {

    /**
     * 获取枚举的数值
     * 本系统枚举类型对应的数据库字段都是tinyint
     * @return int
     * */
    int getVal();

    /**
     * 获取枚举的描述
     * @return String
     * */
    String getDes();
}
