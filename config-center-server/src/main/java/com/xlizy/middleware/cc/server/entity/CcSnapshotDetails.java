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
public class CcSnapshotDetails extends BaseModel implements Serializable {

    /** 所属快照 */
    private Integer snapshotId;

    /** 配置key */
    private String propertiesKey;

    /** 配置value */
    private String propertiesValue;

    /** 配置名称,注释 */
    private String propertiesName;

    /** 是否启用,对应com.xlizy.middleware.cc.server.enums.Enable */
    private Enable propertiesEnable;

    private static final long serialVersionUID = 1L;
}