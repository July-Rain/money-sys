package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriptin  在线比武配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_COMPETITION_ONLINE")
public class CompetitionOnline implements Serializable {
	//id主键
	private String id;

	//题量
	private String topicNum;
	//是否统一规则  0 不是  1是
	private String uniformRules;
	//获胜奖励
	private String winReward;
	//失败奖励
	private String loserReward;
	//答题时间
	private Date answerTime;

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

	public String getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(String topicNum) {
		this.topicNum = topicNum;
	}

	public String getUniformRules() {
		return uniformRules;
	}

	public void setUniformRules(String uniformRules) {
		this.uniformRules = uniformRules;
	}

	public String getWinReward() {
		return winReward;
	}

	public void setWinReward(String winReward) {
		this.winReward = winReward;
	}

	public String getLoserReward() {
		return loserReward;
	}

	public void setLoserReward(String loserReward) {
		this.loserReward = loserReward;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
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
		return "CompetitionOnline{" +
				"id='" + id + '\'' +
				", topicNum='" + topicNum + '\'' +
				", uniformRules='" + uniformRules + '\'' +
				", winReward='" + winReward + '\'' +
				", loserReward='" + loserReward + '\'' +
				", answerTime=" + answerTime +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
