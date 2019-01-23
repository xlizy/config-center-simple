package com.xlizy.middleware.cc.server.controller;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 环境相关
 * @author xlizy
 * @date 2018/5/28
 */
@RestController
@RequestMapping("env")
public class EnvController {

    @Autowired
    EnvService envService;

    /**
     * 添加环境
     * @param env 环境对象
     * */
    @PostMapping
    @OperationLog(bizType = OPType.ADD_ENV)
    public Results addEnv(CcEnv env){
        return envService.addEnv(env);
    }

    /**
     * 编辑环境
     * @param env 环境对象
     * */
    @PutMapping
    @OperationLog(bizType = OPType.EDIT_ENV)
    public Results editEnv(CcEnv env) {
        return envService.editEnv(env);
    }

    /**
     * 删除环境
     * @param ids 环境ID集合
     * */
    @DeleteMapping("{ids}")
    @OperationLog(bizType = OPType.DEL_ENV)
    public Results deleteEnv(@PathVariable("ids") String ids) {
        return envService.deleteEnv(CoreUtil.strArray2List(ids));
    }

    /**
     * 根据主键获取环境信息
     * @param id 主键
     * */
    @GetMapping("{id}")
    @OperationLog(bizType = OPType.GET_ENV)
    public String getEnv(@PathVariable("id") Integer id){
        return Results.dataResult(envService.getEnv(id));
    }

}
