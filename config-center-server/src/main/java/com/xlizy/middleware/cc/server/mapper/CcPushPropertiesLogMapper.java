package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLog;
import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLogCriteria;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface CcPushPropertiesLogMapper {

    @SelectProvider(type=CcPushPropertiesLogSqlProvider.class, method="countByExample")
    long countByExample(CcPushPropertiesLogCriteria example);

    @DeleteProvider(type=CcPushPropertiesLogSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcPushPropertiesLogCriteria example);

    @Delete({
        "delete from push_properties_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into push_properties_log (app, env, ",
        "version, `cluster`, ",
        "push_reason, client_address, ",
        "create_time, create_user, ",
        "last_modify_time, last_modify_user)",
        "values (#{app,jdbcType=VARCHAR}, #{env,jdbcType=VARCHAR}, ",
        "#{version,jdbcType=VARCHAR}, #{cluster,jdbcType=VARCHAR}, ",
        "#{pushReason,jdbcType=BIT}, #{clientAddress,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, ",
        "#{lastModifyTime,jdbcType=TIMESTAMP}, #{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcPushPropertiesLog record);

    @InsertProvider(type=CcPushPropertiesLogSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcPushPropertiesLog record);

    @SelectProvider(type=CcPushPropertiesLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app", property="app", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="push_reason", property="pushReason", javaType=SendConfigType.class, jdbcType=JdbcType.BIT),
        @Result(column="client_address", property="clientAddress", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcPushPropertiesLog> selectByExampleWithRowbounds(CcPushPropertiesLogCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcPushPropertiesLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app", property="app", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="push_reason", property="pushReason", javaType=SendConfigType.class, jdbcType=JdbcType.BIT),
        @Result(column="client_address", property="clientAddress", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcPushPropertiesLog> selectByExample(CcPushPropertiesLogCriteria example);

    @Select({
        "select",
        "id, app, env, version, `cluster`, push_reason, client_address, create_time, ",
        "create_user, last_modify_time, last_modify_user",
        "from push_properties_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app", property="app", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="push_reason", property="pushReason", javaType=SendConfigType.class, jdbcType=JdbcType.BIT),
        @Result(column="client_address", property="clientAddress", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcPushPropertiesLog selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcPushPropertiesLogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcPushPropertiesLog record, @Param("example") CcPushPropertiesLogCriteria example);

    @UpdateProvider(type=CcPushPropertiesLogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcPushPropertiesLog record, @Param("example") CcPushPropertiesLogCriteria example);

    @UpdateProvider(type=CcPushPropertiesLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcPushPropertiesLog record);

    @Update({
        "update push_properties_log",
        "set app = #{app,jdbcType=VARCHAR},",
          "env = #{env,jdbcType=VARCHAR},",
          "version = #{version,jdbcType=VARCHAR},",
          "`cluster` = #{cluster,jdbcType=VARCHAR},",
          "push_reason = #{pushReason,jdbcType=BIT},",
          "client_address = #{clientAddress,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcPushPropertiesLog record);
}