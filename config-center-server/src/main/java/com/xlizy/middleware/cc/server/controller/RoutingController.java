package com.xlizy.middleware.cc.server.controller;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.enums.OPType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面
 * @author xlizy
 * @date 2018/5/19
 */
@Controller
@RequestMapping("/")
public class RoutingController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 应用配置管理页面
     * */
    @RequestMapping("/propertiesManagerPage")
    @OperationLog(bizType = OPType.OTHER_ACCESS_CONFIG_CENTER_PAGE)
    public String propertiesManager(){
        return "configcenter/ConfigCenterManager";
    }

    /**
     * 系统设置页面
     * */
    @RequestMapping("/configManagerPage")
    @OperationLog(bizType = OPType.OTHER_ACCESS_CONFIG_PAGE)
    public String configManager(){
        return "config/ConfigManager";
    }

    /**
     * 配置文件转换工具页面
     * */
    @RequestMapping("/propToolPage")
    @OperationLog(bizType = OPType.OTHER_ACCESS_PROP_TOOL_PAGE)
    public String propToolPage(){
        return "redirect:http://www.toyaml.com/";
    }

    /**
     * 客户端连接信息监控
     * */
    @RequestMapping("/clientManagerPage")
    @OperationLog(bizType = OPType.OTHER_ACCESS_CLIENT_PAGE)
    public String clientManagerPage(){
        return "client/clientManagerPage";
    }

    /**
     * 快照管理页面
     * */
    @RequestMapping("/snapshotManagerPage")
    @OperationLog(bizType = OPType.OTHER_ACCESS_SNAPSHOT_PAGE)
    public String snapshotManager(){
        return "snapshot/snapshotManagerPage";
    }

}
