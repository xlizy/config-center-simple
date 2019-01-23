package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.enums.Enable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CcSnapshotDetailsCriteria {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CcSnapshotDetailsCriteria() {

        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {

        return orderByClause;
    }

    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    public boolean isDistinct() {

        return distinct;
    }

    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {

        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {

            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {

            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {

            return criteria;
        }

        public List<Criterion> getCriteria() {

            return criteria;
        }

        protected void addCriterion(String condition) {

            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {

            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {

            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {

            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {

            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {

            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {

            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {

            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {

            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {

            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {

            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {

            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {

            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {

            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {

            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdIsNull() {

            addCriterion("snapshot_id is null");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdIsNotNull() {

            addCriterion("snapshot_id is not null");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdEqualTo(Integer value) {

            addCriterion("snapshot_id =", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdNotEqualTo(Integer value) {

            addCriterion("snapshot_id <>", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdGreaterThan(Integer value) {

            addCriterion("snapshot_id >", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdGreaterThanOrEqualTo(Integer value) {

            addCriterion("snapshot_id >=", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdLessThan(Integer value) {

            addCriterion("snapshot_id <", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdLessThanOrEqualTo(Integer value) {

            addCriterion("snapshot_id <=", value, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdIn(List<Integer> values) {

            addCriterion("snapshot_id in", values, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdNotIn(List<Integer> values) {

            addCriterion("snapshot_id not in", values, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdBetween(Integer value1, Integer value2) {

            addCriterion("snapshot_id between", value1, value2, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andSnapshotIdNotBetween(Integer value1, Integer value2) {

            addCriterion("snapshot_id not between", value1, value2, "snapshotId");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyIsNull() {

            addCriterion("properties_key is null");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyIsNotNull() {

            addCriterion("properties_key is not null");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyEqualTo(String value) {

            addCriterion("properties_key =", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyNotEqualTo(String value) {

            addCriterion("properties_key <>", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyGreaterThan(String value) {

            addCriterion("properties_key >", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyGreaterThanOrEqualTo(String value) {

            addCriterion("properties_key >=", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyLessThan(String value) {

            addCriterion("properties_key <", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyLessThanOrEqualTo(String value) {

            addCriterion("properties_key <=", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyLike(String value) {

            addCriterion("properties_key like", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyNotLike(String value) {

            addCriterion("properties_key not like", value, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyIn(List<String> values) {

            addCriterion("properties_key in", values, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyNotIn(List<String> values) {

            addCriterion("properties_key not in", values, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyBetween(String value1, String value2) {

            addCriterion("properties_key between", value1, value2, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesKeyNotBetween(String value1, String value2) {

            addCriterion("properties_key not between", value1, value2, "propertiesKey");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueIsNull() {

            addCriterion("properties_value is null");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueIsNotNull() {

            addCriterion("properties_value is not null");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueEqualTo(String value) {

            addCriterion("properties_value =", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueNotEqualTo(String value) {

            addCriterion("properties_value <>", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueGreaterThan(String value) {

            addCriterion("properties_value >", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueGreaterThanOrEqualTo(String value) {

            addCriterion("properties_value >=", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueLessThan(String value) {

            addCriterion("properties_value <", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueLessThanOrEqualTo(String value) {

            addCriterion("properties_value <=", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueLike(String value) {

            addCriterion("properties_value like", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueNotLike(String value) {

            addCriterion("properties_value not like", value, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueIn(List<String> values) {

            addCriterion("properties_value in", values, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueNotIn(List<String> values) {

            addCriterion("properties_value not in", values, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueBetween(String value1, String value2) {

            addCriterion("properties_value between", value1, value2, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesValueNotBetween(String value1, String value2) {

            addCriterion("properties_value not between", value1, value2, "propertiesValue");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameIsNull() {

            addCriterion("properties_name is null");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameIsNotNull() {

            addCriterion("properties_name is not null");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameEqualTo(String value) {

            addCriterion("properties_name =", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameNotEqualTo(String value) {

            addCriterion("properties_name <>", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameGreaterThan(String value) {

            addCriterion("properties_name >", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameGreaterThanOrEqualTo(String value) {

            addCriterion("properties_name >=", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameLessThan(String value) {

            addCriterion("properties_name <", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameLessThanOrEqualTo(String value) {

            addCriterion("properties_name <=", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameLike(String value) {

            addCriterion("properties_name like", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameNotLike(String value) {

            addCriterion("properties_name not like", value, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameIn(List<String> values) {

            addCriterion("properties_name in", values, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameNotIn(List<String> values) {

            addCriterion("properties_name not in", values, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameBetween(String value1, String value2) {

            addCriterion("properties_name between", value1, value2, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesNameNotBetween(String value1, String value2) {

            addCriterion("properties_name not between", value1, value2, "propertiesName");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableIsNull() {

            addCriterion("properties_enable is null");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableIsNotNull() {

            addCriterion("properties_enable is not null");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableEqualTo(Enable value) {

            addCriterion("properties_enable =", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableNotEqualTo(Enable value) {

            addCriterion("properties_enable <>", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableGreaterThan(Enable value) {

            addCriterion("properties_enable >", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableGreaterThanOrEqualTo(Enable value) {

            addCriterion("properties_enable >=", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableLessThan(Enable value) {

            addCriterion("properties_enable <", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableLessThanOrEqualTo(Enable value) {

            addCriterion("properties_enable <=", value, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableIn(List<Enable> values) {

            addCriterion("properties_enable in", values, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableNotIn(List<Enable> values) {

            addCriterion("properties_enable not in", values, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableBetween(Enable value1, Enable value2) {

            addCriterion("properties_enable between", value1, value2, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andPropertiesEnableNotBetween(Enable value1, Enable value2) {

            addCriterion("properties_enable not between", value1, value2, "propertiesEnable");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {

            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {

            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {

            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {

            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {

            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {

            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {

            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {

            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {

            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {

            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {

            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {

            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {

            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {

            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {

            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {

            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {

            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {

            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {

            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {

            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {

            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {

            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {

            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {

            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {

            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {

            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNull() {

            addCriterion("last_modify_time is null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNotNull() {

            addCriterion("last_modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeEqualTo(Date value) {

            addCriterion("last_modify_time =", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotEqualTo(Date value) {

            addCriterion("last_modify_time <>", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThan(Date value) {

            addCriterion("last_modify_time >", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThanOrEqualTo(Date value) {

            addCriterion("last_modify_time >=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThan(Date value) {

            addCriterion("last_modify_time <", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThanOrEqualTo(Date value) {

            addCriterion("last_modify_time <=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIn(List<Date> values) {

            addCriterion("last_modify_time in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotIn(List<Date> values) {

            addCriterion("last_modify_time not in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeBetween(Date value1, Date value2) {

            addCriterion("last_modify_time between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotBetween(Date value1, Date value2) {

            addCriterion("last_modify_time not between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIsNull() {

            addCriterion("last_modify_user is null");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIsNotNull() {

            addCriterion("last_modify_user is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserEqualTo(String value) {

            addCriterion("last_modify_user =", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserNotEqualTo(String value) {

            addCriterion("last_modify_user <>", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserGreaterThan(String value) {

            addCriterion("last_modify_user >", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserGreaterThanOrEqualTo(String value) {

            addCriterion("last_modify_user >=", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserLessThan(String value) {

            addCriterion("last_modify_user <", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserLessThanOrEqualTo(String value) {

            addCriterion("last_modify_user <=", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserLike(String value) {

            addCriterion("last_modify_user like", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserNotLike(String value) {

            addCriterion("last_modify_user not like", value, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIn(List<String> values) {

            addCriterion("last_modify_user in", values, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserNotIn(List<String> values) {

            addCriterion("last_modify_user not in", values, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserBetween(String value1, String value2) {

            addCriterion("last_modify_user between", value1, value2, "lastModifyUser");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserNotBetween(String value1, String value2) {

            addCriterion("last_modify_user not between", value1, value2, "lastModifyUser");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {

            super();
        }
    }

    public static class Criterion {

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {

            return condition;
        }

        public Object getValue() {

            return value;
        }

        public Object getSecondValue() {

            return secondValue;
        }

        public boolean isNoValue() {

            return noValue;
        }

        public boolean isSingleValue() {

            return singleValue;
        }

        public boolean isBetweenValue() {

            return betweenValue;
        }

        public boolean isListValue() {

            return listValue;
        }

        public String getTypeHandler() {

            return typeHandler;
        }

        protected Criterion(String condition) {

            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {

            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {

            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {

            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {

            this(condition, value, secondValue, null);
        }
    }
}