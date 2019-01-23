package com.xlizy.middleware.cc.server.service;

import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.entity.CcEnv;

import java.util.List;

/**
 * 环境服务接口
 * @author xlizy
 * @date 2018/5/19
 */
public interface EnvService {

    /**
     * 添加环境
     * @param env 环境对象
     * @return
     * */
    Results addEnv(CcEnv env);

    /**
     * 编辑环境
     * @param env 环境对象
     * @return
     * */
    Results editEnv(CcEnv env);

    /**
     * 删除环境
     * @param ids 环境ID集合
     * @return
     * */
    Results deleteEnv(List<Integer> ids);

    /**
     * 获取所有环境信息
     * @return
     * */
    List<CcEnv> getEnvs();

    /**
     * 根据应用ID获取环境列表
     * @return
     * */
    List<CcEnv> getEnvsByAppId(Integer id);

    /**
     * 根据主键获取环境信息
     * @param id 主键
     * @return
     * */
    CcEnv getEnv(Integer id);

    /**
     * 根据应用ID及环境坐标说取环境信息
     * @param appId 应用ID
     * @param env 环境名称
     * @param version 版本
     * @param cluster 集群/机房
     * @return
     * */
    CcEnv getEnv(Integer appId, String env, String version, String cluster);
}
