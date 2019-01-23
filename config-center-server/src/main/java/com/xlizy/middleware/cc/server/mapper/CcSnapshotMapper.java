package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcSnapshot;
import com.xlizy.middleware.cc.server.entity.CcSnapshotCriteria;
import com.xlizy.middleware.cc.server.enums.CreateType;
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
public interface CcSnapshotMapper {

    @SelectProvider(type=CcSnapshotSqlProvider.class, method="countByExample")
    long countByExample(CcSnapshotCriteria example);

    @DeleteProvider(type=CcSnapshotSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcSnapshotCriteria example);

    @Delete({
        "delete from snapshot",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into snapshot (`name`, app_id, ",
        "env_id, create_type, create_time, ",
        "create_user, last_modify_time, ",
        "last_modify_user)",
        "values (#{name,jdbcType=VARCHAR}, #{appId,jdbcType=INTEGER}, ",
        "#{envId,jdbcType=INTEGER}, #{createType,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUser,jdbcType=VARCHAR}, #{lastModifyTime,jdbcType=TIMESTAMP}, ",
        "#{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcSnapshot record);

    @InsertProvider(type=CcSnapshotSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcSnapshot record);

    @SelectProvider(type=CcSnapshotSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="create_type", property="createType", javaType=CreateType.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcSnapshot> selectByExampleWithRowbounds(CcSnapshotCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcSnapshotSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="create_type", property="createType", javaType=CreateType.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcSnapshot> selectByExample(CcSnapshotCriteria example);

    @Select({
        "select",
        "id, `name`, app_id, env_id, create_type, create_time, create_user, last_modify_time, ",
        "last_modify_user",
        "from snapshot",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="app_id", property="appId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="env_id", property="envId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="create_type", property="createType", javaType=CreateType.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcSnapshot selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcSnapshotSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcSnapshot record, @Param("example") CcSnapshotCriteria example);

    @UpdateProvider(type=CcSnapshotSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcSnapshot record, @Param("example") CcSnapshotCriteria example);

    @UpdateProvider(type=CcSnapshotSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcSnapshot record);

    @Update({
        "update snapshot",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "app_id = #{appId,jdbcType=INTEGER},",
          "env_id = #{envId,jdbcType=INTEGER},",
          "create_type = #{createType,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcSnapshot record);
}