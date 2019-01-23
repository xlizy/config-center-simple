package com.xlizy.middleware.cc.server.service;

import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.entity.CcApp;

import java.util.List;

/**
 * 应用服务接口
 * @author xlizy
 * @date 2018/5/19
 */
public interface AppService {

    /**
     * 添加应用
     * @param app 应用对象
     * @return
     * */
    Results addApp(CcApp app);

    /**
     * 编辑应用
     * @param app 应用对象
     * @return
     * */
    Results editApp(CcApp app);

    /**
     * 删除应用
     * @param ids 应用ID集合
     * @return
     * */
    Results deleteApp(List<Integer> ids);

    /**
     * 获取所有应用
     * @return
     * */
    List<CcApp> getApps();

    /**
     * 根据主键获取应用信息
     * @param id 应用主键
     * @return
     * */
    CcApp getApp(Integer id);

    /**
     * 根据主键获取应用信息
     * @param name 应用名称
     * @return
     * */
    CcApp getApp(String name);
}
