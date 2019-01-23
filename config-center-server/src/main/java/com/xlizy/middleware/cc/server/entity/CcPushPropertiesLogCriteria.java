package com.xlizy.middleware.cc.server.entity;

import com.xlizy.middleware.cc.server.enums.SendConfigType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CcPushPropertiesLogCriteria {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CcPushPropertiesLogCriteria() {

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

        public Criteria andAppIsNull() {

            addCriterion("app is null");
            return (Criteria) this;
        }

        public Criteria andAppIsNotNull() {

            addCriterion("app is not null");
            return (Criteria) this;
        }

        public Criteria andAppEqualTo(String value) {

            addCriterion("app =", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotEqualTo(String value) {

            addCriterion("app <>", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppGreaterThan(String value) {

            addCriterion("app >", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppGreaterThanOrEqualTo(String value) {

            addCriterion("app >=", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLessThan(String value) {

            addCriterion("app <", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLessThanOrEqualTo(String value) {

            addCriterion("app <=", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLike(String value) {

            addCriterion("app like", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotLike(String value) {

            addCriterion("app not like", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppIn(List<String> values) {

            addCriterion("app in", values, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotIn(List<String> values) {

            addCriterion("app not in", values, "app");
            return (Criteria) this;
        }

        public Criteria andAppBetween(String value1, String value2) {

            addCriterion("app between", value1, value2, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotBetween(String value1, String value2) {

            addCriterion("app not between", value1, value2, "app");
            return (Criteria) this;
        }

        public Criteria andEnvIsNull() {

            addCriterion("env is null");
            return (Criteria) this;
        }

        public Criteria andEnvIsNotNull() {

            addCriterion("env is not null");
            return (Criteria) this;
        }

        public Criteria andEnvEqualTo(String value) {

            addCriterion("env =", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotEqualTo(String value) {

            addCriterion("env <>", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThan(String value) {

            addCriterion("env >", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThanOrEqualTo(String value) {

            addCriterion("env >=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThan(String value) {

            addCriterion("env <", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThanOrEqualTo(String value) {

            addCriterion("env <=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLike(String value) {

            addCriterion("env like", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotLike(String value) {

            addCriterion("env not like", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvIn(List<String> values) {

            addCriterion("env in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotIn(List<String> values) {

            addCriterion("env not in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvBetween(String value1, String value2) {

            addCriterion("env between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotBetween(String value1, String value2) {

            addCriterion("env not between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {

            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {

            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {

            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {

            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {

            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {

            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {

            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {

            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {

            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {

            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {

            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {

            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {

            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {

            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andClusterIsNull() {

            addCriterion("`cluster` is null");
            return (Criteria) this;
        }

        public Criteria andClusterIsNotNull() {

            addCriterion("`cluster` is not null");
            return (Criteria) this;
        }

        public Criteria andClusterEqualTo(String value) {

            addCriterion("`cluster` =", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterNotEqualTo(String value) {

            addCriterion("`cluster` <>", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterGreaterThan(String value) {

            addCriterion("`cluster` >", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterGreaterThanOrEqualTo(String value) {

            addCriterion("`cluster` >=", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterLessThan(String value) {

            addCriterion("`cluster` <", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterLessThanOrEqualTo(String value) {

            addCriterion("`cluster` <=", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterLike(String value) {

            addCriterion("`cluster` like", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterNotLike(String value) {

            addCriterion("`cluster` not like", value, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterIn(List<String> values) {

            addCriterion("`cluster` in", values, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterNotIn(List<String> values) {

            addCriterion("`cluster` not in", values, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterBetween(String value1, String value2) {

            addCriterion("`cluster` between", value1, value2, "cluster");
            return (Criteria) this;
        }

        public Criteria andClusterNotBetween(String value1, String value2) {

            addCriterion("`cluster` not between", value1, value2, "cluster");
            return (Criteria) this;
        }

        public Criteria andPushReasonIsNull() {

            addCriterion("push_reason is null");
            return (Criteria) this;
        }

        public Criteria andPushReasonIsNotNull() {

            addCriterion("push_reason is not null");
            return (Criteria) this;
        }

        public Criteria andPushReasonEqualTo(SendConfigType value) {

            addCriterion("push_reason =", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonNotEqualTo(SendConfigType value) {

            addCriterion("push_reason <>", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonGreaterThan(SendConfigType value) {

            addCriterion("push_reason >", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonGreaterThanOrEqualTo(SendConfigType value) {

            addCriterion("push_reason >=", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonLessThan(SendConfigType value) {

            addCriterion("push_reason <", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonLessThanOrEqualTo(SendConfigType value) {

            addCriterion("push_reason <=", value, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonIn(List<SendConfigType> values) {

            addCriterion("push_reason in", values, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonNotIn(List<SendConfigType> values) {

            addCriterion("push_reason not in", values, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonBetween(SendConfigType value1, SendConfigType value2) {

            addCriterion("push_reason between", value1, value2, "pushReason");
            return (Criteria) this;
        }

        public Criteria andPushReasonNotBetween(SendConfigType value1, SendConfigType value2) {

            addCriterion("push_reason not between", value1, value2, "pushReason");
            return (Criteria) this;
        }

        public Criteria andClientAddressIsNull() {

            addCriterion("client_address is null");
            return (Criteria) this;
        }

        public Criteria andClientAddressIsNotNull() {

            addCriterion("client_address is not null");
            return (Criteria) this;
        }

        public Criteria andClientAddressEqualTo(String value) {

            addCriterion("client_address =", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressNotEqualTo(String value) {

            addCriterion("client_address <>", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressGreaterThan(String value) {

            addCriterion("client_address >", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressGreaterThanOrEqualTo(String value) {

            addCriterion("client_address >=", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressLessThan(String value) {

            addCriterion("client_address <", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressLessThanOrEqualTo(String value) {

            addCriterion("client_address <=", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressLike(String value) {

            addCriterion("client_address like", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressNotLike(String value) {

            addCriterion("client_address not like", value, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressIn(List<String> values) {

            addCriterion("client_address in", values, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressNotIn(List<String> values) {

            addCriterion("client_address not in", values, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressBetween(String value1, String value2) {

            addCriterion("client_address between", value1, value2, "clientAddress");
            return (Criteria) this;
        }

        public Criteria andClientAddressNotBetween(String value1, String value2) {

            addCriterion("client_address not between", value1, value2, "clientAddress");
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