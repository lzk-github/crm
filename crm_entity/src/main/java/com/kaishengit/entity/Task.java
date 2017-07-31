package com.kaishengit.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Task implements Serializable {
    /**
     * 任务id
     */
    private Integer id;

    /**
     * 员工id
     */
    private Integer accountId;

    /**
     * 机会id
     */
    private Integer chanceId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 完成日期
     */
    private String endTime;

    /**
     * 提醒日期
     */
    private String remindTime;

    /**
     * 完成状态( 0表示未完成, 1表示已完成 )
     */
    private Byte state = 0;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 关联客户姓名
     */
    private String custName;

    /**
     * 关联销售机会名字
     */
    private String chanceName;

    private static final long serialVersionUID = 1L;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getChanceName() {
        return chanceName;
    }

    public void setChanceName(String chanceName) {
        this.chanceName = chanceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getChanceId() {
        return chanceId;
    }

    public void setChanceId(Integer chanceId) {
        this.chanceId = chanceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}