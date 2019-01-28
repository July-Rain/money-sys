package com.lawschool.beans.vo;

import java.io.Serializable;

/**
 * ClassName: OrgDiagnosisEntity
 * Description: 部门统计数据
 * date: 2019-1-9 16:59
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class OrgCompetitionVO implements Serializable {

    private String orgId; //部门id
    private String orgName; //部门名称
    private String orgLevel; //部门级别
    private String parentId;//上级部门id

    private int AllCount;
    private int ChuangGuanCount;
    private int pkAllCount;
    private int pkwinCount;
    private int leitaiAllCount;
    private int leitaiwinCount;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getAllCount() {
        return AllCount;
    }

    public void setAllCount(int allCount) {
        AllCount = allCount;
    }

    public int getChuangGuanCount() {
        return ChuangGuanCount;
    }

    public void setChuangGuanCount(int chuangGuanCount) {
        ChuangGuanCount = chuangGuanCount;
    }

    public int getPkAllCount() {
        return pkAllCount;
    }

    public void setPkAllCount(int pkAllCount) {
        this.pkAllCount = pkAllCount;
    }

    public int getPkwinCount() {
        return pkwinCount;
    }

    public void setPkwinCount(int pkwinCount) {
        this.pkwinCount = pkwinCount;
    }

    public int getLeitaiAllCount() {
        return leitaiAllCount;
    }

    public void setLeitaiAllCount(int leitaiAllCount) {
        this.leitaiAllCount = leitaiAllCount;
    }

    public int getLeitaiwinCount() {
        return leitaiwinCount;
    }

    public void setLeitaiwinCount(int leitaiwinCount) {
        this.leitaiwinCount = leitaiwinCount;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }


}
