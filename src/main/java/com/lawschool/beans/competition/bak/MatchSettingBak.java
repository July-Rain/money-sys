package com.lawschool.beans.competition.bak;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawschool.beans.competition.BattleTopicSetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @Descriptin  擂台配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_MATCH_SETTING_BAK")
public class MatchSettingBak implements Serializable {
	//id主键
	@TableId
	private String id;
	//擂主用户id
	private String winId;

	//擂主用户id
	@TableField(exist = false)
	private String winName;

	//题量
	private String topicNum;
	//是否统一规则  0 不是  1是
	private String uniformRules;


	//获胜奖励
	private String winReward;
	//失败奖励
	private String loserReward;
//	//答题时间
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//	private Date answerTime;
	//答题时间
	private String answerTime;

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

	//题目信息集合
	@TableField(exist = false)
	private List<BattleTopicSetting> battleTopicSettingList=new ArrayList<BattleTopicSetting>();



	//操作人
	private String createPeople;
	//操作单位
	private String createDept;
	//操作时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;


	//最后删除人
	private String delPeople;
	//最后删除时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date delTime;

	public String getCreatePeople() {
		return createPeople;
	}

	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDelPeople() {
		return delPeople;
	}

	public void setDelPeople(String delPeople) {
		this.delPeople = delPeople;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public String getWinName() {
		return winName;
	}

	public void setWinName(String winName) {
		this.winName = winName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
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

//	public Date getAnswerTime() {
//		return answerTime;
//	}
//
//	public void setAnswerTime(Date answerTime) {
//		this.answerTime = answerTime;
//	}

	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
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

	public List<BattleTopicSetting> getBattleTopicSettingList() {
		return battleTopicSettingList;
	}

	public void setBattleTopicSettingList(List<BattleTopicSetting> battleTopicSettingList) {
		this.battleTopicSettingList = battleTopicSettingList;
	}

	@Override
	public String toString() {
		return "MatchSetting{" +
				"id='" + id + '\'' +
				", winId='" + winId + '\'' +
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
