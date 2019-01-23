package com.xlizy.middleware.cc.server.service;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.entity.CcSnapshot;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.enums.CreateType;

import java.util.List;

/**
 * 配置快照服务
 * @author xlizy
 * @date 2018/6/4
 */
public interface SnapshotService {

    /**
     * 创建快照
     * @param envId 环境ID
     * @param name 快照名称
     * @param createType 快照创建类型
     * @return
     * */
    Results addSnapshot(Integer envId, String name, CreateType createType);

    /**
     * 删除快照
     * @param id 快照主键
     * @return
     * */
    Results deleteSnapshot(Integer id);

    /**
     * 获取 应用+环境+快照 结合的树形结构
     * @return
     * */
    JSONObject getTree();

    /**
     * 从快照恢复配置
     * @param id 快照主键
     * @param envId 环境主键
     * @return
     * */
    Results restoreSnapshot(Integer id, Integer envId);

    /**
     * 获取所有快照
     * */
    List<CcSnapshot> getSnapshots();

    /**
     * 分页查询快照明细
     * @param details 明细对象
     * @param start 分页参数
     * @param limit 分页参数
     * @return
     * */
    String getSnapshotDetails(CcSnapshotDetails details, Integer start, Integer limit);

}
