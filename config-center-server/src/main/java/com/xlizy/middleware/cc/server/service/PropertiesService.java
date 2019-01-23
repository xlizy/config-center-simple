package com.xlizy.middleware.cc.server.service;

import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.enums.Enable;

import java.io.InputStream;
import java.util.List;

/**
 * 配置服务接口
 * @author xlizy
 * @date 2018/5/19
 */
public interface PropertiesService {

    /**
     * 添加配置
     * @param properties 配置对象
     * @return
     * */
    Results addProperties(CcProperties properties);

    /**
     * 编辑对象
     * @param properties 配置对象
     * @return
     * */
    Results editProperties(CcProperties properties);

    /**
     * 删除配置
     * @param envId 环境ID
     * @param ids 配置ID集合
     * @return
     * */
    Results deleteProperties(Integer envId, List<Integer> ids);

    /**
     * 设置配置启用状态
     * @param envId 环境ID
     * @param ids 配置ID集合
     * @param enable 启用状态
     * @return
     * */
    Results setEnable(Integer envId, List<Integer> ids, Enable enable);

    /**
     * 分页查询配置列表
     * @param start 分页参数
     * @param limit 分页参数
     * @param properties 配置对象
     * @return
     * */
    String getPropertiess(CcProperties properties, Integer start, Integer limit);

    /**
     * 根据主键获取配置信息
     * @param id 配置主键
     * @return
     * */
    CcProperties getProperties(Integer id);

    /**
     * 下载配置文件
     * @param ids 配置ID集合
     * @param envId 环境ID
     * */
    String downloadProperties(String ids, Integer envId);

    /**
     * 上传配置文件
     * @param envId 环境ID
     * @param is 文件字节流
     * @return
     * */
    Results uploadProperties(Integer envId, InputStream is);

    /**
     * 根据环境ID获取配置列表
     * @param envId 环境ID
     * @return
     * */
    List<CcProperties> getPropertiess(Integer envId);
}
