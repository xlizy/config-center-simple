package com.xlizy.middleware.cc.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.ClientMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端监控
 * @author xlizy
 * @date 2018/5/29
 */
@RestController
@RequestMapping("client")
public class ClientMonitorController {

    @Autowired
    ClientMonitorService clientMonitorService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获得客户端连接信息tree
     * */
    @GetMapping("tree")
    @OperationLog(bizType = OPType.GET_CLIENT_CONNECTION_INFO)
    public JSONObject getTree(){
        //先清空缓存数据
        redisTemplate.delete("ClientConnectionInfo");

        //往队列推送一条消息
        JSONObject object = new JSONObject();
        object.put("collectInfo","collectInfo");
        kafkaTemplate.send("config-center_collect-info",object.toJSONString());

        //等待1秒钟,此时N个配置中心服务端节点会把自己NettyServer.allServerSocketChannel的数据写到redis里
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //读取redis数据生成extjs需要的数据结构
        return clientMonitorService.getTree();
    }

}
