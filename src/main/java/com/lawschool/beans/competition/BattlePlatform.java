package com.lawschool.beans.competition;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 *
 * @Descriptin  对战平台
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_BATTLE_PLATFORM")
public class BattlePlatform implements Serializable {
	//id主键
	@TableId
	private String id;

	//比武配置id（可能是在线比武，也可能是 打擂台。存在2种表里的id）
	private String foreignkeyId;
	//对战类型 （来区分是在线比武还是打擂台。来确定反查到哪张表）
	private String type;
	//玩家1
	private String play1;
	//玩家2
	private String play2;
	//对战码
	private String battleCode;

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

	public String getForeignkeyId() {
		return foreignkeyId;
	}

	public void setForeignkeyId(String foreignkeyId) {
		this.foreignkeyId = foreignkeyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlay1() {
		return play1;
	}

	public void setPlay1(String play1) {
		this.play1 = play1;
	}

	public String getPlay2() {
		return play2;
	}

	public void setPlay2(String play2) {
		this.play2 = play2;
	}

	public String getBattleCode() {
		return battleCode;
	}

	public void setBattleCode(String battleCode) {
		this.battleCode = battleCode;
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
		return "battlePlatform{" +
				"id='" + id + '\'' +
				", foreignkeyId='" + foreignkeyId + '\'' +
				", type='" + type + '\'' +
				", play1='" + play1 + '\'' +
				", play2='" + play2 + '\'' +
				", battleCode='" + battleCode + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
