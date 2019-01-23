package com.xlizy.middleware.cc.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.RequestUtils;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.enums.CreateType;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置快照
 * @author xlizy
 * @date 2018/6/4
 */
@RestController
@RequestMapping("snapshot")
public class SnapshotController {

    @Autowired
    SnapshotService snapshotService;

    /**
     * 创建快照
     * @param envId 环境主键
     * @param envId 快照名称
     * */
    @PostMapping
    @OperationLog(bizType = OPType.ADD_SNAPSHOT)
    public Results addSnapshot(Integer envId,String name){
        return snapshotService.addSnapshot(envId,name,CreateType.MANUALLY);
    }

    /**
     * 删除快照
     * @param id 快照主键
     * */
    @DeleteMapping("{id}")
    @OperationLog(bizType = OPType.DEL_SNAPSHOT)
    public Results deleteSnapshot(@PathVariable("id") Integer id){
        return snapshotService.deleteSnapshot(id);
    }

    /**
     * 获取 应用+环境+快照 结合的树形结构
     * */
    @GetMapping("tree")
    @OperationLog(bizType = OPType.GET_APP_ENV_SNAPSHOT_TREE)
    public JSONObject getTree(){
        return snapshotService.getTree();
    }

    /**
     * 从快照恢复配置
     * @param envId 环境主键
     * @param id 快照主键
     * */
    @PostMapping("restoreSnapshot")
    @OperationLog(bizType = OPType.OTHER_RESTORE_SNAPSHOT)
    public Results restoreSnapshot(Integer id, Integer envId){
        return snapshotService.restoreSnapshot(id,envId);
    }

    /**
     * 根据snapshotId,及其他查询条件获取快照明细列表
     * @param details 快照明细对象
     * @param snapshotId 快照主键
     * */
    @GetMapping("list/{snapshotId}")
    @OperationLog(bizType = OPType.GET_SNAPSHOT_DETAILS_FOR_PAGE)
    public String getSnapshots(HttpServletRequest request, @PathVariable("snapshotId")Integer snapshotId, CcSnapshotDetails details){
        if(details == null){
            details = new CcSnapshotDetails();
        }
        details.setSnapshotId(snapshotId);
        int[] limits = RequestUtils.getPageLimit(request);
        return snapshotService.getSnapshotDetails(details,limits[0], limits[1]);
    }

}
