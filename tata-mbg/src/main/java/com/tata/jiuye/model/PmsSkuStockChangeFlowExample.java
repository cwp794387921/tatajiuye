package com.tata.jiuye.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmsSkuStockChangeFlowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmsSkuStockChangeFlowExample() {
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

        public Criteria andPmsSkuStockIdIsNull() {
            addCriterion("pms_sku_stock_id is null");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdIsNotNull() {
            addCriterion("pms_sku_stock_id is not null");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdEqualTo(Long value) {
            addCriterion("pms_sku_stock_id =", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdNotEqualTo(Long value) {
            addCriterion("pms_sku_stock_id <>", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdGreaterThan(Long value) {
            addCriterion("pms_sku_stock_id >", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdGreaterThanOrEqualTo(Long value) {
            addCriterion("pms_sku_stock_id >=", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdLessThan(Long value) {
            addCriterion("pms_sku_stock_id <", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdLessThanOrEqualTo(Long value) {
            addCriterion("pms_sku_stock_id <=", value, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdIn(List<Long> values) {
            addCriterion("pms_sku_stock_id in", values, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdNotIn(List<Long> values) {
            addCriterion("pms_sku_stock_id not in", values, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdBetween(Long value1, Long value2) {
            addCriterion("pms_sku_stock_id between", value1, value2, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andPmsSkuStockIdNotBetween(Long value1, Long value2) {
            addCriterion("pms_sku_stock_id not between", value1, value2, "pmsSkuStockId");
            return (Criteria) this;
        }

        public Criteria andChangeNumIsNull() {
            addCriterion("change_num is null");
            return (Criteria) this;
        }

        public Criteria andChangeNumIsNotNull() {
            addCriterion("change_num is not null");
            return (Criteria) this;
        }

        public Criteria andChangeNumEqualTo(Integer value) {
            addCriterion("change_num =", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumNotEqualTo(Integer value) {
            addCriterion("change_num <>", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumGreaterThan(Integer value) {
            addCriterion("change_num >", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_num >=", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumLessThan(Integer value) {
            addCriterion("change_num <", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumLessThanOrEqualTo(Integer value) {
            addCriterion("change_num <=", value, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumIn(List<Integer> values) {
            addCriterion("change_num in", values, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumNotIn(List<Integer> values) {
            addCriterion("change_num not in", values, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumBetween(Integer value1, Integer value2) {
            addCriterion("change_num between", value1, value2, "changeNum");
            return (Criteria) this;
        }

        public Criteria andChangeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("change_num not between", value1, value2, "changeNum");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeIsNull() {
            addCriterion("quantity_before_change is null");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeIsNotNull() {
            addCriterion("quantity_before_change is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeEqualTo(Integer value) {
            addCriterion("quantity_before_change =", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeNotEqualTo(Integer value) {
            addCriterion("quantity_before_change <>", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeGreaterThan(Integer value) {
            addCriterion("quantity_before_change >", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity_before_change >=", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeLessThan(Integer value) {
            addCriterion("quantity_before_change <", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeLessThanOrEqualTo(Integer value) {
            addCriterion("quantity_before_change <=", value, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeIn(List<Integer> values) {
            addCriterion("quantity_before_change in", values, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeNotIn(List<Integer> values) {
            addCriterion("quantity_before_change not in", values, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeBetween(Integer value1, Integer value2) {
            addCriterion("quantity_before_change between", value1, value2, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityBeforeChangeNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity_before_change not between", value1, value2, "quantityBeforeChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeIsNull() {
            addCriterion("quantity_after_change is null");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeIsNotNull() {
            addCriterion("quantity_after_change is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeEqualTo(Integer value) {
            addCriterion("quantity_after_change =", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeNotEqualTo(Integer value) {
            addCriterion("quantity_after_change <>", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeGreaterThan(Integer value) {
            addCriterion("quantity_after_change >", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity_after_change >=", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeLessThan(Integer value) {
            addCriterion("quantity_after_change <", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeLessThanOrEqualTo(Integer value) {
            addCriterion("quantity_after_change <=", value, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeIn(List<Integer> values) {
            addCriterion("quantity_after_change in", values, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeNotIn(List<Integer> values) {
            addCriterion("quantity_after_change not in", values, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeBetween(Integer value1, Integer value2) {
            addCriterion("quantity_after_change between", value1, value2, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andQuantityAfterChangeNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity_after_change not between", value1, value2, "quantityAfterChange");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoIsNull() {
            addCriterion("replenishment_order_no is null");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoIsNotNull() {
            addCriterion("replenishment_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoEqualTo(String value) {
            addCriterion("replenishment_order_no =", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoNotEqualTo(String value) {
            addCriterion("replenishment_order_no <>", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoGreaterThan(String value) {
            addCriterion("replenishment_order_no >", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("replenishment_order_no >=", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoLessThan(String value) {
            addCriterion("replenishment_order_no <", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoLessThanOrEqualTo(String value) {
            addCriterion("replenishment_order_no <=", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoLike(String value) {
            addCriterion("replenishment_order_no like", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoNotLike(String value) {
            addCriterion("replenishment_order_no not like", value, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoIn(List<String> values) {
            addCriterion("replenishment_order_no in", values, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoNotIn(List<String> values) {
            addCriterion("replenishment_order_no not in", values, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoBetween(String value1, String value2) {
            addCriterion("replenishment_order_no between", value1, value2, "replenishmentOrderNo");
            return (Criteria) this;
        }

        public Criteria andReplenishmentOrderNoNotBetween(String value1, String value2) {
            addCriterion("replenishment_order_no not between", value1, value2, "replenishmentOrderNo");
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

        public Criteria andChangeTypeIsNull() {
            addCriterion("change_type is null");
            return (Criteria) this;
        }

        public Criteria andChangeTypeIsNotNull() {
            addCriterion("change_type is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTypeEqualTo(String value) {
            addCriterion("change_type =", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotEqualTo(String value) {
            addCriterion("change_type <>", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeGreaterThan(String value) {
            addCriterion("change_type >", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("change_type >=", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeLessThan(String value) {
            addCriterion("change_type <", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeLessThanOrEqualTo(String value) {
            addCriterion("change_type <=", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeLike(String value) {
            addCriterion("change_type like", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotLike(String value) {
            addCriterion("change_type not like", value, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeIn(List<String> values) {
            addCriterion("change_type in", values, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotIn(List<String> values) {
            addCriterion("change_type not in", values, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeBetween(String value1, String value2) {
            addCriterion("change_type between", value1, value2, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeTypeNotBetween(String value1, String value2) {
            addCriterion("change_type not between", value1, value2, "changeType");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdIsNull() {
            addCriterion("change_wms_member_id is null");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdIsNotNull() {
            addCriterion("change_wms_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdEqualTo(Long value) {
            addCriterion("change_wms_member_id =", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdNotEqualTo(Long value) {
            addCriterion("change_wms_member_id <>", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdGreaterThan(Long value) {
            addCriterion("change_wms_member_id >", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("change_wms_member_id >=", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdLessThan(Long value) {
            addCriterion("change_wms_member_id <", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("change_wms_member_id <=", value, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdIn(List<Long> values) {
            addCriterion("change_wms_member_id in", values, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdNotIn(List<Long> values) {
            addCriterion("change_wms_member_id not in", values, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdBetween(Long value1, Long value2) {
            addCriterion("change_wms_member_id between", value1, value2, "changeWmsMemberId");
            return (Criteria) this;
        }

        public Criteria andChangeWmsMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("change_wms_member_id not between", value1, value2, "changeWmsMemberId");
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