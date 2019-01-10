package com.lawschool.beans;

import com.google.gson.annotations.Expose;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.CompetitionTeam;
import com.lawschool.beans.competition.MatchSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO类，用来存放聊天的消息
 * @author 孙小康
 *
 */
public class MessageByTeam {

	//发送者
	@Expose
	public String from;
	//发送者名称
	@Expose
	public String fromName;
	//接收者
	@Expose
	List<String> to =new ArrayList<String>();
	//发送的文本
	@Expose
	public String text;
	//发送日期
	@Expose
	public Date date;

	//在线本队用户列表
	@Expose
	List<User> userList = new ArrayList<>();


	//队伍
	@Expose
	CompetitionTeam competitionTeam = new CompetitionTeam();

	//当前处于组队中还是 进入到比赛环节(0是组队中，1是比赛中)
	@Expose
	public String teamOrGame;

	//在线对方队伍用户列表
	@Expose
	List<User> userList2 = new ArrayList<>();

	//在线比武配置
	@Expose
	CompetitionOnline competitionOnline=new CompetitionOnline();

	//对战平台
	@Expose
	BattlePlatform battlePlatform=new BattlePlatform();

	// 对战平台认证码（其实和id一样 要唯一啊）
	@Expose
	public String battleCode;


	//当前答到第几题
	@Expose
	public String nowtimu;

	@Expose
	List<TestQuestions> tqList=new ArrayList<TestQuestions>();
	//题目
	@Expose
	TestQuestions tq=new TestQuestions();

	//我的答案
	@Expose
	public String myanswer;

	//正确答案
	@Expose
	public String rightAnswer;


	//积分
	@Expose
	public String jifen;

	//谁跑
	@Expose
	public String strus;//0自己队友跑了  1对方跑了

	public String getStrus() {
		return strus;
	}

	public void setStrus(String strus) {
		this.strus = strus;
	}

	public String getJifen() {
		return jifen;
	}

	public void setJifen(String jifen) {
		this.jifen = jifen;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getMyanswer() {
		return myanswer;
	}

	public void setMyanswer(String myanswer) {
		this.myanswer = myanswer;
	}

	public String getNowtimu() {
		return nowtimu;
	}

	public void setNowtimu(String nowtimu) {
		this.nowtimu = nowtimu;
	}

	public List<TestQuestions> getTqList() {
		return tqList;
	}

	public void setTqList(List<TestQuestions> tqList) {
		this.tqList = tqList;
	}

	public TestQuestions getTq() {
		return tq;
	}

	public void setTq(TestQuestions tq) {
		this.tq = tq;
	}

	public String getTeamOrGame() {
		return teamOrGame;
	}

	public void setTeamOrGame(String teamOrGame) {
		this.teamOrGame = teamOrGame;
	}

	public List<User> getUserList2() {
		return userList2;
	}

	public void setUserList2(List<User> userList2) {
		this.userList2 = userList2;
	}

	public CompetitionTeam getCompetitionTeam() {
		return competitionTeam;
	}

	public void setCompetitionTeam(CompetitionTeam competitionTeam) {
		this.competitionTeam = competitionTeam;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public CompetitionOnline getCompetitionOnline() {
		return competitionOnline;
	}

	public void setCompetitionOnline(CompetitionOnline competitionOnline) {
		this.competitionOnline = competitionOnline;
	}

	public BattlePlatform getBattlePlatform() {
		return battlePlatform;
	}

	public void setBattlePlatform(BattlePlatform battlePlatform) {
		this.battlePlatform = battlePlatform;
	}

	public String getBattleCode() {
		return battleCode;
	}

	public void setBattleCode(String battleCode) {
		this.battleCode = battleCode;
	}
}
