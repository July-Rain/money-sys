package com.lawschool.beans.competition;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	//专项知识name
	@TableField(exist = false)
	private String specialKnowledgeName;
//	//第几大关
//	private String howManyMark;
	//是否统一设置  0否  1 是
	private String unifyConfiguration;

//	是佛统一配置name
	@TableField(exist = false)
	private String unifyConfigurationName;

	//当前第几小关
	private int howManySmall;

	//试题类型
	private String itemType;

	////试题类型中文
	@TableField(exist = false)
	private String itemTypeName;


	//试题难度
	private String itemDifficulty;

	//试题难度中文
	@TableField(exist = false)
	private String itemDifficultyName;

	//关卡积分
	private String crossingPoints;
	//大关是否奖励   0否  1是
	private String markReward;

	//	大关是否奖励name
	@TableField(exist = false)
	private String markRewardName;

	//奖励分值
	private String rewardScore;
	//操作人
	private String createPeople;
	//操作单位
	private String createDept;
	//操作时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

	//最后删除人//为的是bak表 结构一样   好转
	@TableField(exist = false)
	private String delPeople;
	//最后删除时间//为的是bak表 结构一样   好转
	@TableField(exist = false)
	private Date delTime;


	public String getDelPeople() {
		return delPeople;
	}

	public void setDelPeople(String delPeople) {
		this.delPeople = delPeople;
	}

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

	public String getSpecialKnowledgeName() {
		return specialKnowledgeName;
	}

	public void setSpecialKnowledgeName(String specialKnowledgeName) {
		this.specialKnowledgeName = specialKnowledgeName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getItemDifficulty() {
		return itemDifficulty;
	}

	public void setItemDifficulty(String itemDifficulty) {
		this.itemDifficulty = itemDifficulty;
	}

	public int getHowManySmall() {
		return howManySmall;
	}

	public void setHowManySmall(int howManySmall) {
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

	public String getItemDifficultyName() {
		return itemDifficultyName;
	}

	public void setItemDifficultyName(String itemDifficultyName) {
		this.itemDifficultyName = itemDifficultyName;
	}

	public String getUnifyConfigurationName() {
		return unifyConfigurationName;
	}

	public void setUnifyConfigurationName(String unifyConfigurationName) {
		this.unifyConfigurationName = unifyConfigurationName;
	}

	public String getMarkRewardName() {
		return markRewardName;
	}

	public void setMarkRewardName(String markRewardName) {
		this.markRewardName = markRewardName;
	}
}
