package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import com.lawschool.beans.system.SysRoleEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @Descriptin  系统用户
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
@TableName("law_user")
public class User extends DataEntity<User> {

    private String fullName;//用户全名

    private String orgCode;//部门code

    private Short synFlag;//是否同步

    private String userCode;//用户code   --登录用

    private String userId;//用户id（不用）

    private Date userIndate;//用户注册时间

    private Integer userJob;

    private Integer userJobLevel;

    private String userMobileLong;//用户电话

    private String userMobileShort;//用户电话

    private String userName;//用户名字

    private Date userOverdate;

    private Integer userPartType;

    private String userPoliceId;//行政号

    private Integer userPoliceType;//行政类型

    private Integer userQuater;

    private Integer userSex;//性别

    private Date userStartdate;

    private Integer userStatus;//用户状态

    private Integer userType;//类型

    private String password;//密码

    private String salt;//盐

    private Long sort;//排序号

    private BigDecimal corrosionFile;

    private Short isBasicCorrosion;

    private Short isMiddleRequired;

    private Short isPassedJudicia;

    private String quaLevel;

    private String localJobActual;

    private String localJobLevel;

    private String localJobStand;

    private String middleRequiredType;

    private BigDecimal photo;//照片

    private Date workPoliceTime;

    private Date workTime;

    private String roles;

    private Date quaStartTime;//有效期开始

    private Date quaEndTime;//有效期借宿

    private Date birthday;//生日

    private String localOrgCode;//本地部门编号

    private String isOnline;//是否在线  0 不在  1在

    private String identify;//身份  0-普通用户  1-教官

    @TableField(exist = false) //数据权限
    private List<String> orgDataAuth;

    @TableField(exist = false) //菜单管理
    private List<SysMenu> menuAuth;

    @TableField(exist = false) //角色list
    private List<SysRoleEntity> roleList;

    @TableField(exist = false)
    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Short getSynFlag() {
        return synFlag;
    }

    public void setSynFlag(Short synFlag) {
        this.synFlag = synFlag;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getUserIndate() {
        return userIndate;
    }

    public void setUserIndate(Date userIndate) {
        this.userIndate = userIndate;
    }

    public Integer getUserJob() {
        return userJob;
    }

    public void setUserJob(Integer userJob) {
        this.userJob = userJob;
    }

    public Integer getUserJobLevel() {
        return userJobLevel;
    }

    public void setUserJobLevel(Integer userJobLevel) {
        this.userJobLevel = userJobLevel;
    }

    public String getUserMobileLong() {
        return userMobileLong;
    }

    public void setUserMobileLong(String userMobileLong) {
        this.userMobileLong = userMobileLong == null ? null : userMobileLong.trim();
    }

    public String getUserMobileShort() {
        return userMobileShort;
    }

    public void setUserMobileShort(String userMobileShort) {
        this.userMobileShort = userMobileShort == null ? null : userMobileShort.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getUserOverdate() {
        return userOverdate;
    }

    public void setUserOverdate(Date userOverdate) {
        this.userOverdate = userOverdate;
    }

    public Integer getUserPartType() {
        return userPartType;
    }

    public void setUserPartType(Integer userPartType) {
        this.userPartType = userPartType;
    }

    public String getUserPoliceId() {
        return userPoliceId;
    }

    public void setUserPoliceId(String userPoliceId) {
        this.userPoliceId = userPoliceId == null ? null : userPoliceId.trim();
    }

    public Integer getUserPoliceType() {
        return userPoliceType;
    }

    public void setUserPoliceType(Integer userPoliceType) {
        this.userPoliceType = userPoliceType;
    }

    public Integer getUserQuater() {
        return userQuater;
    }

    public void setUserQuater(Integer userQuater) {
        this.userQuater = userQuater;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Date getUserStartdate() {
        return userStartdate;
    }

    public void setUserStartdate(Date userStartdate) {
        this.userStartdate = userStartdate;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public BigDecimal getCorrosionFile() {
        return corrosionFile;
    }

    public void setCorrosionFile(BigDecimal corrosionFile) {
        this.corrosionFile = corrosionFile;
    }

    public Short getIsBasicCorrosion() {
        return isBasicCorrosion;
    }

    public void setIsBasicCorrosion(Short isBasicCorrosion) {
        this.isBasicCorrosion = isBasicCorrosion;
    }

    public Short getIsMiddleRequired() {
        return isMiddleRequired;
    }

    public void setIsMiddleRequired(Short isMiddleRequired) {
        this.isMiddleRequired = isMiddleRequired;
    }

    public Short getIsPassedJudicia() {
        return isPassedJudicia;
    }

    public void setIsPassedJudicia(Short isPassedJudicia) {
        this.isPassedJudicia = isPassedJudicia;
    }

    public String getQuaLevel() {
        return quaLevel;
    }

    public void setQuaLevel(String quaLevel) {
        this.quaLevel = quaLevel == null ? null : quaLevel.trim();
    }

    public String getLocalJobActual() {
        return localJobActual;
    }

    public void setLocalJobActual(String localJobActual) {
        this.localJobActual = localJobActual == null ? null : localJobActual.trim();
    }

    public String getLocalJobLevel() {
        return localJobLevel;
    }

    public void setLocalJobLevel(String localJobLevel) {
        this.localJobLevel = localJobLevel == null ? null : localJobLevel.trim();
    }

    public String getLocalJobStand() {
        return localJobStand;
    }

    public void setLocalJobStand(String localJobStand) {
        this.localJobStand = localJobStand == null ? null : localJobStand.trim();
    }

    public String getMiddleRequiredType() {
        return middleRequiredType;
    }

    public void setMiddleRequiredType(String middleRequiredType) {
        this.middleRequiredType = middleRequiredType == null ? null : middleRequiredType.trim();
    }

    public BigDecimal getPhoto() {
        return photo;
    }

    public void setPhoto(BigDecimal photo) {
        this.photo = photo;
    }

    public Date getWorkPoliceTime() {
        return workPoliceTime;
    }

    public void setWorkPoliceTime(Date workPoliceTime) {
        this.workPoliceTime = workPoliceTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    public Date getQuaStartTime() {
        return quaStartTime;
    }

    public void setQuaStartTime(Date quaStartTime) {
        this.quaStartTime = quaStartTime;
    }

    public Date getQuaEndTime() {
        return quaEndTime;
    }

    public void setQuaEndTime(Date quaEndTime) {
        this.quaEndTime = quaEndTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLocalOrgCode() {
        return localOrgCode;
    }

    public void setLocalOrgCode(String localOrgCode) {
        this.localOrgCode = localOrgCode == null ? null : localOrgCode.trim();
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline == null ? null : isOnline.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", synFlag=" + synFlag +
                ", userCode='" + userCode + '\'' +
                ", userId='" + userId + '\'' +
                ", userIndate=" + userIndate +
                ", userJob=" + userJob +
                ", userJobLevel=" + userJobLevel +
                ", userMobileLong='" + userMobileLong + '\'' +
                ", userMobileShort='" + userMobileShort + '\'' +
                ", userName='" + userName + '\'' +
                ", userOverdate=" + userOverdate +
                ", userPartType=" + userPartType +
                ", userPoliceId='" + userPoliceId + '\'' +
                ", userPoliceType=" + userPoliceType +
                ", userQuater=" + userQuater +
                ", userSex=" + userSex +
                ", userStartdate=" + userStartdate +
                ", userStatus=" + userStatus +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sort=" + sort +
                ", corrosionFile=" + corrosionFile +
                ", isBasicCorrosion=" + isBasicCorrosion +
                ", isMiddleRequired=" + isMiddleRequired +
                ", isPassedJudicia=" + isPassedJudicia +
                ", quaLevel='" + quaLevel + '\'' +
                ", localJobActual='" + localJobActual + '\'' +
                ", localJobLevel='" + localJobLevel + '\'' +
                ", localJobStand='" + localJobStand + '\'' +
                ", middleRequiredType='" + middleRequiredType + '\'' +
                ", photo=" + photo +
                ", workPoliceTime=" + workPoliceTime +
                ", workTime=" + workTime +
                ", roles='" + roles + '\'' +
                ", quaStartTime=" + quaStartTime +
                ", quaEndTime=" + quaEndTime +
                ", birthday=" + birthday +
                ", localOrgCode='" + localOrgCode + '\'' +
                ", isOnline='" + isOnline + '\'' +
                '}';
    }

    public List<String> getOrgDataAuth() {
        return orgDataAuth;
    }

    public void setOrgDataAuth(List<String> orgDataAuth) {
        this.orgDataAuth = orgDataAuth;
    }

    public List<SysMenu> getMenuAuth() {
        return menuAuth;
    }

    public void setMenuAuth(List<SysMenu> menuAuth) {
        this.menuAuth = menuAuth;
    }

    public List<SysRoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoleEntity> roleList) {
        this.roleList = roleList;
    }


    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }
}