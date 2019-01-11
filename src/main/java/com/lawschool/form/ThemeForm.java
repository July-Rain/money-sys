package com.lawschool.form;

import java.util.List;

/**
 * 主题练习保存Form
 */
public class ThemeForm {
    private String id;// 主题ID
    private Integer status;// 主题练习状态
    private String userId;// 用户ID
    private List<ThemeAnswerForm> list;
    private String typeId;// 主题类型ID
    private String typeName;// 主题名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ThemeAnswerForm> getList() {
        return list;
    }

    public void setList(List<ThemeAnswerForm> list) {
        this.list = list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
