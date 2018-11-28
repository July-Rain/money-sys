package com.lawschool.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("ADD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("ADD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("ADD_TIME =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("ADD_TIME <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("ADD_TIME >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ADD_TIME >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("ADD_TIME <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("ADD_TIME <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("ADD_TIME in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("ADD_TIME not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("ADD_TIME between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("ADD_TIME not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNull() {
            addCriterion("ADD_USER is null");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNotNull() {
            addCriterion("ADD_USER is not null");
            return (Criteria) this;
        }

        public Criteria andAddUserEqualTo(String value) {
            addCriterion("ADD_USER =", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotEqualTo(String value) {
            addCriterion("ADD_USER <>", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThan(String value) {
            addCriterion("ADD_USER >", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThanOrEqualTo(String value) {
            addCriterion("ADD_USER >=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThan(String value) {
            addCriterion("ADD_USER <", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThanOrEqualTo(String value) {
            addCriterion("ADD_USER <=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLike(String value) {
            addCriterion("ADD_USER like", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotLike(String value) {
            addCriterion("ADD_USER not like", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserIn(List<String> values) {
            addCriterion("ADD_USER in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotIn(List<String> values) {
            addCriterion("ADD_USER not in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserBetween(String value1, String value2) {
            addCriterion("ADD_USER between", value1, value2, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotBetween(String value1, String value2) {
            addCriterion("ADD_USER not between", value1, value2, "addUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("UPDATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("UPDATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("UPDATE_USER =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("UPDATE_USER <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("UPDATE_USER >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("UPDATE_USER <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("UPDATE_USER like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("UPDATE_USER not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("UPDATE_USER in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("UPDATE_USER not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("UPDATE_USER between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andFullNameIsNull() {
            addCriterion("FULL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFullNameIsNotNull() {
            addCriterion("FULL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFullNameEqualTo(String value) {
            addCriterion("FULL_NAME =", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotEqualTo(String value) {
            addCriterion("FULL_NAME <>", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameGreaterThan(String value) {
            addCriterion("FULL_NAME >", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameGreaterThanOrEqualTo(String value) {
            addCriterion("FULL_NAME >=", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameLessThan(String value) {
            addCriterion("FULL_NAME <", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameLessThanOrEqualTo(String value) {
            addCriterion("FULL_NAME <=", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameLike(String value) {
            addCriterion("FULL_NAME like", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotLike(String value) {
            addCriterion("FULL_NAME not like", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameIn(List<String> values) {
            addCriterion("FULL_NAME in", values, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotIn(List<String> values) {
            addCriterion("FULL_NAME not in", values, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameBetween(String value1, String value2) {
            addCriterion("FULL_NAME between", value1, value2, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotBetween(String value1, String value2) {
            addCriterion("FULL_NAME not between", value1, value2, "fullName");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNull() {
            addCriterion("ORG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("ORG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("ORG_CODE =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("ORG_CODE <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("ORG_CODE >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("ORG_CODE <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("ORG_CODE like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("ORG_CODE not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("ORG_CODE in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("ORG_CODE not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("ORG_CODE between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE not between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andSynFlagIsNull() {
            addCriterion("SYN_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andSynFlagIsNotNull() {
            addCriterion("SYN_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andSynFlagEqualTo(Short value) {
            addCriterion("SYN_FLAG =", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagNotEqualTo(Short value) {
            addCriterion("SYN_FLAG <>", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagGreaterThan(Short value) {
            addCriterion("SYN_FLAG >", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("SYN_FLAG >=", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagLessThan(Short value) {
            addCriterion("SYN_FLAG <", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagLessThanOrEqualTo(Short value) {
            addCriterion("SYN_FLAG <=", value, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagIn(List<Short> values) {
            addCriterion("SYN_FLAG in", values, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagNotIn(List<Short> values) {
            addCriterion("SYN_FLAG not in", values, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagBetween(Short value1, Short value2) {
            addCriterion("SYN_FLAG between", value1, value2, "synFlag");
            return (Criteria) this;
        }

        public Criteria andSynFlagNotBetween(Short value1, Short value2) {
            addCriterion("SYN_FLAG not between", value1, value2, "synFlag");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("USER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("USER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("USER_CODE =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("USER_CODE <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("USER_CODE >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("USER_CODE >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("USER_CODE <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("USER_CODE <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("USER_CODE like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("USER_CODE not like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("USER_CODE in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("USER_CODE not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("USER_CODE between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("USER_CODE not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIndateIsNull() {
            addCriterion("USER_INDATE is null");
            return (Criteria) this;
        }

        public Criteria andUserIndateIsNotNull() {
            addCriterion("USER_INDATE is not null");
            return (Criteria) this;
        }

        public Criteria andUserIndateEqualTo(Date value) {
            addCriterion("USER_INDATE =", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateNotEqualTo(Date value) {
            addCriterion("USER_INDATE <>", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateGreaterThan(Date value) {
            addCriterion("USER_INDATE >", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateGreaterThanOrEqualTo(Date value) {
            addCriterion("USER_INDATE >=", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateLessThan(Date value) {
            addCriterion("USER_INDATE <", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateLessThanOrEqualTo(Date value) {
            addCriterion("USER_INDATE <=", value, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateIn(List<Date> values) {
            addCriterion("USER_INDATE in", values, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateNotIn(List<Date> values) {
            addCriterion("USER_INDATE not in", values, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateBetween(Date value1, Date value2) {
            addCriterion("USER_INDATE between", value1, value2, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserIndateNotBetween(Date value1, Date value2) {
            addCriterion("USER_INDATE not between", value1, value2, "userIndate");
            return (Criteria) this;
        }

        public Criteria andUserJobIsNull() {
            addCriterion("USER_JOB is null");
            return (Criteria) this;
        }

        public Criteria andUserJobIsNotNull() {
            addCriterion("USER_JOB is not null");
            return (Criteria) this;
        }

        public Criteria andUserJobEqualTo(Integer value) {
            addCriterion("USER_JOB =", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobNotEqualTo(Integer value) {
            addCriterion("USER_JOB <>", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobGreaterThan(Integer value) {
            addCriterion("USER_JOB >", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_JOB >=", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobLessThan(Integer value) {
            addCriterion("USER_JOB <", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobLessThanOrEqualTo(Integer value) {
            addCriterion("USER_JOB <=", value, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobIn(List<Integer> values) {
            addCriterion("USER_JOB in", values, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobNotIn(List<Integer> values) {
            addCriterion("USER_JOB not in", values, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobBetween(Integer value1, Integer value2) {
            addCriterion("USER_JOB between", value1, value2, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_JOB not between", value1, value2, "userJob");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelIsNull() {
            addCriterion("USER_JOB_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelIsNotNull() {
            addCriterion("USER_JOB_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelEqualTo(Integer value) {
            addCriterion("USER_JOB_LEVEL =", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelNotEqualTo(Integer value) {
            addCriterion("USER_JOB_LEVEL <>", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelGreaterThan(Integer value) {
            addCriterion("USER_JOB_LEVEL >", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_JOB_LEVEL >=", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelLessThan(Integer value) {
            addCriterion("USER_JOB_LEVEL <", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelLessThanOrEqualTo(Integer value) {
            addCriterion("USER_JOB_LEVEL <=", value, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelIn(List<Integer> values) {
            addCriterion("USER_JOB_LEVEL in", values, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelNotIn(List<Integer> values) {
            addCriterion("USER_JOB_LEVEL not in", values, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelBetween(Integer value1, Integer value2) {
            addCriterion("USER_JOB_LEVEL between", value1, value2, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserJobLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_JOB_LEVEL not between", value1, value2, "userJobLevel");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongIsNull() {
            addCriterion("USER_MOBILE_LONG is null");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongIsNotNull() {
            addCriterion("USER_MOBILE_LONG is not null");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongEqualTo(String value) {
            addCriterion("USER_MOBILE_LONG =", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongNotEqualTo(String value) {
            addCriterion("USER_MOBILE_LONG <>", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongGreaterThan(String value) {
            addCriterion("USER_MOBILE_LONG >", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongGreaterThanOrEqualTo(String value) {
            addCriterion("USER_MOBILE_LONG >=", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongLessThan(String value) {
            addCriterion("USER_MOBILE_LONG <", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongLessThanOrEqualTo(String value) {
            addCriterion("USER_MOBILE_LONG <=", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongLike(String value) {
            addCriterion("USER_MOBILE_LONG like", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongNotLike(String value) {
            addCriterion("USER_MOBILE_LONG not like", value, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongIn(List<String> values) {
            addCriterion("USER_MOBILE_LONG in", values, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongNotIn(List<String> values) {
            addCriterion("USER_MOBILE_LONG not in", values, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongBetween(String value1, String value2) {
            addCriterion("USER_MOBILE_LONG between", value1, value2, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileLongNotBetween(String value1, String value2) {
            addCriterion("USER_MOBILE_LONG not between", value1, value2, "userMobileLong");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortIsNull() {
            addCriterion("USER_MOBILE_SHORT is null");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortIsNotNull() {
            addCriterion("USER_MOBILE_SHORT is not null");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortEqualTo(String value) {
            addCriterion("USER_MOBILE_SHORT =", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortNotEqualTo(String value) {
            addCriterion("USER_MOBILE_SHORT <>", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortGreaterThan(String value) {
            addCriterion("USER_MOBILE_SHORT >", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortGreaterThanOrEqualTo(String value) {
            addCriterion("USER_MOBILE_SHORT >=", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortLessThan(String value) {
            addCriterion("USER_MOBILE_SHORT <", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortLessThanOrEqualTo(String value) {
            addCriterion("USER_MOBILE_SHORT <=", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortLike(String value) {
            addCriterion("USER_MOBILE_SHORT like", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortNotLike(String value) {
            addCriterion("USER_MOBILE_SHORT not like", value, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortIn(List<String> values) {
            addCriterion("USER_MOBILE_SHORT in", values, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortNotIn(List<String> values) {
            addCriterion("USER_MOBILE_SHORT not in", values, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortBetween(String value1, String value2) {
            addCriterion("USER_MOBILE_SHORT between", value1, value2, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserMobileShortNotBetween(String value1, String value2) {
            addCriterion("USER_MOBILE_SHORT not between", value1, value2, "userMobileShort");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("USER_NAME =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("USER_NAME <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("USER_NAME >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("USER_NAME >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("USER_NAME <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("USER_NAME <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("USER_NAME like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("USER_NAME not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("USER_NAME in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("USER_NAME not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("USER_NAME between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("USER_NAME not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserOverdateIsNull() {
            addCriterion("USER_OVERDATE is null");
            return (Criteria) this;
        }

        public Criteria andUserOverdateIsNotNull() {
            addCriterion("USER_OVERDATE is not null");
            return (Criteria) this;
        }

        public Criteria andUserOverdateEqualTo(Date value) {
            addCriterion("USER_OVERDATE =", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateNotEqualTo(Date value) {
            addCriterion("USER_OVERDATE <>", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateGreaterThan(Date value) {
            addCriterion("USER_OVERDATE >", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateGreaterThanOrEqualTo(Date value) {
            addCriterion("USER_OVERDATE >=", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateLessThan(Date value) {
            addCriterion("USER_OVERDATE <", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateLessThanOrEqualTo(Date value) {
            addCriterion("USER_OVERDATE <=", value, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateIn(List<Date> values) {
            addCriterion("USER_OVERDATE in", values, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateNotIn(List<Date> values) {
            addCriterion("USER_OVERDATE not in", values, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateBetween(Date value1, Date value2) {
            addCriterion("USER_OVERDATE between", value1, value2, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserOverdateNotBetween(Date value1, Date value2) {
            addCriterion("USER_OVERDATE not between", value1, value2, "userOverdate");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeIsNull() {
            addCriterion("USER_PART_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeIsNotNull() {
            addCriterion("USER_PART_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeEqualTo(Integer value) {
            addCriterion("USER_PART_TYPE =", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeNotEqualTo(Integer value) {
            addCriterion("USER_PART_TYPE <>", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeGreaterThan(Integer value) {
            addCriterion("USER_PART_TYPE >", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_PART_TYPE >=", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeLessThan(Integer value) {
            addCriterion("USER_PART_TYPE <", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeLessThanOrEqualTo(Integer value) {
            addCriterion("USER_PART_TYPE <=", value, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeIn(List<Integer> values) {
            addCriterion("USER_PART_TYPE in", values, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeNotIn(List<Integer> values) {
            addCriterion("USER_PART_TYPE not in", values, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeBetween(Integer value1, Integer value2) {
            addCriterion("USER_PART_TYPE between", value1, value2, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPartTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_PART_TYPE not between", value1, value2, "userPartType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdIsNull() {
            addCriterion("USER_POLICE_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdIsNotNull() {
            addCriterion("USER_POLICE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdEqualTo(String value) {
            addCriterion("USER_POLICE_ID =", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdNotEqualTo(String value) {
            addCriterion("USER_POLICE_ID <>", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdGreaterThan(String value) {
            addCriterion("USER_POLICE_ID >", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_POLICE_ID >=", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdLessThan(String value) {
            addCriterion("USER_POLICE_ID <", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdLessThanOrEqualTo(String value) {
            addCriterion("USER_POLICE_ID <=", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdLike(String value) {
            addCriterion("USER_POLICE_ID like", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdNotLike(String value) {
            addCriterion("USER_POLICE_ID not like", value, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdIn(List<String> values) {
            addCriterion("USER_POLICE_ID in", values, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdNotIn(List<String> values) {
            addCriterion("USER_POLICE_ID not in", values, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdBetween(String value1, String value2) {
            addCriterion("USER_POLICE_ID between", value1, value2, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceIdNotBetween(String value1, String value2) {
            addCriterion("USER_POLICE_ID not between", value1, value2, "userPoliceId");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeIsNull() {
            addCriterion("USER_POLICE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeIsNotNull() {
            addCriterion("USER_POLICE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeEqualTo(Integer value) {
            addCriterion("USER_POLICE_TYPE =", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeNotEqualTo(Integer value) {
            addCriterion("USER_POLICE_TYPE <>", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeGreaterThan(Integer value) {
            addCriterion("USER_POLICE_TYPE >", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_POLICE_TYPE >=", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeLessThan(Integer value) {
            addCriterion("USER_POLICE_TYPE <", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("USER_POLICE_TYPE <=", value, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeIn(List<Integer> values) {
            addCriterion("USER_POLICE_TYPE in", values, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeNotIn(List<Integer> values) {
            addCriterion("USER_POLICE_TYPE not in", values, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeBetween(Integer value1, Integer value2) {
            addCriterion("USER_POLICE_TYPE between", value1, value2, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserPoliceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_POLICE_TYPE not between", value1, value2, "userPoliceType");
            return (Criteria) this;
        }

        public Criteria andUserQuaterIsNull() {
            addCriterion("USER_QUATER is null");
            return (Criteria) this;
        }

        public Criteria andUserQuaterIsNotNull() {
            addCriterion("USER_QUATER is not null");
            return (Criteria) this;
        }

        public Criteria andUserQuaterEqualTo(Integer value) {
            addCriterion("USER_QUATER =", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterNotEqualTo(Integer value) {
            addCriterion("USER_QUATER <>", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterGreaterThan(Integer value) {
            addCriterion("USER_QUATER >", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_QUATER >=", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterLessThan(Integer value) {
            addCriterion("USER_QUATER <", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterLessThanOrEqualTo(Integer value) {
            addCriterion("USER_QUATER <=", value, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterIn(List<Integer> values) {
            addCriterion("USER_QUATER in", values, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterNotIn(List<Integer> values) {
            addCriterion("USER_QUATER not in", values, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterBetween(Integer value1, Integer value2) {
            addCriterion("USER_QUATER between", value1, value2, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserQuaterNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_QUATER not between", value1, value2, "userQuater");
            return (Criteria) this;
        }

        public Criteria andUserSexIsNull() {
            addCriterion("USER_SEX is null");
            return (Criteria) this;
        }

        public Criteria andUserSexIsNotNull() {
            addCriterion("USER_SEX is not null");
            return (Criteria) this;
        }

        public Criteria andUserSexEqualTo(Integer value) {
            addCriterion("USER_SEX =", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotEqualTo(Integer value) {
            addCriterion("USER_SEX <>", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexGreaterThan(Integer value) {
            addCriterion("USER_SEX >", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_SEX >=", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexLessThan(Integer value) {
            addCriterion("USER_SEX <", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexLessThanOrEqualTo(Integer value) {
            addCriterion("USER_SEX <=", value, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexIn(List<Integer> values) {
            addCriterion("USER_SEX in", values, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotIn(List<Integer> values) {
            addCriterion("USER_SEX not in", values, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexBetween(Integer value1, Integer value2) {
            addCriterion("USER_SEX between", value1, value2, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserSexNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_SEX not between", value1, value2, "userSex");
            return (Criteria) this;
        }

        public Criteria andUserStartdateIsNull() {
            addCriterion("USER_STARTDATE is null");
            return (Criteria) this;
        }

        public Criteria andUserStartdateIsNotNull() {
            addCriterion("USER_STARTDATE is not null");
            return (Criteria) this;
        }

        public Criteria andUserStartdateEqualTo(Date value) {
            addCriterion("USER_STARTDATE =", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateNotEqualTo(Date value) {
            addCriterion("USER_STARTDATE <>", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateGreaterThan(Date value) {
            addCriterion("USER_STARTDATE >", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateGreaterThanOrEqualTo(Date value) {
            addCriterion("USER_STARTDATE >=", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateLessThan(Date value) {
            addCriterion("USER_STARTDATE <", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateLessThanOrEqualTo(Date value) {
            addCriterion("USER_STARTDATE <=", value, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateIn(List<Date> values) {
            addCriterion("USER_STARTDATE in", values, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateNotIn(List<Date> values) {
            addCriterion("USER_STARTDATE not in", values, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateBetween(Date value1, Date value2) {
            addCriterion("USER_STARTDATE between", value1, value2, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStartdateNotBetween(Date value1, Date value2) {
            addCriterion("USER_STARTDATE not between", value1, value2, "userStartdate");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNull() {
            addCriterion("USER_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNotNull() {
            addCriterion("USER_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andUserStatusEqualTo(Integer value) {
            addCriterion("USER_STATUS =", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotEqualTo(Integer value) {
            addCriterion("USER_STATUS <>", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThan(Integer value) {
            addCriterion("USER_STATUS >", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_STATUS >=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThan(Integer value) {
            addCriterion("USER_STATUS <", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThanOrEqualTo(Integer value) {
            addCriterion("USER_STATUS <=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusIn(List<Integer> values) {
            addCriterion("USER_STATUS in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotIn(List<Integer> values) {
            addCriterion("USER_STATUS not in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusBetween(Integer value1, Integer value2) {
            addCriterion("USER_STATUS between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_STATUS not between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("USER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("USER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("USER_TYPE =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("USER_TYPE <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("USER_TYPE >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_TYPE >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("USER_TYPE <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("USER_TYPE <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("USER_TYPE in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("USER_TYPE not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("USER_TYPE between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_TYPE not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("PASSWORD =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("PASSWORD <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("PASSWORD >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("PASSWORD >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("PASSWORD <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("PASSWORD <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("PASSWORD like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("PASSWORD not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("PASSWORD in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("PASSWORD not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("PASSWORD between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("PASSWORD not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("SALT is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("SALT is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(String value) {
            addCriterion("SALT =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(String value) {
            addCriterion("SALT <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(String value) {
            addCriterion("SALT >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(String value) {
            addCriterion("SALT >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(String value) {
            addCriterion("SALT <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(String value) {
            addCriterion("SALT <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLike(String value) {
            addCriterion("SALT like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotLike(String value) {
            addCriterion("SALT not like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<String> values) {
            addCriterion("SALT in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<String> values) {
            addCriterion("SALT not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(String value1, String value2) {
            addCriterion("SALT between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(String value1, String value2) {
            addCriterion("SALT not between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Long value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Long value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Long value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Long value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Long value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Long value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Long> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Long> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Long value1, Long value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Long value1, Long value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileIsNull() {
            addCriterion("CORROSION_FILE is null");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileIsNotNull() {
            addCriterion("CORROSION_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileEqualTo(BigDecimal value) {
            addCriterion("CORROSION_FILE =", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileNotEqualTo(BigDecimal value) {
            addCriterion("CORROSION_FILE <>", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileGreaterThan(BigDecimal value) {
            addCriterion("CORROSION_FILE >", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CORROSION_FILE >=", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileLessThan(BigDecimal value) {
            addCriterion("CORROSION_FILE <", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CORROSION_FILE <=", value, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileIn(List<BigDecimal> values) {
            addCriterion("CORROSION_FILE in", values, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileNotIn(List<BigDecimal> values) {
            addCriterion("CORROSION_FILE not in", values, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CORROSION_FILE between", value1, value2, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andCorrosionFileNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CORROSION_FILE not between", value1, value2, "corrosionFile");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionIsNull() {
            addCriterion("IS_BASIC_CORROSION is null");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionIsNotNull() {
            addCriterion("IS_BASIC_CORROSION is not null");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionEqualTo(Short value) {
            addCriterion("IS_BASIC_CORROSION =", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionNotEqualTo(Short value) {
            addCriterion("IS_BASIC_CORROSION <>", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionGreaterThan(Short value) {
            addCriterion("IS_BASIC_CORROSION >", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_BASIC_CORROSION >=", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionLessThan(Short value) {
            addCriterion("IS_BASIC_CORROSION <", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionLessThanOrEqualTo(Short value) {
            addCriterion("IS_BASIC_CORROSION <=", value, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionIn(List<Short> values) {
            addCriterion("IS_BASIC_CORROSION in", values, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionNotIn(List<Short> values) {
            addCriterion("IS_BASIC_CORROSION not in", values, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionBetween(Short value1, Short value2) {
            addCriterion("IS_BASIC_CORROSION between", value1, value2, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsBasicCorrosionNotBetween(Short value1, Short value2) {
            addCriterion("IS_BASIC_CORROSION not between", value1, value2, "isBasicCorrosion");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredIsNull() {
            addCriterion("IS_MIDDLE_REQUIRED is null");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredIsNotNull() {
            addCriterion("IS_MIDDLE_REQUIRED is not null");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredEqualTo(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED =", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredNotEqualTo(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED <>", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredGreaterThan(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED >", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED >=", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredLessThan(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED <", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredLessThanOrEqualTo(Short value) {
            addCriterion("IS_MIDDLE_REQUIRED <=", value, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredIn(List<Short> values) {
            addCriterion("IS_MIDDLE_REQUIRED in", values, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredNotIn(List<Short> values) {
            addCriterion("IS_MIDDLE_REQUIRED not in", values, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredBetween(Short value1, Short value2) {
            addCriterion("IS_MIDDLE_REQUIRED between", value1, value2, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsMiddleRequiredNotBetween(Short value1, Short value2) {
            addCriterion("IS_MIDDLE_REQUIRED not between", value1, value2, "isMiddleRequired");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaIsNull() {
            addCriterion("IS_PASSED_JUDICIA is null");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaIsNotNull() {
            addCriterion("IS_PASSED_JUDICIA is not null");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaEqualTo(Short value) {
            addCriterion("IS_PASSED_JUDICIA =", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaNotEqualTo(Short value) {
            addCriterion("IS_PASSED_JUDICIA <>", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaGreaterThan(Short value) {
            addCriterion("IS_PASSED_JUDICIA >", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_PASSED_JUDICIA >=", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaLessThan(Short value) {
            addCriterion("IS_PASSED_JUDICIA <", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaLessThanOrEqualTo(Short value) {
            addCriterion("IS_PASSED_JUDICIA <=", value, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaIn(List<Short> values) {
            addCriterion("IS_PASSED_JUDICIA in", values, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaNotIn(List<Short> values) {
            addCriterion("IS_PASSED_JUDICIA not in", values, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaBetween(Short value1, Short value2) {
            addCriterion("IS_PASSED_JUDICIA between", value1, value2, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andIsPassedJudiciaNotBetween(Short value1, Short value2) {
            addCriterion("IS_PASSED_JUDICIA not between", value1, value2, "isPassedJudicia");
            return (Criteria) this;
        }

        public Criteria andQuaLevelIsNull() {
            addCriterion("QUA_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andQuaLevelIsNotNull() {
            addCriterion("QUA_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andQuaLevelEqualTo(String value) {
            addCriterion("QUA_LEVEL =", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelNotEqualTo(String value) {
            addCriterion("QUA_LEVEL <>", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelGreaterThan(String value) {
            addCriterion("QUA_LEVEL >", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelGreaterThanOrEqualTo(String value) {
            addCriterion("QUA_LEVEL >=", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelLessThan(String value) {
            addCriterion("QUA_LEVEL <", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelLessThanOrEqualTo(String value) {
            addCriterion("QUA_LEVEL <=", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelLike(String value) {
            addCriterion("QUA_LEVEL like", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelNotLike(String value) {
            addCriterion("QUA_LEVEL not like", value, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelIn(List<String> values) {
            addCriterion("QUA_LEVEL in", values, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelNotIn(List<String> values) {
            addCriterion("QUA_LEVEL not in", values, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelBetween(String value1, String value2) {
            addCriterion("QUA_LEVEL between", value1, value2, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andQuaLevelNotBetween(String value1, String value2) {
            addCriterion("QUA_LEVEL not between", value1, value2, "quaLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualIsNull() {
            addCriterion("LOCAL_JOB_ACTUAL is null");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualIsNotNull() {
            addCriterion("LOCAL_JOB_ACTUAL is not null");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualEqualTo(String value) {
            addCriterion("LOCAL_JOB_ACTUAL =", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualNotEqualTo(String value) {
            addCriterion("LOCAL_JOB_ACTUAL <>", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualGreaterThan(String value) {
            addCriterion("LOCAL_JOB_ACTUAL >", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualGreaterThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_ACTUAL >=", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualLessThan(String value) {
            addCriterion("LOCAL_JOB_ACTUAL <", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualLessThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_ACTUAL <=", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualLike(String value) {
            addCriterion("LOCAL_JOB_ACTUAL like", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualNotLike(String value) {
            addCriterion("LOCAL_JOB_ACTUAL not like", value, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualIn(List<String> values) {
            addCriterion("LOCAL_JOB_ACTUAL in", values, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualNotIn(List<String> values) {
            addCriterion("LOCAL_JOB_ACTUAL not in", values, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_ACTUAL between", value1, value2, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobActualNotBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_ACTUAL not between", value1, value2, "localJobActual");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelIsNull() {
            addCriterion("LOCAL_JOB_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelIsNotNull() {
            addCriterion("LOCAL_JOB_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelEqualTo(String value) {
            addCriterion("LOCAL_JOB_LEVEL =", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelNotEqualTo(String value) {
            addCriterion("LOCAL_JOB_LEVEL <>", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelGreaterThan(String value) {
            addCriterion("LOCAL_JOB_LEVEL >", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelGreaterThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_LEVEL >=", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelLessThan(String value) {
            addCriterion("LOCAL_JOB_LEVEL <", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelLessThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_LEVEL <=", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelLike(String value) {
            addCriterion("LOCAL_JOB_LEVEL like", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelNotLike(String value) {
            addCriterion("LOCAL_JOB_LEVEL not like", value, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelIn(List<String> values) {
            addCriterion("LOCAL_JOB_LEVEL in", values, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelNotIn(List<String> values) {
            addCriterion("LOCAL_JOB_LEVEL not in", values, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_LEVEL between", value1, value2, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobLevelNotBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_LEVEL not between", value1, value2, "localJobLevel");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandIsNull() {
            addCriterion("LOCAL_JOB_STAND is null");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandIsNotNull() {
            addCriterion("LOCAL_JOB_STAND is not null");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandEqualTo(String value) {
            addCriterion("LOCAL_JOB_STAND =", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandNotEqualTo(String value) {
            addCriterion("LOCAL_JOB_STAND <>", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandGreaterThan(String value) {
            addCriterion("LOCAL_JOB_STAND >", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandGreaterThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_STAND >=", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandLessThan(String value) {
            addCriterion("LOCAL_JOB_STAND <", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandLessThanOrEqualTo(String value) {
            addCriterion("LOCAL_JOB_STAND <=", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandLike(String value) {
            addCriterion("LOCAL_JOB_STAND like", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandNotLike(String value) {
            addCriterion("LOCAL_JOB_STAND not like", value, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandIn(List<String> values) {
            addCriterion("LOCAL_JOB_STAND in", values, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandNotIn(List<String> values) {
            addCriterion("LOCAL_JOB_STAND not in", values, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_STAND between", value1, value2, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andLocalJobStandNotBetween(String value1, String value2) {
            addCriterion("LOCAL_JOB_STAND not between", value1, value2, "localJobStand");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeIsNull() {
            addCriterion("MIDDLE_REQUIRED_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeIsNotNull() {
            addCriterion("MIDDLE_REQUIRED_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeEqualTo(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE =", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeNotEqualTo(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE <>", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeGreaterThan(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE >", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeGreaterThanOrEqualTo(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE >=", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeLessThan(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE <", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeLessThanOrEqualTo(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE <=", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeLike(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE like", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeNotLike(String value) {
            addCriterion("MIDDLE_REQUIRED_TYPE not like", value, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeIn(List<String> values) {
            addCriterion("MIDDLE_REQUIRED_TYPE in", values, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeNotIn(List<String> values) {
            addCriterion("MIDDLE_REQUIRED_TYPE not in", values, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeBetween(String value1, String value2) {
            addCriterion("MIDDLE_REQUIRED_TYPE between", value1, value2, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andMiddleRequiredTypeNotBetween(String value1, String value2) {
            addCriterion("MIDDLE_REQUIRED_TYPE not between", value1, value2, "middleRequiredType");
            return (Criteria) this;
        }

        public Criteria andPhotoIsNull() {
            addCriterion("PHOTO is null");
            return (Criteria) this;
        }

        public Criteria andPhotoIsNotNull() {
            addCriterion("PHOTO is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoEqualTo(BigDecimal value) {
            addCriterion("PHOTO =", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotEqualTo(BigDecimal value) {
            addCriterion("PHOTO <>", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoGreaterThan(BigDecimal value) {
            addCriterion("PHOTO >", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PHOTO >=", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoLessThan(BigDecimal value) {
            addCriterion("PHOTO <", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PHOTO <=", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoIn(List<BigDecimal> values) {
            addCriterion("PHOTO in", values, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotIn(List<BigDecimal> values) {
            addCriterion("PHOTO not in", values, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PHOTO between", value1, value2, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PHOTO not between", value1, value2, "photo");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeIsNull() {
            addCriterion("WORK_POLICE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeIsNotNull() {
            addCriterion("WORK_POLICE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeEqualTo(Date value) {
            addCriterion("WORK_POLICE_TIME =", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeNotEqualTo(Date value) {
            addCriterion("WORK_POLICE_TIME <>", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeGreaterThan(Date value) {
            addCriterion("WORK_POLICE_TIME >", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("WORK_POLICE_TIME >=", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeLessThan(Date value) {
            addCriterion("WORK_POLICE_TIME <", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeLessThanOrEqualTo(Date value) {
            addCriterion("WORK_POLICE_TIME <=", value, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeIn(List<Date> values) {
            addCriterion("WORK_POLICE_TIME in", values, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeNotIn(List<Date> values) {
            addCriterion("WORK_POLICE_TIME not in", values, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeBetween(Date value1, Date value2) {
            addCriterion("WORK_POLICE_TIME between", value1, value2, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkPoliceTimeNotBetween(Date value1, Date value2) {
            addCriterion("WORK_POLICE_TIME not between", value1, value2, "workPoliceTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("WORK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("WORK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterion("WORK_TIME =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterion("WORK_TIME <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterion("WORK_TIME >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("WORK_TIME >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterion("WORK_TIME <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterion("WORK_TIME <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterion("WORK_TIME in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterion("WORK_TIME not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterion("WORK_TIME between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterion("WORK_TIME not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andRolesIsNull() {
            addCriterion("ROLES is null");
            return (Criteria) this;
        }

        public Criteria andRolesIsNotNull() {
            addCriterion("ROLES is not null");
            return (Criteria) this;
        }

        public Criteria andRolesEqualTo(String value) {
            addCriterion("ROLES =", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotEqualTo(String value) {
            addCriterion("ROLES <>", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesGreaterThan(String value) {
            addCriterion("ROLES >", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesGreaterThanOrEqualTo(String value) {
            addCriterion("ROLES >=", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLessThan(String value) {
            addCriterion("ROLES <", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLessThanOrEqualTo(String value) {
            addCriterion("ROLES <=", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLike(String value) {
            addCriterion("ROLES like", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotLike(String value) {
            addCriterion("ROLES not like", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesIn(List<String> values) {
            addCriterion("ROLES in", values, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotIn(List<String> values) {
            addCriterion("ROLES not in", values, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesBetween(String value1, String value2) {
            addCriterion("ROLES between", value1, value2, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotBetween(String value1, String value2) {
            addCriterion("ROLES not between", value1, value2, "roles");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeIsNull() {
            addCriterion("QUA_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeIsNotNull() {
            addCriterion("QUA_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeEqualTo(Date value) {
            addCriterion("QUA_START_TIME =", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeNotEqualTo(Date value) {
            addCriterion("QUA_START_TIME <>", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeGreaterThan(Date value) {
            addCriterion("QUA_START_TIME >", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("QUA_START_TIME >=", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeLessThan(Date value) {
            addCriterion("QUA_START_TIME <", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("QUA_START_TIME <=", value, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeIn(List<Date> values) {
            addCriterion("QUA_START_TIME in", values, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeNotIn(List<Date> values) {
            addCriterion("QUA_START_TIME not in", values, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeBetween(Date value1, Date value2) {
            addCriterion("QUA_START_TIME between", value1, value2, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("QUA_START_TIME not between", value1, value2, "quaStartTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeIsNull() {
            addCriterion("QUA_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeIsNotNull() {
            addCriterion("QUA_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeEqualTo(Date value) {
            addCriterion("QUA_END_TIME =", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeNotEqualTo(Date value) {
            addCriterion("QUA_END_TIME <>", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeGreaterThan(Date value) {
            addCriterion("QUA_END_TIME >", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("QUA_END_TIME >=", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeLessThan(Date value) {
            addCriterion("QUA_END_TIME <", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("QUA_END_TIME <=", value, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeIn(List<Date> values) {
            addCriterion("QUA_END_TIME in", values, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeNotIn(List<Date> values) {
            addCriterion("QUA_END_TIME not in", values, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeBetween(Date value1, Date value2) {
            addCriterion("QUA_END_TIME between", value1, value2, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andQuaEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("QUA_END_TIME not between", value1, value2, "quaEndTime");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("BIRTHDAY is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("BIRTHDAY is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterion("BIRTHDAY =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterion("BIRTHDAY <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterion("BIRTHDAY >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterion("BIRTHDAY >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterion("BIRTHDAY <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterion("BIRTHDAY <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterion("BIRTHDAY in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterion("BIRTHDAY not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterion("BIRTHDAY between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterion("BIRTHDAY not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeIsNull() {
            addCriterion("LOCAL_ORG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeIsNotNull() {
            addCriterion("LOCAL_ORG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeEqualTo(String value) {
            addCriterion("LOCAL_ORG_CODE =", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeNotEqualTo(String value) {
            addCriterion("LOCAL_ORG_CODE <>", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeGreaterThan(String value) {
            addCriterion("LOCAL_ORG_CODE >", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("LOCAL_ORG_CODE >=", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeLessThan(String value) {
            addCriterion("LOCAL_ORG_CODE <", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("LOCAL_ORG_CODE <=", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeLike(String value) {
            addCriterion("LOCAL_ORG_CODE like", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeNotLike(String value) {
            addCriterion("LOCAL_ORG_CODE not like", value, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeIn(List<String> values) {
            addCriterion("LOCAL_ORG_CODE in", values, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeNotIn(List<String> values) {
            addCriterion("LOCAL_ORG_CODE not in", values, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeBetween(String value1, String value2) {
            addCriterion("LOCAL_ORG_CODE between", value1, value2, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andLocalOrgCodeNotBetween(String value1, String value2) {
            addCriterion("LOCAL_ORG_CODE not between", value1, value2, "localOrgCode");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNull() {
            addCriterion("IS_ONLINE is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNotNull() {
            addCriterion("IS_ONLINE is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineEqualTo(String value) {
            addCriterion("IS_ONLINE =", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotEqualTo(String value) {
            addCriterion("IS_ONLINE <>", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThan(String value) {
            addCriterion("IS_ONLINE >", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThanOrEqualTo(String value) {
            addCriterion("IS_ONLINE >=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThan(String value) {
            addCriterion("IS_ONLINE <", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThanOrEqualTo(String value) {
            addCriterion("IS_ONLINE <=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLike(String value) {
            addCriterion("IS_ONLINE like", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotLike(String value) {
            addCriterion("IS_ONLINE not like", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIn(List<String> values) {
            addCriterion("IS_ONLINE in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotIn(List<String> values) {
            addCriterion("IS_ONLINE not in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineBetween(String value1, String value2) {
            addCriterion("IS_ONLINE between", value1, value2, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotBetween(String value1, String value2) {
            addCriterion("IS_ONLINE not between", value1, value2, "isOnline");
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