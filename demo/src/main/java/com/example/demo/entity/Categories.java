package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class Categories {
    /**
     * 分类ID
     */
    private Integer id;

    /**
     * 上级分类ID, 0为根节点
     */

    private Integer parentId=0;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;
    /**
     * 创建子类
     */
    private ArrayList<Categories> children= new ArrayList<>();

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<Categories> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Categories> children) {
        this.children = children;
    }

    public void addChildren(Categories item){
        if (this.children==null){
            this.children=new ArrayList<>();
        }
        this.children.add(item);
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", children=" + children +
                ", updateTime=" + updateTime +
                '}';
    }
}
