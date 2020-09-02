package com.tata.jiuye.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcctSettleInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AcctSettleInfoExample() {
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

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andAcctIdIsNull() {
            addCriterion("acct_id is null");
            return (Criteria) this;
        }

        public Criteria andAcctIdIsNotNull() {
            addCriterion("acct_id is not null");
            return (Criteria) this;
        }

        public Criteria andAcctIdEqualTo(Long value) {
            addCriterion("acct_id =", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotEqualTo(Long value) {
            addCriterion("acct_id <>", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThan(Long value) {
            addCriterion("acct_id >", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThanOrEqualTo(Long value) {
            addCriterion("acct_id >=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThan(Long value) {
            addCriterion("acct_id <", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThanOrEqualTo(Long value) {
            addCriterion("acct_id <=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdIn(List<Long> values) {
            addCriterion("acct_id in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotIn(List<Long> values) {
            addCriterion("acct_id not in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdBetween(Long value1, Long value2) {
            addCriterion("acct_id between", value1, value2, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotBetween(Long value1, Long value2) {
            addCriterion("acct_id not between", value1, value2, "acctId");
            return (Criteria) this;
        }

        public Criteria andBeforBalIsNull() {
            addCriterion("befor_bal is null");
            return (Criteria) this;
        }

        public Criteria andBeforBalIsNotNull() {
            addCriterion("befor_bal is not null");
            return (Criteria) this;
        }

        public Criteria andBeforBalEqualTo(BigDecimal value) {
            addCriterion("befor_bal =", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalNotEqualTo(BigDecimal value) {
            addCriterion("befor_bal <>", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalGreaterThan(BigDecimal value) {
            addCriterion("befor_bal >", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("befor_bal >=", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalLessThan(BigDecimal value) {
            addCriterion("befor_bal <", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("befor_bal <=", value, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalIn(List<BigDecimal> values) {
            addCriterion("befor_bal in", values, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalNotIn(List<BigDecimal> values) {
            addCriterion("befor_bal not in", values, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("befor_bal between", value1, value2, "beforBal");
            return (Criteria) this;
        }

        public Criteria andBeforBalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("befor_bal not between", value1, value2, "beforBal");
            return (Criteria) this;
        }

        public Criteria andChangeIsNull() {
            addCriterion("change is null");
            return (Criteria) this;
        }

        public Criteria andChangeIsNotNull() {
            addCriterion("change is not null");
            return (Criteria) this;
        }

        public Criteria andChangeEqualTo(BigDecimal value) {
            addCriterion("change =", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeNotEqualTo(BigDecimal value) {
            addCriterion("change <>", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeGreaterThan(BigDecimal value) {
            addCriterion("change >", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("change >=", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeLessThan(BigDecimal value) {
            addCriterion("change <", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("change <=", value, "change");
            return (Criteria) this;
        }

        public Criteria andChangeIn(List<BigDecimal> values) {
            addCriterion("change in", values, "change");
            return (Criteria) this;
        }

        public Criteria andChangeNotIn(List<BigDecimal> values) {
            addCriterion("change not in", values, "change");
            return (Criteria) this;
        }

        public Criteria andChangeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change between", value1, value2, "change");
            return (Criteria) this;
        }

        public Criteria andChangeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("change not between", value1, value2, "change");
            return (Criteria) this;
        }

        public Criteria andAfterBalIsNull() {
            addCriterion("after_bal is null");
            return (Criteria) this;
        }

        public Criteria andAfterBalIsNotNull() {
            addCriterion("after_bal is not null");
            return (Criteria) this;
        }

        public Criteria andAfterBalEqualTo(BigDecimal value) {
            addCriterion("after_bal =", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotEqualTo(BigDecimal value) {
            addCriterion("after_bal <>", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalGreaterThan(BigDecimal value) {
            addCriterion("after_bal >", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_bal >=", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalLessThan(BigDecimal value) {
            addCriterion("after_bal <", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_bal <=", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalIn(List<BigDecimal> values) {
            addCriterion("after_bal in", values, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotIn(List<BigDecimal> values) {
            addCriterion("after_bal not in", values, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_bal between", value1, value2, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_bal not between", value1, value2, "afterBal");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNull() {
            addCriterion("insert_time is null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNotNull() {
            addCriterion("insert_time is not null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeEqualTo(Date value) {
            addCriterion("insert_time =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(Date value) {
            addCriterion("insert_time <>", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThan(Date value) {
            addCriterion("insert_time >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("insert_time >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(Date value) {
            addCriterion("insert_time <", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThanOrEqualTo(Date value) {
            addCriterion("insert_time <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<Date> values) {
            addCriterion("insert_time in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<Date> values) {
            addCriterion("insert_time not in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeBetween(Date value1, Date value2) {
            addCriterion("insert_time between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotBetween(Date value1, Date value2) {
            addCriterion("insert_time not between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andFlowTypeIsNull() {
            addCriterion("flow_type is null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeIsNotNull() {
            addCriterion("flow_type is not null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeEqualTo(String value) {
            addCriterion("flow_type =", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotEqualTo(String value) {
            addCriterion("flow_type <>", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeGreaterThan(String value) {
            addCriterion("flow_type >", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeGreaterThanOrEqualTo(String value) {
            addCriterion("flow_type >=", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeLessThan(String value) {
            addCriterion("flow_type <", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeLessThanOrEqualTo(String value) {
            addCriterion("flow_type <=", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeLike(String value) {
            addCriterion("flow_type like", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotLike(String value) {
            addCriterion("flow_type not like", value, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeIn(List<String> values) {
            addCriterion("flow_type in", values, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotIn(List<String> values) {
            addCriterion("flow_type not in", values, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeBetween(String value1, String value2) {
            addCriterion("flow_type between", value1, value2, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeNotBetween(String value1, String value2) {
            addCriterion("flow_type not between", value1, value2, "flowType");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailIsNull() {
            addCriterion("flow_type_detail is null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailIsNotNull() {
            addCriterion("flow_type_detail is not null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailEqualTo(String value) {
            addCriterion("flow_type_detail =", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailNotEqualTo(String value) {
            addCriterion("flow_type_detail <>", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailGreaterThan(String value) {
            addCriterion("flow_type_detail >", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailGreaterThanOrEqualTo(String value) {
            addCriterion("flow_type_detail >=", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailLessThan(String value) {
            addCriterion("flow_type_detail <", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailLessThanOrEqualTo(String value) {
            addCriterion("flow_type_detail <=", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailLike(String value) {
            addCriterion("flow_type_detail like", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailNotLike(String value) {
            addCriterion("flow_type_detail not like", value, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailIn(List<String> values) {
            addCriterion("flow_type_detail in", values, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailNotIn(List<String> values) {
            addCriterion("flow_type_detail not in", values, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailBetween(String value1, String value2) {
            addCriterion("flow_type_detail between", value1, value2, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andFlowTypeDetailNotBetween(String value1, String value2) {
            addCriterion("flow_type_detail not between", value1, value2, "flowTypeDetail");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Long value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Long value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Long value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Long value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Long value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Long> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Long> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Long value1, Long value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Long value1, Long value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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