package com.lawschool.beans.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("LAW_EXAM_CONFIG")
public class ExamConfig implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4298322751587715208L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 考试名称
	 */
	private String examName;

	/**
	 * 专项知识ID
	 */
	private String specialKnowledgeId;

	/**
	 * 组卷形式
	 */
	private String groupForm;

	/**
	 * 试卷类型
	 */
	private String examType;

	/**
	 * 出题方式
	 */
	private String questionWay;

	/**
	 * 题目顺序
	 */
	private String topicOrderType;

	/**
	 * 选项顺序
	 */
	private String optionOrderType;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 考试时长
	 */
	private Short examTime;

	/**
	 * 答案显示规则
	 */
	private String answerShowRule;

	/**
	 * 考试分数
	 */
	private Short examScore;

	/**
	 * 及格线
	 */
	private Short passPnt;

	/**
	 * 达标奖励方式
	 */
	private String reachRewardType;

	/**
	 * 达标奖励
	 */
	private String reachReward;

	/**
	 * 组织机构代码
	 */
	private String organizedOrgCode;

	/**
	 * 是否显示法律依据
	 */
	private String isShowLaw;

	/**
	 * 是否显示法律比例
	 */
	private String isShowLawProrortion;

	/**
	 * 是否必考
	 */
	private String isMustTest;

	/**
	 * 题目数量
	 */
	private BigDecimal examCount;

	/**
	 * 阅卷类型
	 */
	private String checkType;

	/**
	 * 阅卷人数
	 */
	private BigDecimal checkNum;

	/**
	 * 阅卷口令
	 */
	private String checkPassword;

	/**
	 * 阅卷分差值
	 */
	private Short checkScoreDifference;

	/**
	 * 是否仲裁
	 */
	private String isAibitration;

	/**
	 * 仲裁人code
	 */
	private String aibitrationUserCode;

	/**
	 * 配置状态
	 */
	private String configState;

	@TableField(exist = false)
	private String[] deptId;// 适用部门

	@TableField(exist = false)
	private String[] userId;// 适用人员

	/**
	 * 主键
	 * 
	 * @return ID 主键
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键
	 * 
	 * @param id 主键
	 */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	/**
	 * 考试名称
	 * 
	 * @return EXAM_NAME 考试名称
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * 考试名称
	 * 
	 * @param examName 考试名称
	 */
	public void setExamName(String examName) {
		this.examName = examName == null ? null : examName.trim();
	}

	/**
	 * 专项知识ID
	 * 
	 * @return Special_knowledge_ID 专项知识ID
	 */
	public String getSpecialKnowledgeId() {
		return specialKnowledgeId;
	}

	/**
	 * 专项知识ID
	 * 
	 * @param specialKnowledgeId 专项知识ID
	 */
	public void setSpecialKnowledgeId(String specialKnowledgeId) {
		this.specialKnowledgeId = specialKnowledgeId == null ? null : specialKnowledgeId.trim();
	}

	/**
	 * 组卷形式
	 * 
	 * @return GROUP_FORM 组卷形式
	 */
	public String getGroupForm() {
		return groupForm;
	}

	/**
	 * 组卷形式
	 * 
	 * @param groupForm 组卷形式
	 */
	public void setGroupForm(String groupForm) {
		this.groupForm = groupForm == null ? null : groupForm.trim();
	}

	/**
	 * 试卷类型
	 * 
	 * @return EXAM_TYPE 试卷类型
	 */
	public String getExamType() {
		return examType;
	}

	/**
	 * 试卷类型
	 * 
	 * @param examType 试卷类型
	 */
	public void setExamType(String examType) {
		this.examType = examType == null ? null : examType.trim();
	}

	/**
	 * 出题方式
	 * 
	 * @return QUESTION_WAY 出题方式
	 */
	public String getQuestionWay() {
		return questionWay;
	}

	/**
	 * 出题方式
	 * 
	 * @param questionWay 出题方式
	 */
	public void setQuestionWay(String questionWay) {
		this.questionWay = questionWay == null ? null : questionWay.trim();
	}

	/**
	 * 题目顺序
	 * 
	 * @return TOPIC_ORDER_TYPE 题目顺序
	 */
	public String getTopicOrderType() {
		return topicOrderType;
	}

	/**
	 * 题目顺序
	 * 
	 * @param topicOrderType 题目顺序
	 */
	public void setTopicOrderType(String topicOrderType) {
		this.topicOrderType = topicOrderType == null ? null : topicOrderType.trim();
	}

	/**
	 * 选项顺序
	 * 
	 * @return OPTION_ORDER_TYPE 选项顺序
	 */
	public String getOptionOrderType() {
		return optionOrderType;
	}

	/**
	 * 选项顺序
	 * 
	 * @param optionOrderType 选项顺序
	 */
	public void setOptionOrderType(String optionOrderType) {
		this.optionOrderType = optionOrderType == null ? null : optionOrderType.trim();
	}

	/**
	 * 开始时间
	 * 
	 * @return START_TIME 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 开始时间
	 * 
	 * @param startTime 开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间
	 * 
	 * @return END_TIME 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 结束时间
	 * 
	 * @param endTime 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 考试时长
	 * 
	 * @return EXAM_TIME 考试时长
	 */
	public Short getExamTime() {
		return examTime;
	}

	/**
	 * 考试时长
	 * 
	 * @param examTime 考试时长
	 */
	public void setExamTime(Short examTime) {
		this.examTime = examTime;
	}

	/**
	 * 答案显示规则
	 * 
	 * @return ANSWER_SHOW_RULE 答案显示规则
	 */
	public String getAnswerShowRule() {
		return answerShowRule;
	}

	/**
	 * 答案显示规则
	 * 
	 * @param answerShowRule 答案显示规则
	 */
	public void setAnswerShowRule(String answerShowRule) {
		this.answerShowRule = answerShowRule == null ? null : answerShowRule.trim();
	}

	/**
	 * 考试分数
	 * 
	 * @return EXAM_SCORE 考试分数
	 */
	public Short getExamScore() {
		return examScore;
	}

	/**
	 * 考试分数
	 * 
	 * @param examScore 考试分数
	 */
	public void setExamScore(Short examScore) {
		this.examScore = examScore;
	}

	/**
	 * 及格线
	 * 
	 * @return PASS_PNT 及格线
	 */
	public Short getPassPnt() {
		return passPnt;
	}

	/**
	 * 及格线
	 * 
	 * @param passPnt 及格线
	 */
	public void setPassPnt(Short passPnt) {
		this.passPnt = passPnt;
	}

	/**
	 * 达标奖励方式
	 * 
	 * @return REACH_REWARD_TYPE 达标奖励方式
	 */
	public String getReachRewardType() {
		return reachRewardType;
	}

	/**
	 * 达标奖励方式
	 * 
	 * @param reachRewardType 达标奖励方式
	 */
	public void setReachRewardType(String reachRewardType) {
		this.reachRewardType = reachRewardType == null ? null : reachRewardType.trim();
	}

	/**
	 * 达标奖励
	 * 
	 * @return REACH_REWARD 达标奖励
	 */
	public String getReachReward() {
		return reachReward;
	}

	/**
	 * 达标奖励
	 * 
	 * @param reachReward 达标奖励
	 */
	public void setReachReward(String reachReward) {
		this.reachReward = reachReward == null ? null : reachReward.trim();
	}

	/**
	 * 组织机构代码
	 * 
	 * @return ORGANIZED_ORG_CODE 组织机构代码
	 */
	public String getOrganizedOrgCode() {
		return organizedOrgCode;
	}

	/**
	 * 组织机构代码
	 * 
	 * @param organizedOrgCode 组织机构代码
	 */
	public void setOrganizedOrgCode(String organizedOrgCode) {
		this.organizedOrgCode = organizedOrgCode == null ? null : organizedOrgCode.trim();
	}

	/**
	 * 是否显示法律依据
	 * 
	 * @return IS_SHOW_LAW 是否显示法律依据
	 */
	public String getIsShowLaw() {
		return isShowLaw;
	}

	/**
	 * 是否显示法律依据
	 * 
	 * @param isShowLaw 是否显示法律依据
	 */
	public void setIsShowLaw(String isShowLaw) {
		this.isShowLaw = isShowLaw == null ? null : isShowLaw.trim();
	}

	/**
	 * 是否显示法律比例
	 * 
	 * @return IS_SHOW_LAW_PRORORTION 是否显示法律比例
	 */
	public String getIsShowLawProrortion() {
		return isShowLawProrortion;
	}

	/**
	 * 是否显示法律比例
	 * 
	 * @param isShowLawProrortion 是否显示法律比例
	 */
	public void setIsShowLawProrortion(String isShowLawProrortion) {
		this.isShowLawProrortion = isShowLawProrortion == null ? null : isShowLawProrortion.trim();
	}

	/**
	 * 是否必考
	 * 
	 * @return IS_MUST_TEST 是否必考
	 */
	public String getIsMustTest() {
		return isMustTest;
	}

	/**
	 * 是否必考
	 * 
	 * @param isMustTest 是否必考
	 */
	public void setIsMustTest(String isMustTest) {
		this.isMustTest = isMustTest == null ? null : isMustTest.trim();
	}

	/**
	 * 题目数量
	 * 
	 * @return EXAM_COUNT 题目数量
	 */
	public BigDecimal getExamCount() {
		return examCount;
	}

	/**
	 * 题目数量
	 * 
	 * @param examCount 题目数量
	 */
	public void setExamCount(BigDecimal examCount) {
		this.examCount = examCount;
	}

	/**
	 * 阅卷类型
	 * 
	 * @return CHECK_TYPE 阅卷类型
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * 阅卷类型
	 * 
	 * @param checkType 阅卷类型
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType == null ? null : checkType.trim();
	}

	/**
	 * 阅卷人数
	 * 
	 * @return CHECK_NUM 阅卷人数
	 */
	public BigDecimal getCheckNum() {
		return checkNum;
	}

	/**
	 * 阅卷人数
	 * 
	 * @param checkNum 阅卷人数
	 */
	public void setCheckNum(BigDecimal checkNum) {
		this.checkNum = checkNum;
	}

	/**
	 * 阅卷口令
	 * 
	 * @return CHECK_PASSWORD 阅卷口令
	 */
	public String getCheckPassword() {
		return checkPassword;
	}

	/**
	 * 阅卷口令
	 * 
	 * @param checkPassword 阅卷口令
	 */
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword == null ? null : checkPassword.trim();
	}

	/**
	 * 阅卷分差值
	 * 
	 * @return CHECK_SCORE_DIFFERENCE 阅卷分差值
	 */
	public Short getCheckScoreDifference() {
		return checkScoreDifference;
	}

	/**
	 * 阅卷分差值
	 * 
	 * @param checkScoreDifference 阅卷分差值
	 */
	public void setCheckScoreDifference(Short checkScoreDifference) {
		this.checkScoreDifference = checkScoreDifference;
	}

	/**
	 * 是否仲裁
	 * 
	 * @return IS_AIBITRATION 是否仲裁
	 */
	public String getIsAibitration() {
		return isAibitration;
	}

	/**
	 * 是否仲裁
	 * 
	 * @param isAibitration 是否仲裁
	 */
	public void setIsAibitration(String isAibitration) {
		this.isAibitration = isAibitration == null ? null : isAibitration.trim();
	}

	/**
	 * 仲裁人code
	 * 
	 * @return AIBITRATION_USER_CODE 仲裁人code
	 */
	public String getAibitrationUserCode() {
		return aibitrationUserCode;
	}

	/**
	 * 仲裁人code
	 * 
	 * @param aibitrationUserCode 仲裁人code
	 */
	public void setAibitrationUserCode(String aibitrationUserCode) {
		this.aibitrationUserCode = aibitrationUserCode == null ? null : aibitrationUserCode.trim();
	}

	/**
	 * 配置状态
	 * 
	 * @return CONFIG_STATE 配置状态
	 */
	public String getConfigState() {
		return configState;
	}

	/**
	 * 配置状态
	 * 
	 * @param configState 配置状态
	 */
	public void setConfigState(String configState) {
		this.configState = configState == null ? null : configState.trim();
	}
}