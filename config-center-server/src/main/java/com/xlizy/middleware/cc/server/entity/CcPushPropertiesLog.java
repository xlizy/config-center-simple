package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.common.base.BaseModel;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @author xlizy
 * @date 2018/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CcPushPropertiesLog extends BaseModel implements Serializable {

    /** 应用名称 */
    private String app;

    /** 环境名称 */
    private String env;

    /** 版本 */
    private String version;

    /** 集群/机房 */
    private String cluster;

    /** 推送原因,对应com.xlizy.middleware.cc.server.enums.SendConfigType */
    private SendConfigType pushReason;

    /** 客户端地址 */
    private String clientAddress;

    private static final long serialVersionUID = 1L;
}