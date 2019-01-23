package com.xlizy.middleware.cc.server.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.client.common.HeartbeatHandler;
import com.xlizy.middleware.cc.server.common.utils.IdGenerateUtil;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.common.utils.SpringContextUtil;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.service.NettyServerService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Iterator;
import java.util.Set;

/**
 * netty服务端处理类
 * @author xlizy
 * @date 2018/6/5
 */
@Slf4j
public class NettyServerHandler extends HeartbeatHandler {


    public NettyServerHandler() {
        super("server");
    }


    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {

        MDC.put(CoreUtil.TRACE_ID, IdGenerateUtil.getUUID());

        byte[] data = new byte[byteBuf.readableBytes() - 5];
        byteBuf.skipBytes(5);
        byteBuf.readBytes(data);
        String content = new String(data);
        log.info("接收到客户端请求信息:{}",content);

        JSONObject object = JSON.parseObject(content);

        StringBuilder key = new StringBuilder();
        key.append(object.getString("app")).append("@")
                .append(object.getString("env")).append("@")
                .append(object.getString("version")).append("@")
                .append(object.getString("cluster"));
        log.info("客户端来自:{}",key.toString());

        NettyServer.allServerSocketChannel.put(key.toString(),channelHandlerContext.channel());

        SpringContextUtil.getBean(NettyServerService.class).sendConfig(
                object.getString("app")
                ,object.getString("env")
                ,object.getString("version")
                ,object.getString("cluster")
                ,SendConfigType.INITIATIVE);
    }

    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
        log.warn("{} reader timeout, close it",ctx.channel().remoteAddress().toString());
        clearChannel(ctx);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        clearChannel(ctx);
        ctx.close();
    }

    /**
     * 清除失效的channel
     * */
    private void clearChannel(ChannelHandlerContext ctx){
        log.debug("clear invalid channel");
        Set<String> set = NettyServer.allServerSocketChannel.keySet();
        Iterator setItr = set.iterator();
        while(setItr.hasNext()){
            Object k = setItr.next();
            boolean b = NettyServer.allServerSocketChannel.get(String.valueOf(k)).remove(ctx.channel());
            if(b){
                break;
            }
        }
    }
}
