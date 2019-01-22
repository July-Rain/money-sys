package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 *
 * @Descriptin  收藏表
 * @author      zjw
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@TableName("LAW_COLLECTION")
public class Collection {
    private String id;//id

    private String comStucode;//课件code   对应  Stu_Media /  TestQuestion 的id

    private String comUserid;//用户id

    private String optuser;//用户名字

    private Date opttime;//操作时间

    private Integer type;//类型  10-课程 20-试卷

    private int delStatus ;//状态

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

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }
}