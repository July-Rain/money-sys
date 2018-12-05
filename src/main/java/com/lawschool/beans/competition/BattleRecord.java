package com.lawschool.beans.competition;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 *
 * @Descriptin  对战记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_BATTLE_RECORD")
public class BattleRecord implements Serializable {
	//id主键
	@TableId
	private String id;

	//对战平台id
	private String battlePlatformId;
	//用户id
	private String userId;
	//是否获胜   0失败   1获胜
	private String whetherWin;
	//获得分值
	private String score;
	//对战类型    这次的对战比赛是在线比武还是擂台比赛
	private String type;

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


	public String getBattlePlatformId() {
		return battlePlatformId;
	}

	public void setBattlePlatformId(String battlePlatformId) {
		this.battlePlatformId = battlePlatformId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWhetherWin() {
		return whetherWin;
	}

	public void setWhetherWin(String whetherWin) {
		this.whetherWin = whetherWin;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "battleRecord{" +
				"id='" + id + '\'' +
				", battlePlatformId='" + battlePlatformId + '\'' +
				", userId='" + userId + '\'' +
				", whetherWin='" + whetherWin + '\'' +
				", score='" + score + '\'' +
				", type='" + type + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
