package com.tata.jiuye.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryExample() {
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

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Long value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Long value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Long value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Long value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Long> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Long value1, Long value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Long value1, Long value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityIsNull() {
            addCriterion("available_inventory_quantity is null");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityIsNotNull() {
            addCriterion("available_inventory_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityEqualTo(Integer value) {
            addCriterion("available_inventory_quantity =", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityNotEqualTo(Integer value) {
            addCriterion("available_inventory_quantity <>", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityGreaterThan(Integer value) {
            addCriterion("available_inventory_quantity >", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("available_inventory_quantity >=", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityLessThan(Integer value) {
            addCriterion("available_inventory_quantity <", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("available_inventory_quantity <=", value, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityIn(List<Integer> values) {
            addCriterion("available_inventory_quantity in", values, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityNotIn(List<Integer> values) {
            addCriterion("available_inventory_quantity not in", values, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityBetween(Integer value1, Integer value2) {
            addCriterion("available_inventory_quantity between", value1, value2, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andAvailableInventoryQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("available_inventory_quantity not between", value1, value2, "availableInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityIsNull() {
            addCriterion("locked_inventory_quantity is null");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityIsNotNull() {
            addCriterion("locked_inventory_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityEqualTo(Integer value) {
            addCriterion("locked_inventory_quantity =", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityNotEqualTo(Integer value) {
            addCriterion("locked_inventory_quantity <>", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityGreaterThan(Integer value) {
            addCriterion("locked_inventory_quantity >", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("locked_inventory_quantity >=", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityLessThan(Integer value) {
            addCriterion("locked_inventory_quantity <", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("locked_inventory_quantity <=", value, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityIn(List<Integer> values) {
            addCriterion("locked_inventory_quantity in", values, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityNotIn(List<Integer> values) {
            addCriterion("locked_inventory_quantity not in", values, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityBetween(Integer value1, Integer value2) {
            addCriterion("locked_inventory_quantity between", value1, value2, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andLockedInventoryQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("locked_inventory_quantity not between", value1, value2, "lockedInventoryQuantity");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceIsNull() {
            addCriterion("replenishment_price is null");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceIsNotNull() {
            addCriterion("replenishment_price is not null");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceEqualTo(BigDecimal value) {
            addCriterion("replenishment_price =", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceNotEqualTo(BigDecimal value) {
            addCriterion("replenishment_price <>", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceGreaterThan(BigDecimal value) {
            addCriterion("replenishment_price >", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("replenishment_price >=", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceLessThan(BigDecimal value) {
            addCriterion("replenishment_price <", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("replenishment_price <=", value, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceIn(List<BigDecimal> values) {
            addCriterion("replenishment_price in", values, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceNotIn(List<BigDecimal> values) {
            addCriterion("replenishment_price not in", values, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replenishment_price between", value1, value2, "replenishmentPrice");
            return (Criteria) this;
        }

        public Criteria andReplenishmentPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("replenishment_price not between", value1, value2, "replenishmentPrice");
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