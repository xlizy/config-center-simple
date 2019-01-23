package com.xlizy.middleware.cc.server.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 持久层对象父类
 * @author xlizy
 * @date 2018/3/9
 */
@Data
public abstract class BaseModel implements Serializable {

    /** 主键 */
    protected Integer id;

    /** 创建时间 */
    protected Date createTime;

    /** 创建人 */
    protected String createUser;

    /** 最后修改时间 */
    protected Date lastModifyTime;

    /** 最后修改人 */
    protected String lastModifyUser;
}
