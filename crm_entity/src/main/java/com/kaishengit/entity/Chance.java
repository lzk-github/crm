package com.kaishengit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Chance implements Serializable {
    private Integer id;

    /**
     * 客户姓名
     */
    private String custName;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * 机会名称
     */
    private String chanceName;

    /**
     * 所属员工id
     */
    private Integer accountId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 机会价值
     */
    private Double worth;

    /**
     * 当前进度
     */
    private String currProgress;

    /**
     * 机会详细内容
     */
    private String content;

    private Date lastContactTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 每个Chance对象封装一个Customer对象
     */
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChanceName() {
        return chanceName;
    }

    public void setChanceName(String chanceName) {
        this.chanceName = chanceName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getWorth() {
        return worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }

    public String getCurrProgress() {
        return currProgress;
    }

    public void setCurrProgress(String currProgress) {
        this.currProgress = currProgress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }
}