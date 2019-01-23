package com.xlizy.middleware.cc.server.controller;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用相关
 * @author xlizy
 * @date 2018/5/28
 */
@RestController
@RequestMapping("app")
public class AppController {

    @Autowired
    AppService appService;

    /**
     * 添加应用
     * @param app 应用对象
     * */
    @PostMapping
    @OperationLog(bizType = OPType.ADD_APP)
    public Results addApp(CcApp app){
        return appService.addApp(app);
    }

    /**
     * 编辑应用
     * @param app 应用对象
     * */
    @PutMapping
    @OperationLog(bizType = OPType.EDIT_APP)
    public Results editApp(CcApp app) {
        return appService.editApp(app);
    }

    /**
     * 删除应用
     * @param ids 应用ID集合
     * */
    @DeleteMapping("{ids}")
    @OperationLog(bizType = OPType.DEL_APP)
    public Results deleteApp(@PathVariable("ids") String ids) {
        return appService.deleteApp(CoreUtil.strArray2List(ids));
    }

    /**
     * 根据主键获取应用信息
     * @param id 应用主键
     * */
    @GetMapping("{id}")
    @OperationLog(bizType = OPType.GET_APP)
    public String getApp(@PathVariable("id") Integer id){
        return Results.dataResult(appService.getApp(id));
    }


}
