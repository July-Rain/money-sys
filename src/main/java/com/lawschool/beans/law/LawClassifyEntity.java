package com.lawschool.beans.law;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
/**
 * ClassName: LawClassifyEntity
 * Description: 法律主题分类 entity
 * date: 2018-12-7 15:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_classify")
public class LawClassifyEntity implements Serializable {
    private String id; //id

    private String classifyCode;//分类code

    private String classifyId;//法律分类的id 从接口同步过来

    private String classifyName;//法律分类的名称

    private Long parentId;//上级的法律分类的id

    private Short delFlag;//是否删除  -1：已删除  0：正常

    private Long orderNum;//排序标记

    private Date createTime;//创建时间

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}