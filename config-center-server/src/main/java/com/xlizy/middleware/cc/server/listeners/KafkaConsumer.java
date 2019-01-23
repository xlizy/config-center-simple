package com.xlizy.middleware.cc.server.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.common.utils.IdGenerateUtil;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.netty.NettyServer;
import com.xlizy.middleware.cc.server.service.NettyServerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author xlizy
 * @date 2019-01-09
 */
@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    NettyServerService nettyServerService;

    @KafkaListener(topics = "config-center_send-config",groupId = "default")
    public void sendConfig(ConsumerRecord<?, String> record){
        MDC.put(CoreUtil.TRACE_ID, IdGenerateUtil.getUUID());
        log.info("接收到消息:{}",record.value());

        JSONObject msg = JSON.parseObject(record.value());
        nettyServerService.sendConfig(msg.getString("app"),msg.getString("env"),msg.getString("version"),msg.getString("cluster"), SendConfigType.valueOf(msg.getString("sendConfigType")));
    }

    @KafkaListener(topics = "config-center_collect-info",groupId = "default")
    public void collectInfo(ConsumerRecord<?, String> record){
        MDC.put(CoreUtil.TRACE_ID, IdGenerateUtil.getUUID());
        log.info("接收到消息:{}",record.value());

        JSONObject msg = JSON.parseObject(record.value());
        //将当前节点上的客户端连接信息稍微处理一下,写到redis上
        NettyServer.allServerSocketChannel.keySet().forEach(k -> {
            String key = CoreUtil.getLocalIP() + ":" + CoreUtil.getPID() + "::::" + k;
            JSONArray array = new JSONArray();
            NettyServer.allServerSocketChannel.get(k).forEach(c -> {
                array.add(c.toString());
            });
            redisTemplate.opsForHash().put("ClientConnectionInfo",key,array.toJSONString());
        });
    }

}
