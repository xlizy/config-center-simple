package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.entity.CcPropertiesCriteria;
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
public interface CcPropertiesMapper {

    @SelectProvider(type=CcPropertiesSqlProvider.class, method="countByExample")
    long countByExample(CcPropertiesCriteria example);

    @DeleteProvider(type=CcPropertiesSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcPropertiesCriteria example);

    @Delete({
        "delete from properties",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into properties (env_id, `key`, ",
        "`value`, `name`, remark, ",
        "`enable`, create_time, ",
        "create_user, last_modify_time, ",
        "last_modify_user)",
        "values (#{envId,jdbcType=INTEGER}, #{key,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{enable,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUser,jdbcType=VARCHAR}, #{lastModifyTime,jdbcType=TIMESTAMP}, ",
        "#{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcProperties record);

    @InsertProvider(type=CcPropertiesSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcProperties record);

    @SelectProvider(type=CcPropertiesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="key", property="key", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcProperties> selectByExampleWithRowbounds(CcPropertiesCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcPropertiesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="key", property="key", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcProperties> selectByExample(CcPropertiesCriteria example);

    @Select({
        "select",
        "id, env_id, `key`, `value`, `name`, remark, `enable`, create_time, create_user, ",
        "last_modify_time, last_modify_user",
        "from properties",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="key", property="key", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="enable", property="enable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcProperties selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcPropertiesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcProperties record, @Param("example") CcPropertiesCriteria example);

    @UpdateProvider(type=CcPropertiesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcProperties record, @Param("example") CcPropertiesCriteria example);

    @UpdateProvider(type=CcPropertiesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcProperties record);

    @Update({
        "update properties",
        "set env_id = #{envId,jdbcType=INTEGER},",
          "`key` = #{key,jdbcType=VARCHAR},",
          "`value` = #{value,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "`enable` = #{enable,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcProperties record);
}