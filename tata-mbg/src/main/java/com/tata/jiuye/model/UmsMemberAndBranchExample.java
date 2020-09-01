package com.tata.jiuye.model;

import java.util.ArrayList;
import java.util.List;

public class UmsMemberAndBranchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UmsMemberAndBranchExample() {
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

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andIsBranchIsNull() {
            addCriterion("is_branch is null");
            return (Criteria) this;
        }

        public Criteria andIsBranchIsNotNull() {
            addCriterion("is_branch is not null");
            return (Criteria) this;
        }

        public Criteria andIsBranchEqualTo(Integer value) {
            addCriterion("is_branch =", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchNotEqualTo(Integer value) {
            addCriterion("is_branch <>", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchGreaterThan(Integer value) {
            addCriterion("is_branch >", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_branch >=", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchLessThan(Integer value) {
            addCriterion("is_branch <", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchLessThanOrEqualTo(Integer value) {
            addCriterion("is_branch <=", value, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchIn(List<Integer> values) {
            addCriterion("is_branch in", values, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchNotIn(List<Integer> values) {
            addCriterion("is_branch not in", values, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchBetween(Integer value1, Integer value2) {
            addCriterion("is_branch between", value1, value2, "isBranch");
            return (Criteria) this;
        }

        public Criteria andIsBranchNotBetween(Integer value1, Integer value2) {
            addCriterion("is_branch not between", value1, value2, "isBranch");
            return (Criteria) this;
        }

        public Criteria andParentIsNull() {
            addCriterion("parent is null");
            return (Criteria) this;
        }

        public Criteria andParentIsNotNull() {
            addCriterion("parent is not null");
            return (Criteria) this;
        }

        public Criteria andParentEqualTo(String value) {
            addCriterion("parent =", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotEqualTo(String value) {
            addCriterion("parent <>", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThan(String value) {
            addCriterion("parent >", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualTo(String value) {
            addCriterion("parent >=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThan(String value) {
            addCriterion("parent <", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualTo(String value) {
            addCriterion("parent <=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLike(String value) {
            addCriterion("parent like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotLike(String value) {
            addCriterion("parent not like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentIn(List<String> values) {
            addCriterion("parent in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotIn(List<String> values) {
            addCriterion("parent not in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentBetween(String value1, String value2) {
            addCriterion("parent between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotBetween(String value1, String value2) {
            addCriterion("parent not between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andUserBarnchIsNull() {
            addCriterion("user_barnch is null");
            return (Criteria) this;
        }

        public Criteria andUserBarnchIsNotNull() {
            addCriterion("user_barnch is not null");
            return (Criteria) this;
        }

        public Criteria andUserBarnchEqualTo(String value) {
            addCriterion("user_barnch =", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchNotEqualTo(String value) {
            addCriterion("user_barnch <>", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchGreaterThan(String value) {
            addCriterion("user_barnch >", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchGreaterThanOrEqualTo(String value) {
            addCriterion("user_barnch >=", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchLessThan(String value) {
            addCriterion("user_barnch <", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchLessThanOrEqualTo(String value) {
            addCriterion("user_barnch <=", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchLike(String value) {
            addCriterion("user_barnch like", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchNotLike(String value) {
            addCriterion("user_barnch not like", value, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchIn(List<String> values) {
            addCriterion("user_barnch in", values, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchNotIn(List<String> values) {
            addCriterion("user_barnch not in", values, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchBetween(String value1, String value2) {
            addCriterion("user_barnch between", value1, value2, "userBarnch");
            return (Criteria) this;
        }

        public Criteria andUserBarnchNotBetween(String value1, String value2) {
            addCriterion("user_barnch not between", value1, value2, "userBarnch");
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