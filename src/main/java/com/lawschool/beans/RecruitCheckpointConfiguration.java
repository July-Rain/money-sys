package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @Descriptin  闯关关卡配置实体类
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */

/**
 * 闯关关卡配置
 */
@TableName("LAW_RECRUIT_CHECK_CON")
public class RecruitCheckpointConfiguration implements Serializable {
//  对应数据库	LAW_RECRUIT_CHECK_CON   太长了 存不了  所以删减了表名字


	//id
	@TableId
	private String id;
	//闯关配置id
	private String recruitConfigurationId;
	//专项知识ID
	private String specialKnowledgeId;
//	//第几大关
//	private String howManyMark;
	//是否统一设置  0否  1 是
	private String unifyConfiguration;
//	//小关数量
//	private String smallNum;
	//当前第几小关
	private String howManySmall;
	//试题难度
	private String itemDifficulty;
	//关卡积分
	private String crossingPoints;
	//大关是否奖励   0否  1是
	private String markReward;
	//奖励分值
	private String rewardScore;
	//操作人
	private String create;
	//操作单位
	private String createDept;
	//操作时间
	private Date createTime;
	//状态
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

	public String getRecruitConfigurationId() {
		return recruitConfigurationId;
	}

	public void setRecruitConfigurationId(String recruitConfigurationId) {
		this.recruitConfigurationId = recruitConfigurationId;
	}

	public String getSpecialKnowledgeId() {
		return specialKnowledgeId;
	}

	public void setSpecialKnowledgeId(String specialKnowledgeId) {
		this.specialKnowledgeId = specialKnowledgeId;
	}



	public String getUnifyConfiguration() {
		return unifyConfiguration;
	}

	public void setUnifyConfiguration(String unifyConfiguration) {
		this.unifyConfiguration = unifyConfiguration;
	}



	public String getItemDifficulty() {
		return itemDifficulty;
	}

	public void setItemDifficulty(String itemDifficulty) {
		this.itemDifficulty = itemDifficulty;
	}

	public String getHowManySmall() {
		return howManySmall;
	}

	public void setHowManySmall(String howManySmall) {
		this.howManySmall = howManySmall;
	}



	public String getCrossingPoints() {
		return crossingPoints;
	}

	public void setCrossingPoints(String crossingPoints) {
		this.crossingPoints = crossingPoints;
	}

	public String getMarkReward() {
		return markReward;
	}

	public void setMarkReward(String markReward) {
		this.markReward = markReward;
	}

	public String getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(String rewardScore) {
		this.rewardScore = rewardScore;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
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



}
