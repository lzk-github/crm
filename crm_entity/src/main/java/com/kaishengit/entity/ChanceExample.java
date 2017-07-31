package com.kaishengit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChanceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChanceExample() {
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

        public Criteria andChanceNameIsNull() {
            addCriterion("chance_name is null");
            return (Criteria) this;
        }

        public Criteria andChanceNameIsNotNull() {
            addCriterion("chance_name is not null");
            return (Criteria) this;
        }

        public Criteria andChanceNameEqualTo(String value) {
            addCriterion("chance_name =", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameNotEqualTo(String value) {
            addCriterion("chance_name <>", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameGreaterThan(String value) {
            addCriterion("chance_name >", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameGreaterThanOrEqualTo(String value) {
            addCriterion("chance_name >=", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameLessThan(String value) {
            addCriterion("chance_name <", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameLessThanOrEqualTo(String value) {
            addCriterion("chance_name <=", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameLike(String value) {
            addCriterion("chance_name like", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameNotLike(String value) {
            addCriterion("chance_name not like", value, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameIn(List<String> values) {
            addCriterion("chance_name in", values, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameNotIn(List<String> values) {
            addCriterion("chance_name not in", values, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameBetween(String value1, String value2) {
            addCriterion("chance_name between", value1, value2, "chanceName");
            return (Criteria) this;
        }

        public Criteria andChanceNameNotBetween(String value1, String value2) {
            addCriterion("chance_name not between", value1, value2, "chanceName");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Integer value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Integer value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Integer value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Integer value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Integer> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Integer> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Integer value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Integer value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Integer value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Integer value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Integer value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Integer> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Integer> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Integer value1, Integer value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andWorthIsNull() {
            addCriterion("worth is null");
            return (Criteria) this;
        }

        public Criteria andWorthIsNotNull() {
            addCriterion("worth is not null");
            return (Criteria) this;
        }

        public Criteria andWorthEqualTo(Double value) {
            addCriterion("worth =", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotEqualTo(Double value) {
            addCriterion("worth <>", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthGreaterThan(Double value) {
            addCriterion("worth >", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthGreaterThanOrEqualTo(Double value) {
            addCriterion("worth >=", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthLessThan(Double value) {
            addCriterion("worth <", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthLessThanOrEqualTo(Double value) {
            addCriterion("worth <=", value, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthIn(List<Double> values) {
            addCriterion("worth in", values, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotIn(List<Double> values) {
            addCriterion("worth not in", values, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthBetween(Double value1, Double value2) {
            addCriterion("worth between", value1, value2, "worth");
            return (Criteria) this;
        }

        public Criteria andWorthNotBetween(Double value1, Double value2) {
            addCriterion("worth not between", value1, value2, "worth");
            return (Criteria) this;
        }

        public Criteria andCurrProgressIsNull() {
            addCriterion("curr_progress is null");
            return (Criteria) this;
        }

        public Criteria andCurrProgressIsNotNull() {
            addCriterion("curr_progress is not null");
            return (Criteria) this;
        }

        public Criteria andCurrProgressEqualTo(String value) {
            addCriterion("curr_progress =", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressNotEqualTo(String value) {
            addCriterion("curr_progress <>", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressGreaterThan(String value) {
            addCriterion("curr_progress >", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressGreaterThanOrEqualTo(String value) {
            addCriterion("curr_progress >=", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressLessThan(String value) {
            addCriterion("curr_progress <", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressLessThanOrEqualTo(String value) {
            addCriterion("curr_progress <=", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressLike(String value) {
            addCriterion("curr_progress like", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressNotLike(String value) {
            addCriterion("curr_progress not like", value, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressIn(List<String> values) {
            addCriterion("curr_progress in", values, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressNotIn(List<String> values) {
            addCriterion("curr_progress not in", values, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressBetween(String value1, String value2) {
            addCriterion("curr_progress between", value1, value2, "currProgress");
            return (Criteria) this;
        }

        public Criteria andCurrProgressNotBetween(String value1, String value2) {
            addCriterion("curr_progress not between", value1, value2, "currProgress");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeIsNull() {
            addCriterion("last_contact_time is null");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeIsNotNull() {
            addCriterion("last_contact_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeEqualTo(Date value) {
            addCriterion("last_contact_time =", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeNotEqualTo(Date value) {
            addCriterion("last_contact_time <>", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeGreaterThan(Date value) {
            addCriterion("last_contact_time >", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_contact_time >=", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeLessThan(Date value) {
            addCriterion("last_contact_time <", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_contact_time <=", value, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeIn(List<Date> values) {
            addCriterion("last_contact_time in", values, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeNotIn(List<Date> values) {
            addCriterion("last_contact_time not in", values, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeBetween(Date value1, Date value2) {
            addCriterion("last_contact_time between", value1, value2, "lastContactTime");
            return (Criteria) this;
        }

        public Criteria andLastContactTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_contact_time not between", value1, value2, "lastContactTime");
            return (Criteria) this;
        }
    }

    /**
     */
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