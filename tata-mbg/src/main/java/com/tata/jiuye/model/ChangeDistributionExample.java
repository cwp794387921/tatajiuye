package com.tata.jiuye.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChangeDistributionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChangeDistributionExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdIsNull() {
            addCriterion("wms_member_id is null");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdIsNotNull() {
            addCriterion("wms_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdEqualTo(Long value) {
            addCriterion("wms_member_id =", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdNotEqualTo(Long value) {
            addCriterion("wms_member_id <>", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdGreaterThan(Long value) {
            addCriterion("wms_member_id >", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("wms_member_id >=", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdLessThan(Long value) {
            addCriterion("wms_member_id <", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("wms_member_id <=", value, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdIn(List<Long> values) {
            addCriterion("wms_member_id in", values, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdNotIn(List<Long> values) {
            addCriterion("wms_member_id not in", values, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdBetween(Long value1, Long value2) {
            addCriterion("wms_member_id between", value1, value2, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andWmsMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("wms_member_id not between", value1, value2, "wmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdIsNull() {
            addCriterion("change_member_id is null");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdIsNotNull() {
            addCriterion("change_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdEqualTo(Long value) {
            addCriterion("change_member_id =", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdNotEqualTo(Long value) {
            addCriterion("change_member_id <>", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdGreaterThan(Long value) {
            addCriterion("change_member_id >", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("change_member_id >=", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdLessThan(Long value) {
            addCriterion("change_member_id <", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("change_member_id <=", value, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdIn(List<Long> values) {
            addCriterion("change_member_id in", values, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdNotIn(List<Long> values) {
            addCriterion("change_member_id not in", values, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdBetween(Long value1, Long value2) {
            addCriterion("change_member_id between", value1, value2, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("change_member_id not between", value1, value2, "changeMemberId");
            return (Criteria) this;
        }

        public Criteria andOrderSnIsNull() {
            addCriterion("order_sn is null");
            return (Criteria) this;
        }

        public Criteria andOrderSnIsNotNull() {
            addCriterion("order_sn is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSnEqualTo(String value) {
            addCriterion("order_sn =", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotEqualTo(String value) {
            addCriterion("order_sn <>", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThan(String value) {
            addCriterion("order_sn >", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnGreaterThanOrEqualTo(String value) {
            addCriterion("order_sn >=", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThan(String value) {
            addCriterion("order_sn <", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnLessThanOrEqualTo(String value) {
            addCriterion("order_sn <=", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnLike(String value) {
            addCriterion("order_sn like", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotLike(String value) {
            addCriterion("order_sn not like", value, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnIn(List<String> values) {
            addCriterion("order_sn in", values, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotIn(List<String> values) {
            addCriterion("order_sn not in", values, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnBetween(String value1, String value2) {
            addCriterion("order_sn between", value1, value2, "orderSn");
            return (Criteria) this;
        }

        public Criteria andOrderSnNotBetween(String value1, String value2) {
            addCriterion("order_sn not between", value1, value2, "orderSn");
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