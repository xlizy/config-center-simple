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
public class CcProperties extends BaseModel implements Serializable {

    /** 所属环境 */
    private Integer envId;

    /** 配置key */
    private String key;

    /** 配置value */
    private String value;

    /** 配置名称,注释 */
    private String name;

    /** 备注 */
    private String remark;

    /** 是否启用,对应com.xlizy.middleware.cc.server.enums.Enable */
    private Enable enable;

    private static final long serialVersionUID = 1L;
}