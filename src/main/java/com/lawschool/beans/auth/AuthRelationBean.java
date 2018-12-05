package com.lawschool.beans.auth;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: AuthRelationBean
 * Description: 权限关联关系表
 * date: 2018-12-5 15:58
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_auth_relation")
public class AuthRelationBean implements Serializable {

    private String id;//权限表id

    private String functionId;//功能模块的主键id

    private String authId;//权限id

    private String authType;//权限类型

    private String functionFlag;//功能模块来源

    private String createUser;//创建人

    private Date createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
