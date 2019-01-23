package com.xlizy.middleware.cc.server.common.handlers;

import com.xlizy.middleware.cc.server.common.base.BaseValEnum;
import com.xlizy.middleware.cc.server.common.utils.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 枚举处理类
 * 参考: https://blog.csdn.net/yanghanxiu/article/details/78078043
 * @author xlizy
 * @date 2018/3/10
 */
public class ValEnumTypeHandler<E extends Enum<?> & BaseValEnum> extends BaseTypeHandler<BaseValEnum> {

    private Class<E> type;

    public ValEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseValEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getVal());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int code) {
        try {
            return EnumUtil.valOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
        }
    }

}