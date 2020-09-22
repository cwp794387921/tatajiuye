package com.tata.jiuye.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WmsMemberCreditlineChangeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WmsMemberCreditlineChangeExample() {
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

        public Criteria andChangeValueIsNull() {
            addCriterion("change_value is null");
            return (Criteria) this;
        }

        public Criteria andChangeValueIsNotNull() {
            addCriterion("change_value is not null");
            return (Criteria) this;
        }

        public Criteria andChangeValueEqualTo(BigDecimal value) {
            addCriterion("change_value =", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueNotEqualTo(BigDecimal value) {
            addCriterion("change_value <>", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueGreaterThan(BigDecimal value) {
            addCriterion("change_value >", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("change_value >=", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueLessThan(BigDecimal value) {
            addCriterion("change_value <", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("change_value <=", value, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueIn(List<BigDecimal> values) {
            addCriterion("change_value in", values, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueNotIn(List<BigDecimal> values) {
            addCriterion("change_value not in", values, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change_value between", value1, value2, "changeValue");
            return (Criteria) this;
        }

        public Criteria andChangeValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change_value not between", value1, value2, "changeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIsNull() {
            addCriterion("before_value is null");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIsNotNull() {
            addCriterion("before_value is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeValueEqualTo(BigDecimal value) {
            addCriterion("before_value =", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotEqualTo(BigDecimal value) {
            addCriterion("before_value <>", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueGreaterThan(BigDecimal value) {
            addCriterion("before_value >", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_value >=", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueLessThan(BigDecimal value) {
            addCriterion("before_value <", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_value <=", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIn(List<BigDecimal> values) {
            addCriterion("before_value in", values, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotIn(List<BigDecimal> values) {
            addCriterion("before_value not in", values, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_value between", value1, value2, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_value not between", value1, value2, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueIsNull() {
            addCriterion("after_value is null");
            return (Criteria) this;
        }

        public Criteria andAfterValueIsNotNull() {
            addCriterion("after_value is not null");
            return (Criteria) this;
        }

        public Criteria andAfterValueEqualTo(BigDecimal value) {
            addCriterion("after_value =", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotEqualTo(BigDecimal value) {
            addCriterion("after_value <>", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueGreaterThan(BigDecimal value) {
            addCriterion("after_value >", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_value >=", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueLessThan(BigDecimal value) {
            addCriterion("after_value <", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_value <=", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueIn(List<BigDecimal> values) {
            addCriterion("after_value in", values, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotIn(List<BigDecimal> values) {
            addCriterion("after_value not in", values, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_value between", value1, value2, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_value not between", value1, value2, "afterValue");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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