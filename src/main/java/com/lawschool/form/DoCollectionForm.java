package com.lawschool.form;


public class DoCollectionForm {
    private String sourceId;// 资源ID
    private Integer type;// 收藏类型
    private Integer isCollect;// 收藏(1)/取消收藏(0)

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }
}
