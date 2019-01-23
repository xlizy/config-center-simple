package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.common.base.BaseModel;
import com.xlizy.middleware.cc.server.enums.OPType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author xlizy
 * @date 2018/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CcOperation extends BaseModel implements Serializable {

    /** 业务跟踪ID */
    private String traceId;

    /** 操作人ip */
    private String ip;

    /** 操作人 */
    private String userName;

    /** 操作类型 */
    private OPType bizType;

    /** 备注 */
    private String remark;

    /** 操作开始时间 */
    private Date startTime;

    /** 操作结束时间 */
    private Date endTime;

    private static final long serialVersionUID = 1L;
}