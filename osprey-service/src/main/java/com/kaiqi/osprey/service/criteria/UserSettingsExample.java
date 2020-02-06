package com.kaiqi.osprey.service.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户设置表 查询条件类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:55
 */
public class UserSettingsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSettingsExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagIsNull() {
            addCriterion("login_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagIsNotNull() {
            addCriterion("login_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagEqualTo(Integer value) {
            addCriterion("login_auth_flag =", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotEqualTo(Integer value) {
            addCriterion("login_auth_flag <>", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagGreaterThan(Integer value) {
            addCriterion("login_auth_flag >", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_auth_flag >=", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagLessThan(Integer value) {
            addCriterion("login_auth_flag <", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("login_auth_flag <=", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagIn(List<Integer> values) {
            addCriterion("login_auth_flag in", values, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotIn(List<Integer> values) {
            addCriterion("login_auth_flag not in", values, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("login_auth_flag between", value1, value2, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("login_auth_flag not between", value1, value2, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIsNull() {
            addCriterion("google_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIsNotNull() {
            addCriterion("google_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagEqualTo(Integer value) {
            addCriterion("google_auth_flag =", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotEqualTo(Integer value) {
            addCriterion("google_auth_flag <>", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagGreaterThan(Integer value) {
            addCriterion("google_auth_flag >", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("google_auth_flag >=", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagLessThan(Integer value) {
            addCriterion("google_auth_flag <", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("google_auth_flag <=", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIn(List<Integer> values) {
            addCriterion("google_auth_flag in", values, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotIn(List<Integer> values) {
            addCriterion("google_auth_flag not in", values, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("google_auth_flag between", value1, value2, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("google_auth_flag not between", value1, value2, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIsNull() {
            addCriterion("email_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIsNotNull() {
            addCriterion("email_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagEqualTo(Integer value) {
            addCriterion("email_auth_flag =", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotEqualTo(Integer value) {
            addCriterion("email_auth_flag <>", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagGreaterThan(Integer value) {
            addCriterion("email_auth_flag >", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_auth_flag >=", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagLessThan(Integer value) {
            addCriterion("email_auth_flag <", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("email_auth_flag <=", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIn(List<Integer> values) {
            addCriterion("email_auth_flag in", values, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotIn(List<Integer> values) {
            addCriterion("email_auth_flag not in", values, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("email_auth_flag between", value1, value2, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("email_auth_flag not between", value1, value2, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIsNull() {
            addCriterion("mobile_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIsNotNull() {
            addCriterion("mobile_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagEqualTo(Integer value) {
            addCriterion("mobile_auth_flag =", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotEqualTo(Integer value) {
            addCriterion("mobile_auth_flag <>", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagGreaterThan(Integer value) {
            addCriterion("mobile_auth_flag >", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("mobile_auth_flag >=", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagLessThan(Integer value) {
            addCriterion("mobile_auth_flag <", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("mobile_auth_flag <=", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIn(List<Integer> values) {
            addCriterion("mobile_auth_flag in", values, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotIn(List<Integer> values) {
            addCriterion("mobile_auth_flag not in", values, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("mobile_auth_flag between", value1, value2, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("mobile_auth_flag not between", value1, value2, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagIsNull() {
            addCriterion("sub_notify_flag is null");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagIsNotNull() {
            addCriterion("sub_notify_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagEqualTo(Integer value) {
            addCriterion("sub_notify_flag =", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagNotEqualTo(Integer value) {
            addCriterion("sub_notify_flag <>", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagGreaterThan(Integer value) {
            addCriterion("sub_notify_flag >", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_notify_flag >=", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagLessThan(Integer value) {
            addCriterion("sub_notify_flag <", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sub_notify_flag <=", value, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagIn(List<Integer> values) {
            addCriterion("sub_notify_flag in", values, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagNotIn(List<Integer> values) {
            addCriterion("sub_notify_flag not in", values, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagBetween(Integer value1, Integer value2) {
            addCriterion("sub_notify_flag between", value1, value2, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andSubNotifyFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_notify_flag not between", value1, value2, "subNotifyFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagIsNull() {
            addCriterion("protocol_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagIsNotNull() {
            addCriterion("protocol_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagEqualTo(Integer value) {
            addCriterion("protocol_auth_flag =", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagNotEqualTo(Integer value) {
            addCriterion("protocol_auth_flag <>", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagGreaterThan(Integer value) {
            addCriterion("protocol_auth_flag >", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("protocol_auth_flag >=", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagLessThan(Integer value) {
            addCriterion("protocol_auth_flag <", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("protocol_auth_flag <=", value, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagIn(List<Integer> values) {
            addCriterion("protocol_auth_flag in", values, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagNotIn(List<Integer> values) {
            addCriterion("protocol_auth_flag not in", values, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("protocol_auth_flag between", value1, value2, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andProtocolAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("protocol_auth_flag not between", value1, value2, "protocolAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagIsNull() {
            addCriterion("trade_password_set_flag is null");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagIsNotNull() {
            addCriterion("trade_password_set_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagEqualTo(Integer value) {
            addCriterion("trade_password_set_flag =", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagNotEqualTo(Integer value) {
            addCriterion("trade_password_set_flag <>", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagGreaterThan(Integer value) {
            addCriterion("trade_password_set_flag >", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_password_set_flag >=", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagLessThan(Integer value) {
            addCriterion("trade_password_set_flag <", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagLessThanOrEqualTo(Integer value) {
            addCriterion("trade_password_set_flag <=", value, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagIn(List<Integer> values) {
            addCriterion("trade_password_set_flag in", values, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagNotIn(List<Integer> values) {
            addCriterion("trade_password_set_flag not in", values, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagBetween(Integer value1, Integer value2) {
            addCriterion("trade_password_set_flag between", value1, value2, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andTradePasswordSetFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_password_set_flag not between", value1, value2, "tradePasswordSetFlag");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIsNull() {
            addCriterion("login_pwd_strength is null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIsNotNull() {
            addCriterion("login_pwd_strength is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthEqualTo(Integer value) {
            addCriterion("login_pwd_strength =", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotEqualTo(Integer value) {
            addCriterion("login_pwd_strength <>", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthGreaterThan(Integer value) {
            addCriterion("login_pwd_strength >", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_pwd_strength >=", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthLessThan(Integer value) {
            addCriterion("login_pwd_strength <", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthLessThanOrEqualTo(Integer value) {
            addCriterion("login_pwd_strength <=", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIn(List<Integer> values) {
            addCriterion("login_pwd_strength in", values, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotIn(List<Integer> values) {
            addCriterion("login_pwd_strength not in", values, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthBetween(Integer value1, Integer value2) {
            addCriterion("login_pwd_strength between", value1, value2, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotBetween(Integer value1, Integer value2) {
            addCriterion("login_pwd_strength not between", value1, value2, "loginPwdStrength");
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

        public Criteria andFieldIsNull(String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
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