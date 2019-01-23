package com.xlizy.middleware.cc.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.ConfigCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心配置管理页面接口
 * @author xlizy
 * @date 2018/5/19
 */
@RestController
@RequestMapping("cc")
public class ConfigCenterController {

    @Autowired
    ConfigCenterService configCenterService;

    /**
     * 应用+环境 结合的树形结构
     * */
    @GetMapping("tree")
    @OperationLog(bizType = OPType.GET_APP_ENV_TREE)
    public JSONObject getTree(){
        JSONObject jsonObject = configCenterService.getTree();
        return jsonObject;
    }

}
