package com.lawschool.beans;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.BaseEntity;

import java.util.Date;
import java.util.List;

@TableName("law_org")
public class Org extends BaseEntity<Org>  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4751803427555498795L;

    private Date addTime;  //添加时间

    private String addUser;//添加人

    private Date updateTime; //更新时间

    private String updateUser;//更新人

    private String dictionaryName; // 大平台名称

    private String fullName;	//部门全称

    private String oldOrgCode;	//旧部门编号

    private String ordercode;	// 排序

    private String orgCode;// 机构代码

    private Date orgEnddate;// 停用日期

    private String orgId;// 机构ID

    private Date orgIndate;// 更新日期

    private Short orgLevel;// 更新日期

    private String orgName;// 机构名称

    private String orgShortname;// 机构简称

    private Date orgStartdate;// 启用日期

    private Integer orgStatus;// 机构状态：大于等于2000正常

    private Integer orgType;// 机构类型：10在编；20内设；30临时；40外部；70现役；99999其他；0未知；-1待验证（挂局领导）
	// 本地第一次 -1 已修改成 10

    private String otherName;// 机构其他名称

    private String parentCode;// 上级机构代码

    private String parentId;// 上级机构ID

    private Short synFlag; // 同步标志位，具体请参开存储过程dpt_syn_grp

    private String localOrgCode; // 本地组织代码（挂树增加6位的代码）

    private String localOrgName;// 本地组织名称（13个局机关=〉xx市公安局）

    private String localParentOrgId; // 本地上级组织

    private String localOrgType; // 部门分类：勤务机构、综合单位、派出所、其他

    private String localPoliceType;// 警种

    @TableField(exist = false)
    private List<User> list;

    @TableField(exist = false)
    private List child;//子集

    public Org(String id, Date addTime, String addUser, Date updateTime, String updateUser, String dictionaryName, String fullName, String oldOrgCode, String ordercode, String orgCode, Date orgEnddate, String orgId, Date orgIndate, Short orgLevel, String orgName, String orgShortname, Date orgStartdate, Integer orgStatus, Integer orgType, String otherName, String parentCode, String parentId, Short synFlag, String localOrgCode, String localOrgName, String localParentOrgId, String localOrgType, String localPoliceType) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        this.ordercode = ordercode == null ? null : ordercode;
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

    public List getChild() {
        return child;
    }

    public void setChild(List child) {
        this.child = child;
    }

//    @Override
//    public int compareTo(Org o) {
//        if(o.ordercode!=null&&this.ordercode!=null){
//            return -o.ordercode + this.ordercode;
//        }
//        return -1;
//    }
}