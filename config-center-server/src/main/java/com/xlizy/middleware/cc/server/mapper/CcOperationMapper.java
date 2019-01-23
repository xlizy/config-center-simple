package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcOperation;
import com.xlizy.middleware.cc.server.entity.CcOperationCriteria;
import com.xlizy.middleware.cc.server.enums.OPType;
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
public interface CcOperationMapper {

    @SelectProvider(type=CcOperationSqlProvider.class, method="countByExample")
    long countByExample(CcOperationCriteria example);

    @DeleteProvider(type=CcOperationSqlProvider.class, method="deleteByExample")
    int deleteByExample(CcOperationCriteria example);

    @Delete({
        "delete from operation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into operation (trace_id, ip, ",
        "user_name, biz_type, ",
        "remark, start_time, ",
        "end_time)",
        "values (#{traceId,jdbcType=VARCHAR}, #{ip,jdbcType=VARCHAR}, ",
        "#{userName,jdbcType=VARCHAR}, #{bizType,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CcOperation record);

    @InsertProvider(type=CcOperationSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CcOperation record);

    @SelectProvider(type=CcOperationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="trace_id", property="traceId", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_type", property="bizType", javaType=OPType.class, jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<CcOperation> selectByExampleWithRowbounds(CcOperationCriteria example, RowBounds rowBounds);

    @SelectProvider(type=CcOperationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="trace_id", property="traceId", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_type", property="bizType", javaType=OPType.class, jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<CcOperation> selectByExample(CcOperationCriteria example);

    @Select({
        "select",
        "id, trace_id, ip, user_name, biz_type, remark, start_time, end_time",
        "from operation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="trace_id", property="traceId", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_type", property="bizType", javaType=OPType.class, jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    CcOperation selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CcOperationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CcOperation record, @Param("example") CcOperationCriteria example);

    @UpdateProvider(type=CcOperationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CcOperation record, @Param("example") CcOperationCriteria example);

    @UpdateProvider(type=CcOperationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CcOperation record);

    @Update({
        "update operation",
        "set trace_id = #{traceId,jdbcType=VARCHAR},",
          "ip = #{ip,jdbcType=VARCHAR},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "biz_type = #{bizType,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CcOperation record);
}