package com.lawschool.beans.vo;

import java.io.Serializable;

/**
 *
 * @Descriptin  部门禁赛统计vo类
 * @author     SUN
 * @version     v1.0
 * @Time
 *
 */

public class CompetitionStatisticsByDeptVo implements Serializable {

    private String userid;//用户id

    private String userName;//用户中文
    private String checkpoint;//闯关分数汇总

    private String pk;//竞赛汇总（包括1对1  code码 1对1   组队赛）

    private String leitai;//打擂台汇总

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getLeitai() {
        return leitai;
    }

    public void setLeitai(String leitai) {
        this.leitai = leitai;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}