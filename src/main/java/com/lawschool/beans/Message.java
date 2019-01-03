package com.lawschool.beans;

import com.google.gson.annotations.Expose;
import com.lawschool.beans.competition.BattlePlatform;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO类，用来存放聊天的消息
 * @author 孙小康
 *
 */
public class Message {

	//发送者
	@Expose
	public String from;
	//发送者名称
	@Expose
	public String fromName;
	//接收者
	@Expose
	public String to;
	//发送的文本
	@Expose
	public String text;
	//发送日期
	@Expose
	public Date date;

	// 对战平台认证码（其实和id一样 要唯一啊）
	@Expose
	public String battleCode;

	//当前答到第几题
	@Expose
	public String nowtimu;



	//在线用户列表
	@Expose
	List<User> userList = new ArrayList<>();
	@Expose
	List<TestQuestions> tqList=new ArrayList<TestQuestions>();
	//在线比武配置
	@Expose
	CompetitionOnline competitionOnline=new CompetitionOnline();

	//打擂台配置
	@Expose
	MatchSetting matchSetting=new MatchSetting();


	//对战平台
	@Expose
	BattlePlatform battlePlatform=new BattlePlatform();

	//我的答案
	@Expose
	public String myanswer;
	//题目
	@Expose
	TestQuestions tq=new TestQuestions();

//	我的分数
	@Expose
    public String mycore;
//	对手的分数
	@Expose
	public String youcore;
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
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
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
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

	public String getBattleCode() {
		return battleCode;
	}

	public void setBattleCode(String battleCode) {
		this.battleCode = battleCode;
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

	public CompetitionOnline getCompetitionOnline() {
		return competitionOnline;
	}

	public void setCompetitionOnline(CompetitionOnline competitionOnline) {
		this.competitionOnline = competitionOnline;
	}

	public String getMycore() {
		return mycore;
	}

	public void setMycore(String mycore) {
		this.mycore = mycore;
	}

	public String getYoucore() {
		return youcore;
	}

	public void setYoucore(String youcore) {
		this.youcore = youcore;
	}

	public String getMyanswer() {
		return myanswer;
	}

	public void setMyanswer(String myanswer) {
		this.myanswer = myanswer;
	}

	public TestQuestions getTq() {
		return tq;
	}

	public void setTq(TestQuestions tq) {
		this.tq = tq;
	}

	public MatchSetting getMatchSetting() {
		return matchSetting;
	}

	public void setMatchSetting(MatchSetting matchSetting) {
		this.matchSetting = matchSetting;
	}

	public BattlePlatform getBattlePlatform() {
		return battlePlatform;
	}

	public void setBattlePlatform(BattlePlatform battlePlatform) {
		this.battlePlatform = battlePlatform;
	}
}
