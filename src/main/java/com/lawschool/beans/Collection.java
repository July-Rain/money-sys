package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

@TableName("LAW_COLLECTION")
public class Collection {
    private String id;

    private String comStucode;

    private String comUserid;

    private String optuser;

    private Date opttime;

    private Short type;

    private Short delStatus ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getComStucode() {
        return comStucode;
    }

    public void setComStucode(String comStucode) {
        this.comStucode = comStucode == null ? null : comStucode.trim();
    }

    public String getComUserid() {
        return comUserid;
    }

    public void setComUserid(String comUserid) {
        this.comUserid = comUserid == null ? null : comUserid.trim();
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser == null ? null : optuser.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Short delStatus) {
        this.delStatus = delStatus;
    }
}