package com.lawschool.beans.law;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: LawClassifyEntity
 * Description: 法律主题分类 entity
 * date: 2018-12-7 15:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_classify")
public class LawClassifyEntity implements Serializable ,Comparable<LawClassifyEntity>{
    private String id; //id

    private String classifyCode;//分类code

    private String classifyId;//法律分类的id 从接口同步过来

    private String classifyName;//法律分类的名称

    private String parentId;//上级的法律分类的id

    private Short delFlag;//是否删除  -1：已删除  0：正常

    private Integer orderNum;//排序标记

    private Date createTime;//创建时间

    private String synFlag;//更新标记

    @TableField(exist=false)
    private List<?> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode == null ? null : classifyCode.trim();
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId == null ? null : classifyId.trim();
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getSynFlag() {
        return synFlag;
    }

    public void setSynFlag(String synFlag) {
        this.synFlag = synFlag;
    }

    @Override
    public int compareTo(LawClassifyEntity o) {
        if(o.orderNum!=null&&this.orderNum!=null){
            return -o.orderNum + this.orderNum;
        }
        return -1;
    }
}