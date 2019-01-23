package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.common.base.BaseModel;
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
public class CcApp extends BaseModel implements Serializable {

    /** 应用名称 */
    private String name;

    /** 应用备注 */
    private String remark;

    private static final long serialVersionUID = 1L;
}