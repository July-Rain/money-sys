package com.lawschool.beans.competition;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriptin  竞赛记录实体类
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_COMPETITION_RECORD")
public class CompetitionRecord implements Serializable {
	//id主键
	@TableId
	private String id;
	//用户id
	private String userId;
	//闯关小关关卡id  里面有第几大关  第几小关 题目类型的信息     --------但是有个问题  当配置被删除或者修改后（修改实质也是删除）  就找不到了  但是配份表有
	private String foreignKeyId;
	//类型id(作用于判断存的到底是闯关id还是比武台记录id，因为这是两张不同的表)-------------不放了  只放闯关
//	private String typeId;

	//记录状态      0失效    1启用
	private String status;
	//打到第几大关
	private String howBig;
    //打到第几小关
	private String howLit;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//记录时间


	//获得的积分
	private String score;

	//备用字段1
	private String backup1;
	//备用字段2
	private String backup2;
	//备用字段3
	private String backup3;
	//备用字段4
	private String backup4;
	//备用字段5
	private String backup5;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getForeignKeyId() {
		return foreignKeyId;
	}

	public void setForeignKeyId(String foreignKeyId) {
		this.foreignKeyId = foreignKeyId;
	}

//	public String getTypeId() {
//		return typeId;
//	}
//
//	public void setTypeId(String typeId) {
//		this.typeId = typeId;
//	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}

	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

	public String getBackup4() {
		return backup4;
	}

	public void setBackup4(String backup4) {
		this.backup4 = backup4;
	}

	public String getBackup5() {
		return backup5;
	}

	public void setBackup5(String backup5) {
		this.backup5 = backup5;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHowBig() {
		return howBig;
	}

	public void setHowBig(String howBig) {
		this.howBig = howBig;
	}

	public String getHowLit() {
		return howLit;
	}

	public void setHowLit(String howLit) {
		this.howLit = howLit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CompetitionRecord{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", foreignKeyId='" + foreignKeyId + '\'' +
				", status='" + status + '\'' +
				", howBig='" + howBig + '\'' +
				", howLit='" + howLit + '\'' +
				", createTime=" + createTime +
				", score='" + score + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
