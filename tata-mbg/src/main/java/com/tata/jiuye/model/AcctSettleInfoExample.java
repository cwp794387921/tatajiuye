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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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

        public Criteria andAcctIdEqualTo(String value) {
            addCriterion("acct_id =", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotEqualTo(String value) {
            addCriterion("acct_id <>", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThan(String value) {
            addCriterion("acct_id >", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThanOrEqualTo(String value) {
            addCriterion("acct_id >=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThan(String value) {
            addCriterion("acct_id <", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThanOrEqualTo(String value) {
            addCriterion("acct_id <=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLike(String value) {
            addCriterion("acct_id like", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotLike(String value) {
            addCriterion("acct_id not like", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdIn(List<String> values) {
            addCriterion("acct_id in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotIn(List<String> values) {
            addCriterion("acct_id not in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdBetween(String value1, String value2) {
            addCriterion("acct_id between", value1, value2, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotBetween(String value1, String value2) {
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

        public Criteria andAfterBalEqualTo(String value) {
            addCriterion("after_bal =", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotEqualTo(String value) {
            addCriterion("after_bal <>", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalGreaterThan(String value) {
            addCriterion("after_bal >", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalGreaterThanOrEqualTo(String value) {
            addCriterion("after_bal >=", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalLessThan(String value) {
            addCriterion("after_bal <", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalLessThanOrEqualTo(String value) {
            addCriterion("after_bal <=", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalLike(String value) {
            addCriterion("after_bal like", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotLike(String value) {
            addCriterion("after_bal not like", value, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalIn(List<String> values) {
            addCriterion("after_bal in", values, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotIn(List<String> values) {
            addCriterion("after_bal not in", values, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalBetween(String value1, String value2) {
            addCriterion("after_bal between", value1, value2, "afterBal");
            return (Criteria) this;
        }

        public Criteria andAfterBalNotBetween(String value1, String value2) {
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