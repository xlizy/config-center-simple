package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.common.base.BaseModel;
import com.xlizy.middleware.cc.server.enums.CreateType;
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
public class CcSnapshot extends BaseModel implements Serializable {

    /** 快照名称 */
    private String name;

    /** 所属应用 */
    private Integer appId;

    /** 所属环境 */
    private Integer envId;

    /** 创建类型,对应com.xlizy.middleware.cc.server.enums.CreateType */
    private CreateType createType;

    private static final long serialVersionUID = 1L;
}