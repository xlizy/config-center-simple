package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.common.base.BaseModel;
import com.xlizy.middleware.cc.server.enums.Enable;
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
public class CcEnv extends BaseModel implements Serializable {

    /** 所属应用 */
    private Integer appId;

    /** 环境名称 */
    private String name;

    /** 环境 */
    private String env;

    /** 版本 */
    private String version;

    /** 集群/机房 */
    private String cluster;

    /** 是否启用,对应com.xlizy.middleware.cc.server.enums.Enable */
    private Enable enable;

    /** 备注 */
    private String remark;

    private static final long serialVersionUID = 1L;
}