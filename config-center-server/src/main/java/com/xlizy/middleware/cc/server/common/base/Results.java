package com.xlizy.middleware.cc.server.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * 返回接口封装
 * @author xlizy
 */
public class Results<T> implements Serializable {
    /** 操作是否成功 */
    public boolean success;
    /** 错误代码(正数成功\负数失败,默认成功为1) */
    public int code = 0;
    /** 提示信息 */
    public String message;
    /** 结果 */
    public T data;
    /** 错误详情 */
    public String errorMsgDetails;

    /** 跟踪Id */
    public String traceId = MDC.get(CoreUtil.TRACE_ID);

    public Results() {
    }

    public Results(boolean success, int code, String message, T data){
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Results<T> success(){
        return new Results(true,0,null,null);
    }

    public static <T> Results<T> failure(){
        return new Results(false,0,null,null);
    }

    public Results code(int code){
        this.code = code;
        return this;
    }

    public Results msg(String msg){
        this.message = msg;
        return this;
    }

    public Results data(T data){
        this.data = data;
        return this;
    }

    public Results errorMsgDetails(String errorMsgDetails){
        this.errorMsgDetails = errorMsgDetails;
        return this;
    }

    public boolean isSuccess(){
        return this.success;
    }


    public static String gridResult(long totalCount, Object content){
        StringBuffer sb = new StringBuffer("{totalCount:");
        sb.append(totalCount);
        sb.append(",rows:");
        sb.append(JSON.toJSONString(content,SerializerFeature.WriteEnumUsingToString));
        sb.append("}");
        return sb.toString();
    }

    public static JSONObject gridResultJson(long totalCount, Object content){
        return JSON.parseObject(gridResult(totalCount,content));
    }

    public static String treeResult(String content){
        StringBuffer sb=new StringBuffer("[");
        sb.append(content);
        sb.append("]");
        return sb.toString();
    }

    public static String dataResult(Object content){
        StringBuffer sb=new StringBuffer("{success:true,data:");
        sb.append(JSON.toJSONString(content, SerializerFeature.WriteMapNullValue));
        sb.append("}");
        return sb.toString();
    }

    public static String dataListResult(Object content) {
        StringBuffer sb = new StringBuffer("");
        sb.append(JSON.toJSONString(content, SerializerFeature.WriteMapNullValue));
        return sb.toString();
    }

    public String toJsonString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
