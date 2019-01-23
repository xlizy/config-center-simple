package com.xlizy.middleware.cc.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.service.ClientMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 客户端监控服务接口实现
 * @author xlizy
 * @date 2018/5/29
 */
@Service
public class ClientMonitorServiceImpl implements ClientMonitorService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public JSONObject getTree() {
        JSONArray array = new JSONArray();
        Map<String,String> map =  redisTemplate.opsForHash().entries("ClientConnectionInfo");
        map.keySet().forEach(k -> {
            String v = map.get(k);
            if(StringUtils.isNotEmpty(v)){
                JSONObject ser = new JSONObject();
                ser.put("text", k);
                ser.put("qtip", "<strong>"+k+"</strong>");
                ser.put("expanded",Boolean.TRUE);
                JSONArray vs = JSON.parseArray(v);
                JSONArray arr = new JSONArray();
                vs.forEach(val -> {
                    JSONObject _v = new JSONObject();
                    _v.put("text", val);
                    _v.put("qtip", "<strong>"+val+"</strong>");
                    _v.put("leaf", Boolean.TRUE);
                    arr.add(_v);
                });
                ser.put("children", arr);
                array.add(ser);

            }else{
                JSONObject ser = new JSONObject();
                ser.put("text", k);
                ser.put("qtip", "<strong>"+k+"</strong>");
                ser.put("leaf", Boolean.TRUE);
                array.add(ser);
            }

        });
        JSONObject object = new JSONObject();
        object.put("children",array);
        return object;
    }
}
