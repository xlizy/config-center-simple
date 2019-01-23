package com.xlizy.middleware.cc.server.mapper;

import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLog;
import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLogCriteria;
import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLogCriteria.Criteria;
import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLogCriteria.Criterion;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class CcPushPropertiesLogSqlProvider {


    public String countByExample(CcPushPropertiesLogCriteria example) {

        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("push_properties_log");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CcPushPropertiesLogCriteria example) {

        SQL sql = new SQL();
        sql.DELETE_FROM("push_properties_log");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(CcPushPropertiesLog record) {

        SQL sql = new SQL();
        sql.INSERT_INTO("push_properties_log");
        
        if (record.getApp() != null) {
            sql.VALUES("app", "#{app,jdbcType=VARCHAR}");
        }
        
        if (record.getEnv() != null) {
            sql.VALUES("env", "#{env,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            sql.VALUES("version", "#{version,jdbcType=VARCHAR}");
        }
        
        if (record.getCluster() != null) {
            sql.VALUES("`cluster`", "#{cluster,jdbcType=VARCHAR}");
        }
        
        if (record.getPushReason() != null) {
            sql.VALUES("push_reason", "#{pushReason,jdbcType=BIT}");
        }
        
        if (record.getClientAddress() != null) {
            sql.VALUES("client_address", "#{clientAddress,jdbcType=VARCHAR}");
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

    public String selectByExample(CcPushPropertiesLogCriteria example) {

        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("app");
        sql.SELECT("env");
        sql.SELECT("version");
        sql.SELECT("`cluster`");
        sql.SELECT("push_reason");
        sql.SELECT("client_address");
        sql.SELECT("create_time");
        sql.SELECT("create_user");
        sql.SELECT("last_modify_time");
        sql.SELECT("last_modify_user");
        sql.FROM("push_properties_log");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {

        CcPushPropertiesLog record = (CcPushPropertiesLog) parameter.get("record");
        CcPushPropertiesLogCriteria example = (CcPushPropertiesLogCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("push_properties_log");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getApp() != null) {
            sql.SET("app = #{record.app,jdbcType=VARCHAR}");
        }
        
        if (record.getEnv() != null) {
            sql.SET("env = #{record.env,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            sql.SET("version = #{record.version,jdbcType=VARCHAR}");
        }
        
        if (record.getCluster() != null) {
            sql.SET("`cluster` = #{record.cluster,jdbcType=VARCHAR}");
        }
        
        if (record.getPushReason() != null) {
            sql.SET("push_reason = #{record.pushReason,jdbcType=BIT}");
        }
        
        if (record.getClientAddress() != null) {
            sql.SET("client_address = #{record.clientAddress,jdbcType=VARCHAR}");
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
        sql.UPDATE("push_properties_log");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("app = #{record.app,jdbcType=VARCHAR}");
        sql.SET("env = #{record.env,jdbcType=VARCHAR}");
        sql.SET("version = #{record.version,jdbcType=VARCHAR}");
        sql.SET("`cluster` = #{record.cluster,jdbcType=VARCHAR}");
        sql.SET("push_reason = #{record.pushReason,jdbcType=BIT}");
        sql.SET("client_address = #{record.clientAddress,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("create_user = #{record.createUser,jdbcType=VARCHAR}");
        sql.SET("last_modify_time = #{record.lastModifyTime,jdbcType=TIMESTAMP}");
        sql.SET("last_modify_user = #{record.lastModifyUser,jdbcType=VARCHAR}");
        
        CcPushPropertiesLogCriteria example = (CcPushPropertiesLogCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(CcPushPropertiesLog record) {

        SQL sql = new SQL();
        sql.UPDATE("push_properties_log");
        
        if (record.getApp() != null) {
            sql.SET("app = #{app,jdbcType=VARCHAR}");
        }
        
        if (record.getEnv() != null) {
            sql.SET("env = #{env,jdbcType=VARCHAR}");
        }
        
        if (record.getVersion() != null) {
            sql.SET("version = #{version,jdbcType=VARCHAR}");
        }
        
        if (record.getCluster() != null) {
            sql.SET("`cluster` = #{cluster,jdbcType=VARCHAR}");
        }
        
        if (record.getPushReason() != null) {
            sql.SET("push_reason = #{pushReason,jdbcType=BIT}");
        }
        
        if (record.getClientAddress() != null) {
            sql.SET("client_address = #{clientAddress,jdbcType=VARCHAR}");
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

    protected void applyWhere(SQL sql, CcPushPropertiesLogCriteria example, boolean includeExamplePhrase) {

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