package com.tata.jiuye.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WithdrawalExamineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WithdrawalExamineExample() {
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

        public Criteria andApplicantMemberIdIsNull() {
            addCriterion("applicant_member_id is null");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdIsNotNull() {
            addCriterion("applicant_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdEqualTo(Long value) {
            addCriterion("applicant_member_id =", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdNotEqualTo(Long value) {
            addCriterion("applicant_member_id <>", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdGreaterThan(Long value) {
            addCriterion("applicant_member_id >", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("applicant_member_id >=", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdLessThan(Long value) {
            addCriterion("applicant_member_id <", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("applicant_member_id <=", value, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdIn(List<Long> values) {
            addCriterion("applicant_member_id in", values, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdNotIn(List<Long> values) {
            addCriterion("applicant_member_id not in", values, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdBetween(Long value1, Long value2) {
            addCriterion("applicant_member_id between", value1, value2, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("applicant_member_id not between", value1, value2, "applicantMemberId");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameIsNull() {
            addCriterion("applicant_member_name is null");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameIsNotNull() {
            addCriterion("applicant_member_name is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameEqualTo(String value) {
            addCriterion("applicant_member_name =", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameNotEqualTo(String value) {
            addCriterion("applicant_member_name <>", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameGreaterThan(String value) {
            addCriterion("applicant_member_name >", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("applicant_member_name >=", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameLessThan(String value) {
            addCriterion("applicant_member_name <", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameLessThanOrEqualTo(String value) {
            addCriterion("applicant_member_name <=", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameLike(String value) {
            addCriterion("applicant_member_name like", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameNotLike(String value) {
            addCriterion("applicant_member_name not like", value, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameIn(List<String> values) {
            addCriterion("applicant_member_name in", values, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameNotIn(List<String> values) {
            addCriterion("applicant_member_name not in", values, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameBetween(String value1, String value2) {
            addCriterion("applicant_member_name between", value1, value2, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApplicantMemberNameNotBetween(String value1, String value2) {
            addCriterion("applicant_member_name not between", value1, value2, "applicantMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdIsNull() {
            addCriterion("approver_member_id is null");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdIsNotNull() {
            addCriterion("approver_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdEqualTo(Long value) {
            addCriterion("approver_member_id =", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdNotEqualTo(Long value) {
            addCriterion("approver_member_id <>", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdGreaterThan(Long value) {
            addCriterion("approver_member_id >", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("approver_member_id >=", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdLessThan(Long value) {
            addCriterion("approver_member_id <", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("approver_member_id <=", value, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdIn(List<Long> values) {
            addCriterion("approver_member_id in", values, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdNotIn(List<Long> values) {
            addCriterion("approver_member_id not in", values, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdBetween(Long value1, Long value2) {
            addCriterion("approver_member_id between", value1, value2, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("approver_member_id not between", value1, value2, "approverMemberId");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameIsNull() {
            addCriterion("approver_member_name is null");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameIsNotNull() {
            addCriterion("approver_member_name is not null");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameEqualTo(String value) {
            addCriterion("approver_member_name =", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameNotEqualTo(String value) {
            addCriterion("approver_member_name <>", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameGreaterThan(String value) {
            addCriterion("approver_member_name >", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("approver_member_name >=", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameLessThan(String value) {
            addCriterion("approver_member_name <", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameLessThanOrEqualTo(String value) {
            addCriterion("approver_member_name <=", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameLike(String value) {
            addCriterion("approver_member_name like", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameNotLike(String value) {
            addCriterion("approver_member_name not like", value, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameIn(List<String> values) {
            addCriterion("approver_member_name in", values, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameNotIn(List<String> values) {
            addCriterion("approver_member_name not in", values, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameBetween(String value1, String value2) {
            addCriterion("approver_member_name between", value1, value2, "approverMemberName");
            return (Criteria) this;
        }

        public Criteria andApproverMemberNameNotBetween(String value1, String value2) {
            addCriterion("approver_member_name not between", value1, value2, "approverMemberName");
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

        public Criteria andWithdrawalAmountIsNull() {
            addCriterion("withdrawal_amount is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountIsNotNull() {
            addCriterion("withdrawal_amount is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountEqualTo(BigDecimal value) {
            addCriterion("withdrawal_amount =", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountNotEqualTo(BigDecimal value) {
            addCriterion("withdrawal_amount <>", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountGreaterThan(BigDecimal value) {
            addCriterion("withdrawal_amount >", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawal_amount >=", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountLessThan(BigDecimal value) {
            addCriterion("withdrawal_amount <", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdrawal_amount <=", value, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountIn(List<BigDecimal> values) {
            addCriterion("withdrawal_amount in", values, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountNotIn(List<BigDecimal> values) {
            addCriterion("withdrawal_amount not in", values, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawal_amount between", value1, value2, "withdrawalAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdrawal_amount not between", value1, value2, "withdrawalAmount");
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

        public Criteria andAcctSettleInfoIdIsNull() {
            addCriterion("acct_settle_info_id is null");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdIsNotNull() {
            addCriterion("acct_settle_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdEqualTo(Long value) {
            addCriterion("acct_settle_info_id =", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdNotEqualTo(Long value) {
            addCriterion("acct_settle_info_id <>", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdGreaterThan(Long value) {
            addCriterion("acct_settle_info_id >", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("acct_settle_info_id >=", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdLessThan(Long value) {
            addCriterion("acct_settle_info_id <", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdLessThanOrEqualTo(Long value) {
            addCriterion("acct_settle_info_id <=", value, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdIn(List<Long> values) {
            addCriterion("acct_settle_info_id in", values, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdNotIn(List<Long> values) {
            addCriterion("acct_settle_info_id not in", values, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdBetween(Long value1, Long value2) {
            addCriterion("acct_settle_info_id between", value1, value2, "acctSettleInfoId");
            return (Criteria) this;
        }

        public Criteria andAcctSettleInfoIdNotBetween(Long value1, Long value2) {
            addCriterion("acct_settle_info_id not between", value1, value2, "acctSettleInfoId");
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