package com.xlizy.middleware.cc.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.ConfigCenterService;
import com.xlizy.middleware.cc.server.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置管理页面服务接口实现
 * @author xlizy
 * @date 2018/5/19
 */
@Service
public class ConfigCenterServiceImpl implements ConfigCenterService {

    @Autowired
    AppService appService;

    @Autowired
    EnvService envService;

    @Cacheable(value = "configCenterTree")
    @Override
    public JSONObject getTree() {
        JSONArray array = new JSONArray();
        List<CcApp> appList = appService.getApps();
        List<CcEnv> envList = envService.getEnvs();
        appList.forEach(a ->{
            //获取当前应用下的环境列表
            List<CcEnv> envs = envList.stream().filter(e -> e.getAppId().compareTo(a.getId()) == 0).collect(Collectors.toList());
            //应用下面有环境信息
            if(!envs.isEmpty()){
                JSONObject app = new JSONObject();
                app.put("dataId", a.getId());
                app.put("type", "app");
                app.put("text", a.getName());
                app.put("qtip", "<strong>name</strong>:" + a.getName() + "<br/><strong>remark</strong>:" + a.getRemark());
                app.put("expanded",Boolean.TRUE);
                JSONArray envArray = new JSONArray();
                envs.forEach(e -> {
                    JSONObject env = new JSONObject();
                    env.put("dataId", e.getId());
                    env.put("type", "env");
                    env.put("text", e.getName());
                    env.put("qtip", "<strong>name</strong>:" + e.getName() + "<br/><strong>env</strong>:" + e.getEnv() + "<br/><strong>version</strong>:" + e.getVersion() + "<br/><strong>cluster</strong>:" + e.getCluster() + "<br/><strong>enable</strong>:" + e.getEnable().getDes());
                    env.put("leaf", Boolean.TRUE);
                    envArray.add(env);
                });
                app.put("children", envArray);
                array.add(app);
            }else{//应用下面没有环境信息
                JSONObject app = new JSONObject();
                app.put("dataId", a.getId());
                app.put("type", "app");
                app.put("text", a.getName());
                app.put("qtip", "<strong>name</strong>:" + a.getName() + "<br/><strong>remark</strong>:" + a.getRemark());
                app.put("leaf", Boolean.TRUE);
                array.add(app);
            }
        });

        JSONObject object = new JSONObject();
        object.put("children",array);
        return object;
    }
}
