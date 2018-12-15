package com.lawschool.beans.competition;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @Descriptin  闯关配置实体类
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */

/**
 * 闯关配置
 */
@TableName("LAW_RECRUIT_CONFIGURATION")
public class RecruitConfiguration implements Serializable {
	//id主键
	@TableId
	private String id;
	//第几大关顺序   1.2.3.4.5.6 这个样子
	private int markNumOrder;

	//小关数量
	private String smallNum;
	//是否统一设置  0否  1 是
	private String unifyConfiguration;
	//配置状态      0禁用    1启用
	private String status;

	//大关是否奖励   0否  1是
	private String markReward;
	//奖励分值
	private String rewardScore;
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

//小关信息集合
   @TableField(exist = false)
	private List<RecruitCheckpointConfiguration>  recruitCheckpointConfigurationList =new ArrayList<RecruitCheckpointConfiguration>();


	public String getUnifyConfiguration() {
		return unifyConfiguration;
	}

	public void setUnifyConfiguration(String unifyConfiguration) {
		this.unifyConfiguration = unifyConfiguration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<RecruitCheckpointConfiguration> getRecruitCheckpointConfigurationList() {
		return recruitCheckpointConfigurationList;
	}

	public void setRecruitCheckpointConfigurationList(List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList) {
		this.recruitCheckpointConfigurationList = recruitCheckpointConfigurationList;
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

	public int getMarkNumOrder() {
		return markNumOrder;
	}

	public void setMarkNumOrder(int markNumOrder) {
		this.markNumOrder = markNumOrder;
	}

	public String getSmallNum() {
		return smallNum;
	}

	public void setSmallNum(String smallNum) {
		this.smallNum = smallNum;
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
}
