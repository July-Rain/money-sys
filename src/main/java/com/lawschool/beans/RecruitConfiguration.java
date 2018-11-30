package com.lawschool.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
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
	//大关数量
	private String markNum;
	//配置状态      0禁用    1启用
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

	public String getMarkNum() {
		return markNum;
	}

	public void setMarkNum(String markNum) {
		this.markNum = markNum;
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
		return "RecruitConfiguration{" +
				"id='" + id + '\'' +
				", markNum='" + markNum + '\'' +
				", status='" + status + '\'' +
				", backup1='" + backup1 + '\'' +
				", backup2='" + backup2 + '\'' +
				", backup3='" + backup3 + '\'' +
				", backup4='" + backup4 + '\'' +
				", backup5='" + backup5 + '\'' +
				'}';
	}
}
