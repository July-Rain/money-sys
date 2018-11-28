package com.lawschool.beans;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Org {
    private Long id;

    private Date addTime;

    private String addUser;

    private Date updateTime;

    private String updateUser;

    private String dictionaryName;

    private String fullName;

    private String oldOrgCode;

    private String ordercode;

    private String orgCode;

    private Date orgEnddate;

    private String orgId;

    private Date orgIndate;

    private Short orgLevel;

    private String orgName;

    private String orgShortname;

    private Date orgStartdate;

    private Integer orgStatus;

    private Integer orgType;

    private String otherName;

    private String parentCode;

    private String parentId;

    private Short synFlag;

    private String localOrgCode;

    private String localOrgName;

    private String localParentOrgId;

    private String localOrgType;

    private String localPoliceType;
    
    private List<User> list;

    public Org(Long id, Date addTime, String addUser, Date updateTime, String updateUser, String dictionaryName, String fullName, String oldOrgCode, String ordercode, String orgCode, Date orgEnddate, String orgId, Date orgIndate, Short orgLevel, String orgName, String orgShortname, Date orgStartdate, Integer orgStatus, Integer orgType, String otherName, String parentCode, String parentId, Short synFlag, String localOrgCode, String localOrgName, String localParentOrgId, String localOrgType, String localPoliceType) {
        this.id = id;
        this.addTime = addTime;
        this.addUser = addUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.dictionaryName = dictionaryName;
        this.fullName = fullName;
        this.oldOrgCode = oldOrgCode;
        this.ordercode = ordercode;
        this.orgCode = orgCode;
        this.orgEnddate = orgEnddate;
        this.orgId = orgId;
        this.orgIndate = orgIndate;
        this.orgLevel = orgLevel;
        this.orgName = orgName;
        this.orgShortname = orgShortname;
        this.orgStartdate = orgStartdate;
        this.orgStatus = orgStatus;
        this.orgType = orgType;
        this.otherName = otherName;
        this.parentCode = parentCode;
        this.parentId = parentId;
        this.synFlag = synFlag;
        this.localOrgCode = localOrgCode;
        this.localOrgName = localOrgName;
        this.localParentOrgId = localParentOrgId;
        this.localOrgType = localOrgType;
        this.localPoliceType = localPoliceType;
    }

    public Org() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName == null ? null : dictionaryName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getOldOrgCode() {
        return oldOrgCode;
    }

    public void setOldOrgCode(String oldOrgCode) {
        this.oldOrgCode = oldOrgCode == null ? null : oldOrgCode.trim();
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode == null ? null : ordercode.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Date getOrgEnddate() {
        return orgEnddate;
    }

    public void setOrgEnddate(Date orgEnddate) {
        this.orgEnddate = orgEnddate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Date getOrgIndate() {
        return orgIndate;
    }

    public void setOrgIndate(Date orgIndate) {
        this.orgIndate = orgIndate;
    }

    public Short getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Short orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgShortname() {
        return orgShortname;
    }

    public void setOrgShortname(String orgShortname) {
        this.orgShortname = orgShortname == null ? null : orgShortname.trim();
    }

    public Date getOrgStartdate() {
        return orgStartdate;
    }

    public void setOrgStartdate(Date orgStartdate) {
        this.orgStartdate = orgStartdate;
    }

    public Integer getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(Integer orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName == null ? null : otherName.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Short getSynFlag() {
        return synFlag;
    }

    public void setSynFlag(Short synFlag) {
        this.synFlag = synFlag;
    }

    public String getLocalOrgCode() {
        return localOrgCode;
    }

    public void setLocalOrgCode(String localOrgCode) {
        this.localOrgCode = localOrgCode == null ? null : localOrgCode.trim();
    }

    public String getLocalOrgName() {
        return localOrgName;
    }

    public void setLocalOrgName(String localOrgName) {
        this.localOrgName = localOrgName == null ? null : localOrgName.trim();
    }

    public String getLocalParentOrgId() {
        return localParentOrgId;
    }

    public void setLocalParentOrgId(String localParentOrgId) {
        this.localParentOrgId = localParentOrgId == null ? null : localParentOrgId.trim();
    }

    public String getLocalOrgType() {
        return localOrgType;
    }

    public void setLocalOrgType(String localOrgType) {
        this.localOrgType = localOrgType == null ? null : localOrgType.trim();
    }

    public String getLocalPoliceType() {
        return localPoliceType;
    }

    public void setLocalPoliceType(String localPoliceType) {
        this.localPoliceType = localPoliceType == null ? null : localPoliceType.trim();
    }

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}
    
    
}