package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcAppCriteria;
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
public interface CcAppMapper {

    @SelectProvider(type=CcAppSqlProvider.class, method="countByExample")
    long countByExample(CcAppCriteria example);

    @DeleteProvider(type=CcAppSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcAppCriteria example);

    @Delete({
        "delete from app",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into app (`name`, remark, ",
        "create_time, create_user, ",
        "last_modify_time, last_modify_user)",
        "values (#{name,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, ",
        "#{lastModifyTime,jdbcType=TIMESTAMP}, #{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcApp record);

    @InsertProvider(type=CcAppSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcApp record);

    @SelectProvider(type=CcAppSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcApp> selectByExampleWithRowbounds(CcAppCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcAppSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcApp> selectByExample(CcAppCriteria example);

    @Select({
        "select",
        "id, `name`, remark, create_time, create_user, last_modify_time, last_modify_user",
        "from app",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcApp selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcAppSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcApp record, @Param("example") CcAppCriteria example);

    @UpdateProvider(type=CcAppSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcApp record, @Param("example") CcAppCriteria example);

    @UpdateProvider(type=CcAppSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcApp record);

    @Update({
        "update app",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcApp record);
}