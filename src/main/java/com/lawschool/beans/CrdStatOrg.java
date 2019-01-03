package com.lawschool.beans;

import org.springframework.context.annotation.Bean;

/**
 * @author zjw
 * @Title: 学分部门维度统计
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018-12-2916:27
 */

public class CrdStatOrg {

    private String orgCode;
    private String parentCode;
    private String orgLevel;
    private String OrgName;
    private String orgAllCrd;
    private String orgAllCrdRank;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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

    public String getOrgAllCrd() {
        return orgAllCrd;
    }

    public void setOrgAllCrd(String orgAllCrd) {
        this.orgAllCrd = orgAllCrd;
    }

    public String getOrgAllCrdRank() {
        return orgAllCrdRank;
    }

    public void setOrgAllCrdRank(String orgAllCrdRank) {
        this.orgAllCrdRank = orgAllCrdRank;
    }
}
