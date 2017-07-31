package com.kaishengit.dto;

/**
 * ZTree插件的转换对象
 * Created by lzk on 2017/7/17.
 */
public class ZTreeNode {
    private Integer id;
    private String name;
    private Integer pId;
    private boolean open = true;

    @Override
    public String toString() {
        return "ZTreeNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pId=" + pId +
                ", open=" + open +
                '}';
    }

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

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
