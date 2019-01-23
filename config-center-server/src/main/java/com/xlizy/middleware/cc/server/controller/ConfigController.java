package com.xlizy.middleware.cc.server.controller;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统设置
 * @author xlizy
 * @date 2018/4/25
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    /**
     * 清空session缓存
     * */
    @PostMapping("clearSessionCache")
    @OperationLog(bizType = OPType.CLEAR_SESSION_CACHE)
    public Results clearSessionCache(){
        configService.clearSessionCache();
        return Results.success().msg("清空成功");
    }

    /**
     * 清空数据缓存
     * */
    @PostMapping("clearDataCache")
    @OperationLog(bizType = OPType.CLEAR_DATA_CACHE)
    public Results clearDataCache(){
        configService.clearDataCache();
        return Results.success().msg("清空成功");
    }
}
