package com.kaishengit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Disk implements Serializable {
    /**
     * 网盘id
     */
    private Integer id;

    /**
     * 文件|文件夹 名字
     */
    private String name;

    /**
     * 父文件id,如果为0,表示根文件夹
     */
    private Integer pid;

    /**
     * 文件类型(file|dir)
     */
    private String type;

    /**
     * 文件大小(文件夹的此选项为0)
     */
    private String fileSize;

    /**
     * 最后一次更新时间
     */
    private Date updateTime;

    /**
     * 创建人id
     */
    private Integer accountId;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 加锁 密码
     */
    private String password;

    /**
     * 文件存放的真实名称
     */
    private String saveName;

    /**
     * 文件的md5值
     */
    private String md5;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}