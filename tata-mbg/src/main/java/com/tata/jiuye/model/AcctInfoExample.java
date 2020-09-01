package com.tata.jiuye.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcctInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AcctInfoExample() {
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

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andMemberNoIsNull() {
            addCriterion("member_no is null");
            return (Criteria) this;
        }

        public Criteria andMemberNoIsNotNull() {
            addCriterion("member_no is not null");
            return (Criteria) this;
        }

        public Criteria andMemberNoEqualTo(String value) {
            addCriterion("member_no =", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotEqualTo(String value) {
            addCriterion("member_no <>", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoGreaterThan(String value) {
            addCriterion("member_no >", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoGreaterThanOrEqualTo(String value) {
            addCriterion("member_no >=", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLessThan(String value) {
            addCriterion("member_no <", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLessThanOrEqualTo(String value) {
            addCriterion("member_no <=", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoLike(String value) {
            addCriterion("member_no like", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotLike(String value) {
            addCriterion("member_no not like", value, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoIn(List<String> values) {
            addCriterion("member_no in", values, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotIn(List<String> values) {
            addCriterion("member_no not in", values, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoBetween(String value1, String value2) {
            addCriterion("member_no between", value1, value2, "memberNo");
            return (Criteria) this;
        }

        public Criteria andMemberNoNotBetween(String value1, String value2) {
            addCriterion("member_no not between", value1, value2, "memberNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoIsNull() {
            addCriterion("branch_no is null");
            return (Criteria) this;
        }

        public Criteria andBranchNoIsNotNull() {
            addCriterion("branch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNoEqualTo(String value) {
            addCriterion("branch_no =", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoNotEqualTo(String value) {
            addCriterion("branch_no <>", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoGreaterThan(String value) {
            addCriterion("branch_no >", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoGreaterThanOrEqualTo(String value) {
            addCriterion("branch_no >=", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoLessThan(String value) {
            addCriterion("branch_no <", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoLessThanOrEqualTo(String value) {
            addCriterion("branch_no <=", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoLike(String value) {
            addCriterion("branch_no like", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoNotLike(String value) {
            addCriterion("branch_no not like", value, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoIn(List<String> values) {
            addCriterion("branch_no in", values, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoNotIn(List<String> values) {
            addCriterion("branch_no not in", values, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoBetween(String value1, String value2) {
            addCriterion("branch_no between", value1, value2, "branchNo");
            return (Criteria) this;
        }

        public Criteria andBranchNoNotBetween(String value1, String value2) {
            addCriterion("branch_no not between", value1, value2, "branchNo");
            return (Criteria) this;
        }

        public Criteria andEffDateIsNull() {
            addCriterion("eff_date is null");
            return (Criteria) this;
        }

        public Criteria andEffDateIsNotNull() {
            addCriterion("eff_date is not null");
            return (Criteria) this;
        }

        public Criteria andEffDateEqualTo(Date value) {
            addCriterion("eff_date =", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateNotEqualTo(Date value) {
            addCriterion("eff_date <>", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateGreaterThan(Date value) {
            addCriterion("eff_date >", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateGreaterThanOrEqualTo(Date value) {
            addCriterion("eff_date >=", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateLessThan(Date value) {
            addCriterion("eff_date <", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateLessThanOrEqualTo(Date value) {
            addCriterion("eff_date <=", value, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateIn(List<Date> values) {
            addCriterion("eff_date in", values, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateNotIn(List<Date> values) {
            addCriterion("eff_date not in", values, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateBetween(Date value1, Date value2) {
            addCriterion("eff_date between", value1, value2, "effDate");
            return (Criteria) this;
        }

        public Criteria andEffDateNotBetween(Date value1, Date value2) {
            addCriterion("eff_date not between", value1, value2, "effDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateIsNull() {
            addCriterion("expir_date is null");
            return (Criteria) this;
        }

        public Criteria andExpirDateIsNotNull() {
            addCriterion("expir_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpirDateEqualTo(Date value) {
            addCriterion("expir_date =", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateNotEqualTo(Date value) {
            addCriterion("expir_date <>", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateGreaterThan(Date value) {
            addCriterion("expir_date >", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateGreaterThanOrEqualTo(Date value) {
            addCriterion("expir_date >=", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateLessThan(Date value) {
            addCriterion("expir_date <", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateLessThanOrEqualTo(Date value) {
            addCriterion("expir_date <=", value, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateIn(List<Date> values) {
            addCriterion("expir_date in", values, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateNotIn(List<Date> values) {
            addCriterion("expir_date not in", values, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateBetween(Date value1, Date value2) {
            addCriterion("expir_date between", value1, value2, "expirDate");
            return (Criteria) this;
        }

        public Criteria andExpirDateNotBetween(Date value1, Date value2) {
            addCriterion("expir_date not between", value1, value2, "expirDate");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andRemakeIsNull() {
            addCriterion("remake is null");
            return (Criteria) this;
        }

        public Criteria andRemakeIsNotNull() {
            addCriterion("remake is not null");
            return (Criteria) this;
        }

        public Criteria andRemakeEqualTo(String value) {
            addCriterion("remake =", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeNotEqualTo(String value) {
            addCriterion("remake <>", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeGreaterThan(String value) {
            addCriterion("remake >", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeGreaterThanOrEqualTo(String value) {
            addCriterion("remake >=", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeLessThan(String value) {
            addCriterion("remake <", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeLessThanOrEqualTo(String value) {
            addCriterion("remake <=", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeLike(String value) {
            addCriterion("remake like", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeNotLike(String value) {
            addCriterion("remake not like", value, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeIn(List<String> values) {
            addCriterion("remake in", values, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeNotIn(List<String> values) {
            addCriterion("remake not in", values, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeBetween(String value1, String value2) {
            addCriterion("remake between", value1, value2, "remake");
            return (Criteria) this;
        }

        public Criteria andRemakeNotBetween(String value1, String value2) {
            addCriterion("remake not between", value1, value2, "remake");
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