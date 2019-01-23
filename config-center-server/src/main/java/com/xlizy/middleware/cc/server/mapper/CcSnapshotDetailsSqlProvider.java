package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetailsCriteria;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetailsCriteria.Criteria;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetailsCriteria.Criterion;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class CcSnapshotDetailsSqlProvider {


    public String countByExample(CcSnapshotDetailsCriteria example) {

        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("snapshot_details");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CcSnapshotDetailsCriteria example) {

        SQL sql = new SQL();
        sql.DELETE_FROM("snapshot_details");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(CcSnapshotDetails record) {

        SQL sql = new SQL();
        sql.INSERT_INTO("snapshot_details");
        
        if (record.getSnapshotId() != null) {
            sql.VALUES("snapshot_id", "#{snapshotId,jdbcType=INTEGER}");
        }
        
        if (record.getPropertiesKey() != null) {
            sql.VALUES("properties_key", "#{propertiesKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesValue() != null) {
            sql.VALUES("properties_value", "#{propertiesValue,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesName() != null) {
            sql.VALUES("properties_name", "#{propertiesName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesEnable() != null) {
            sql.VALUES("properties_enable", "#{propertiesEnable,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUser() != null) {
            sql.VALUES("create_user", "#{createUser,jdbcType=VARCHAR}");
        }
        
        if (record.getLastModifyTime() != null) {
            sql.VALUES("last_modify_time", "#{lastModifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastModifyUser() != null) {
            sql.VALUES("last_modify_user", "#{lastModifyUser,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(CcSnapshotDetailsCriteria example) {

        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("snapshot_id");
        sql.SELECT("properties_key");
        sql.SELECT("properties_value");
        sql.SELECT("properties_name");
        sql.SELECT("properties_enable");
        sql.SELECT("create_time");
        sql.SELECT("create_user");
        sql.SELECT("last_modify_time");
        sql.SELECT("last_modify_user");
        sql.FROM("snapshot_details");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {

        CcSnapshotDetails record = (CcSnapshotDetails) parameter.get("record");
        CcSnapshotDetailsCriteria example = (CcSnapshotDetailsCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("snapshot_details");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getSnapshotId() != null) {
            sql.SET("snapshot_id = #{record.snapshotId,jdbcType=INTEGER}");
        }
        
        if (record.getPropertiesKey() != null) {
            sql.SET("properties_key = #{record.propertiesKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesValue() != null) {
            sql.SET("properties_value = #{record.propertiesValue,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesName() != null) {
            sql.SET("properties_name = #{record.propertiesName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesEnable() != null) {
            sql.SET("properties_enable = #{record.propertiesEnable,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUser() != null) {
            sql.SET("create_user = #{record.createUser,jdbcType=VARCHAR}");
        }
        
        if (record.getLastModifyTime() != null) {
            sql.SET("last_modify_time = #{record.lastModifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastModifyUser() != null) {
            sql.SET("last_modify_user = #{record.lastModifyUser,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {

        SQL sql = new SQL();
        sql.UPDATE("snapshot_details");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("snapshot_id = #{record.snapshotId,jdbcType=INTEGER}");
        sql.SET("properties_key = #{record.propertiesKey,jdbcType=VARCHAR}");
        sql.SET("properties_value = #{record.propertiesValue,jdbcType=VARCHAR}");
        sql.SET("properties_name = #{record.propertiesName,jdbcType=VARCHAR}");
        sql.SET("properties_enable = #{record.propertiesEnable,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("create_user = #{record.createUser,jdbcType=VARCHAR}");
        sql.SET("last_modify_time = #{record.lastModifyTime,jdbcType=TIMESTAMP}");
        sql.SET("last_modify_user = #{record.lastModifyUser,jdbcType=VARCHAR}");
        
        CcSnapshotDetailsCriteria example = (CcSnapshotDetailsCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(CcSnapshotDetails record) {

        SQL sql = new SQL();
        sql.UPDATE("snapshot_details");
        
        if (record.getSnapshotId() != null) {
            sql.SET("snapshot_id = #{snapshotId,jdbcType=INTEGER}");
        }
        
        if (record.getPropertiesKey() != null) {
            sql.SET("properties_key = #{propertiesKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesValue() != null) {
            sql.SET("properties_value = #{propertiesValue,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesName() != null) {
            sql.SET("properties_name = #{propertiesName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertiesEnable() != null) {
            sql.SET("properties_enable = #{propertiesEnable,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUser() != null) {
            sql.SET("create_user = #{createUser,jdbcType=VARCHAR}");
        }
        
        if (record.getLastModifyTime() != null) {
            sql.SET("last_modify_time = #{lastModifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastModifyUser() != null) {
            sql.SET("last_modify_user = #{lastModifyUser,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, CcSnapshotDetailsCriteria example, boolean includeExamplePhrase) {

        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}