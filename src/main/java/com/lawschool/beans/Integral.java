package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.Date;
/**
 * @Author zjw
 * @Description 用户积分学分记录表
 * @Date 23:31 2018/12/20
 * @Param 
 * @return 
**/
@TableName("LAW_INTEGRAL")
public class Integral  extends DataEntity<Integral> {

    private String type;//类型  0 -学分  1-积分

    private Float point;//分数

    private String src;//来源    闯关：Checkpoint    在线比武：onlinPk     擂台：leitai

    private String userId;//用户id

    public Integral() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

//    public Integer getPoint() {
//        return point;
//    }
//
//    public void setPoint(Integer point) {
//        this.point = point;
//    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}