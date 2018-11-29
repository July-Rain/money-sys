package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriptin  比武战队
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_COMPETITION_TEAM")
public class CompetitionTeam implements Serializable {
	//id主键
	private String id;
	//战队名称
	private String teamName;
	//队长
	private String userId;
	//战队规模
	private String scale;
	//战队状态   0 解散   1 正常
	private String status;

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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "CompetitionTeam{" +
				"id='" + id + '\'' +
				", teamName='" + teamName + '\'' +
				", userId='" + userId + '\'' +
				", scale='" + scale + '\'' +
				", status='" + status + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
