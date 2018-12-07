package com.lawschool.beans.law;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
/**
 * ClassName: LawClassifyLibEntity
 * Description: 库分类 entity
 * date: 2018-12-7 15:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_classify_lib")
public class LawClassifyLibEntity implements Serializable {
    private String id;//库的id

    private String libCode;//库分类code

    private String libId;//库分类的id 从接口同步过来

    private String libName;//库分类的名称

    private Long parentId;//上级的库分类的id

    private Short delFlag;//是否删除  -1：已删除  0：正常

    private Long orderNum;//排序标记

    private Date createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId == null ? null : libId.trim();
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName == null ? null : libName.trim();
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