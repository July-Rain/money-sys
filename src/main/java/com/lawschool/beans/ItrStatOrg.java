package com.lawschool.beans;

/**
 * @author zjw
 * @Title: 积分 部门维度统计
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018-12-2916:27
 */

public class ItrStatOrg {

    private String orgId;//部门id
    private String parentId;//父级部门id
    private String orgLevel;//部门层级
    private String OrgName;//部门名字
    private String orgAllItr;//部门总积分
    private String orgAllItrRank;//部门总积分排名

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getOrgAllItr() {
        return orgAllItr;
    }

    public void setOrgAllItr(String orgAllItr) {
        this.orgAllItr = orgAllItr;
    }

    public String getOrgAllItrRank() {
        return orgAllItrRank;
    }

    public void setOrgAllItrRank(String orgAllItrRank) {
        this.orgAllItrRank = orgAllItrRank;
    }
}
