package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcEnvCriteria;
import com.xlizy.middleware.cc.server.enums.Enable;
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
public interface CcEnvMapper {

    @SelectProvider(type=CcEnvSqlProvider.class, method="countByExample")
    long countByExample(CcEnvCriteria example);

    @DeleteProvider(type=CcEnvSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcEnvCriteria example);

    @Delete({
        "delete from env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into env (app_id, `name`, ",
        "env, version, `cluster`, ",
        "`enable`, remark, create_time, ",
        "create_user, last_modify_time, ",
        "last_modify_user)",
        "values (#{appId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{env,jdbcType=VARCHAR}, #{version,jdbcType=VARCHAR}, #{cluster,jdbcType=VARCHAR}, ",
        "#{enable,jdbcType=BIT}, #{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUser,jdbcType=VARCHAR}, #{lastModifyTime,jdbcType=TIMESTAMP}, ",
        "#{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcEnv record);

    @InsertProvider(type=CcEnvSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcEnv record);

    @SelectProvider(type=CcEnvSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcEnv> selectByExampleWithRowbounds(CcEnvCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcEnvSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcEnv> selectByExample(CcEnvCriteria example);

    @Select({
        "select",
        "id, app_id, `name`, env, version, `cluster`, `enable`, remark, create_time, ",
        "create_user, last_modify_time, last_modify_user",
        "from env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="env", property="env", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="version", property="version", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="cluster", property="cluster", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcEnv selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcEnvSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcEnv record, @Param("example") CcEnvCriteria example);

    @UpdateProvider(type=CcEnvSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcEnv record, @Param("example") CcEnvCriteria example);

    @UpdateProvider(type=CcEnvSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcEnv record);

    @Update({
        "update env",
        "set app_id = #{appId,jdbcType=INTEGER},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "env = #{env,jdbcType=VARCHAR},",
          "version = #{version,jdbcType=VARCHAR},",
          "`cluster` = #{cluster,jdbcType=VARCHAR},",
          "`enable` = #{enable,jdbcType=BIT},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcEnv record);
}