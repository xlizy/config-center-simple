package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetailsCriteria;
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
public interface CcSnapshotDetailsMapper {

    @SelectProvider(type=CcSnapshotDetailsSqlProvider.class, method="countByExample")
    long countByExample(CcSnapshotDetailsCriteria example);

    @DeleteProvider(type=CcSnapshotDetailsSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcSnapshotDetailsCriteria example);

    @Delete({
        "delete from snapshot_details",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into snapshot_details (snapshot_id, properties_key, ",
        "properties_value, properties_name, ",
        "properties_enable, create_time, ",
        "create_user, last_modify_time, ",
        "last_modify_user)",
        "values (#{snapshotId,jdbcType=INTEGER}, #{propertiesKey,jdbcType=VARCHAR}, ",
        "#{propertiesValue,jdbcType=VARCHAR}, #{propertiesName,jdbcType=VARCHAR}, ",
        "#{propertiesEnable,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUser,jdbcType=VARCHAR}, #{lastModifyTime,jdbcType=TIMESTAMP}, ",
        "#{lastModifyUser,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcSnapshotDetails record);

    @InsertProvider(type=CcSnapshotDetailsSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcSnapshotDetails record);

    @SelectProvider(type=CcSnapshotDetailsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="snapshot_id", property="snapshotId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="properties_key", property="propertiesKey", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_value", property="propertiesValue", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_name", property="propertiesName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_enable", property="propertiesEnable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcSnapshotDetails> selectByExampleWithRowbounds(CcSnapshotDetailsCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcSnapshotDetailsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="snapshot_id", property="snapshotId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="properties_key", property="propertiesKey", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_value", property="propertiesValue", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_name", property="propertiesName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_enable", property="propertiesEnable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<CcSnapshotDetails> selectByExample(CcSnapshotDetailsCriteria example);

    @Select({
        "select",
        "id, snapshot_id, properties_key, properties_value, properties_name, properties_enable, ",
        "create_time, create_user, last_modify_time, last_modify_user",
        "from snapshot_details",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="snapshot_id", property="snapshotId", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Result(column="properties_key", property="propertiesKey", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_value", property="propertiesValue", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_name", property="propertiesName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="properties_enable", property="propertiesEnable", javaType=Enable.class, jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user", property="createUser", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="last_modify_time", property="lastModifyTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_modify_user", property="lastModifyUser", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    CcSnapshotDetails selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcSnapshotDetailsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcSnapshotDetails record, @Param("example") CcSnapshotDetailsCriteria example);

    @UpdateProvider(type=CcSnapshotDetailsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcSnapshotDetails record, @Param("example") CcSnapshotDetailsCriteria example);

    @UpdateProvider(type=CcSnapshotDetailsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcSnapshotDetails record);

    @Update({
        "update snapshot_details",
        "set snapshot_id = #{snapshotId,jdbcType=INTEGER},",
          "properties_key = #{propertiesKey,jdbcType=VARCHAR},",
          "properties_value = #{propertiesValue,jdbcType=VARCHAR},",
          "properties_name = #{propertiesName,jdbcType=VARCHAR},",
          "properties_enable = #{propertiesEnable,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=VARCHAR},",
          "last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP},",
          "last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcSnapshotDetails record);
}