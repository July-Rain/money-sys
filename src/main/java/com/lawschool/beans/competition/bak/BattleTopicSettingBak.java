package com.lawschool.beans.competition.bak;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriptin  对战题目配置 备份表
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */


@TableName("LAW_BATTLE_TOPIC_SETTING_bak")
public class BattleTopicSettingBak implements Serializable {
	//id主键
	@TableId
	private String id;

	//比武配置id（可能是在线比武，也可能是 打擂台。存在2种表里的id）
	private String foreignKeyId;
	//对战类型 （来区分是在线比武还是打擂台。来确定反查到哪张表）
	private String type;
	//试题难度
	private String questionDifficulty;
	//是否获取积分 0否  1是
	private String whetherGetIntegral;
	//获得分值
	private String score;
	//专项知识id
	private String knowledgeId;

	//当前第几小关
	private int howManySmall;
	//试题类型
	private String questionType;


	//中文
	@TableField(exist = false)
	private String typeName;
	//中文
	@TableField(exist = false)
	private String questionDifficultyName;
	//中文
	@TableField(exist = false)
	private String whetherGetIntegralName;
	//中文
	@TableField(exist = false)
	private String knowledgeName;
	//中文
	@TableField(exist = false)
	private String questionTypeName;



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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForeignKeyId() {
		return foreignKeyId;
	}

	public void setForeignKeyId(String foreignKeyId) {
		this.foreignKeyId = foreignKeyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuestionDifficulty() {
		return questionDifficulty;
	}

	public void setQuestionDifficulty(String questionDifficulty) {
		this.questionDifficulty = questionDifficulty;
	}

	public String getWhetherGetIntegral() {
		return whetherGetIntegral;
	}

	public void setWhetherGetIntegral(String whetherGetIntegral) {
		this.whetherGetIntegral = whetherGetIntegral;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
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

	public int getHowManySmall() {
		return howManySmall;
	}

	public void setHowManySmall(int howManySmall) {
		this.howManySmall = howManySmall;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getQuestionDifficultyName() {
		return questionDifficultyName;
	}

	public void setQuestionDifficultyName(String questionDifficultyName) {
		this.questionDifficultyName = questionDifficultyName;
	}

	public String getWhetherGetIntegralName() {
		return whetherGetIntegralName;
	}

	public void setWhetherGetIntegralName(String whetherGetIntegralName) {
		this.whetherGetIntegralName = whetherGetIntegralName;
	}

	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	@Override
	public String toString() {
		return "battleTopicSetting{" +
				"id='" + id + '\'' +
				", foreignkeyId='" + foreignKeyId + '\'' +
				", type='" + type + '\'' +
				", questionDifficulty='" + questionDifficulty + '\'' +
				", whetherGetIntegral='" + whetherGetIntegral + '\'' +
				", score='" + score + '\'' +
				", knowledgeId='" + knowledgeId + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
