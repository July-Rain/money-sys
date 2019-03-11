package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.Integral;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.UserQuestRecord;
import com.lawschool.beans.competition.*;
import com.lawschool.beans.competition.bak.BattleTopicSettingBak;
import com.lawschool.beans.competition.bak.CompetitionOnlineBak;
import com.lawschool.beans.competition.bak.RecruitConfigurationBak;
import com.lawschool.beans.system.Fraction;
import com.lawschool.dao.competition.CompetitionOnlineDao;
import com.lawschool.enums.Source;
import com.lawschool.service.*;
import com.lawschool.service.competition.BattleRecordService;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.service.competition.MatchSettingService;
import com.lawschool.service.competition.bak.BattleTopicSettingBakService;
import com.lawschool.service.competition.bak.CompetitionOnlineBakService;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class CompetitionOnlineServiceImpl extends ServiceImpl<CompetitionOnlineDao, CompetitionOnline> implements CompetitionOnlineService {


	@Autowired
	private CompetitionOnlineDao ompetitionOnlineDao;
	@Autowired
	private BattleTopicSettingService battleTopicSettingService;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CompetitionOnlineBakService competitionOnlineBakService;

	@Autowired
	private BattleTopicSettingBakService battleTopicSettingBakService;

	@Autowired
	private TestQuestionService testQuestionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private UserQuestRecordService userQuestRecordService;
	@Autowired
	private BattleRecordService battleRecordService;
	@Autowired
	private IntegralService integralService;

	@Autowired
	private UserService userService;

	@Autowired
	private MatchSettingService matchSettingService;
	@Autowired
	private FractionService fractionService;



	@Override
	public List<CompetitionOnline> list() {
		return this.selectList(new EntityWrapper<CompetitionOnline>());
	}

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public CompetitionOnline findAll() {
		CompetitionOnline  competitionOnline=this.selectOne(new EntityWrapper<CompetitionOnline>());//得到在线比武的的list
			//通过配置大关的id找到关联的小关配置信息,
			List<BattleTopicSetting> battleTopicSettingList =battleTopicSettingService.selectListByBaBaId(competitionOnline.getId());
			List<BattleTopicSetting> list2=new ArrayList();
			if(battleTopicSettingList.size()==0)
			{
				return null;
			}
			if(competitionOnline.getUniformRules().equals("1"))//如果这个大关是统一配置  那么就存一个在list
			{
				list2.add(battleTopicSettingList.get(0));
				//将小关信息放入对应的大关里面  一起返回给前端
				competitionOnline.setBattleTopicSettingList(list2);
			}
			else
			{
				//将小关信息放入对应的大关里面  一起返回给前端
				competitionOnline.setBattleTopicSettingList(battleTopicSettingList);
			}
		return competitionOnline;
	}

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public CompetitionOnline info(String id) {
		CompetitionOnline competitiononline=  this.selectOne(new EntityWrapper<CompetitionOnline>().eq("ID",id));
		return competitiononline;
	}

	@Override
	@SysLog("保存")
	@Transactional(rollbackFor = Exception.class)
	public void save(CompetitionOnline competitionOnline) {

		//去作用域中取user
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		User u= (User) request.getSession().getAttribute("user");
		User u = (User) SecurityUtils.getSubject().getPrincipal();

		//到表中找积分规则
		Result result=fractionService.getFractionByType("1", Source.COMPEITIONONLINE);
		Fraction fraction=(Fraction)result.get("fraction");



		competitionOnline.setId(IdWorker.getIdStr());
		competitionOnline.setWinReward(String.valueOf(fraction.getMinScore()));//胜利者  得分
		competitionOnline.setLoserReward(String.valueOf(fraction.getMaxScore())); //失败者 得分
		competitionOnline.setCreatePeople(u.getId());     //创建人id
		competitionOnline.setCreateTime(new Date());   	//创建时间
		competitionOnline.setCreateDept(u.getOrgCode());  //所属部门code

		System.out.println(competitionOnline);

		this.insert(competitionOnline);

		//加完之后  还要循环加对战题目配置
		int topicNum = Integer.parseInt(competitionOnline.getTopicNum());//得到题量   决定循环对战题目的次数，其实和 对象里面的 对战题目的集合是一个数字.
		for (int i=0;i<topicNum;i++)
		{
			//判断是否统一配置
		   String uniformRules=	competitionOnline.getUniformRules();
			if(uniformRules.equals("0"))//不是统一配置
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignKeyId(competitionOnline.getId());
				battleTopicSetting.setType("99992");  //存字典掉的code //加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
				battleTopicSetting.setHowManySmall((i+1));//第几关
				battleTopicSetting.setCreatePeople(u.getId());     //创建人id
				battleTopicSetting.setCreateTime(new Date());   	//创建时间
				battleTopicSetting.setCreateDept(u.getOrgCode());  //所属部门code


				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(i).getKnowledgeId());   //知识点 //像这种的数据来源 就是在之前实体里面的取   不一样的配置  所以下标要一致
				battleTopicSetting.setQuestionDifficulty(competitionOnline.getBattleTopicSettingList().get(i).getQuestionDifficulty());//试题难度
//				battleTopicSetting.setWhetherGetIntegral(competitionOnline.getBattleTopicSettingList().get(i).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setWhetherGetIntegral("1");//是否获得积分
				battleTopicSetting.setScore("1");//获得分值
				battleTopicSetting.setQuestionType(competitionOnline.getBattleTopicSettingList().get(i).getQuestionType());//试题类型

				battleTopicSettingService.insert(battleTopicSetting);
			}

			else if(uniformRules.equals("1"))
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignKeyId(competitionOnline.getId());
				battleTopicSetting.setType("99992");  //存字典掉的code //加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
				battleTopicSetting.setHowManySmall(i+1);//第几关
				battleTopicSetting.setCreatePeople(u.getId());     //创建人id
				battleTopicSetting.setCreateTime(new Date());   	//创建时间
				battleTopicSetting.setCreateDept(u.getOrgCode());  //所属部门code


				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(0).getKnowledgeId());//像这种的数据来源 就是在之前实体里面的取   一样的配置  所以下标就找第一个  也就只有一个数据
				battleTopicSetting.setQuestionDifficulty(competitionOnline.getBattleTopicSettingList().get(0).getQuestionDifficulty());//试题难度
//				battleTopicSetting.setWhetherGetIntegral(competitionOnline.getBattleTopicSettingList().get(0).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setWhetherGetIntegral("1");//是否获得积分

				battleTopicSetting.setScore("1");//获得分值
				battleTopicSetting.setQuestionType(competitionOnline.getBattleTopicSettingList().get(0).getQuestionType());//试题类型
				battleTopicSettingService.insert(battleTopicSetting);
			}
		}




	}

	@Override
	@SysLog("查询，添加，删除")//弃用的方法
	@Transactional(rollbackFor = Exception.class)
	public void deleteComOnline(String id) {
		this.deleteById(id);
		//删完还要删关联表
		battleTopicSettingService.delete(new EntityWrapper<BattleTopicSetting>());
	}


	@Override
	public void updateComOnline() {
		CompetitionOnline competitionOnline=new CompetitionOnline();
		competitionOnline.setId("1");
		competitionOnline.setTopicNum("32");
		competitionOnline.setUniformRules("555");
		this.updateById(competitionOnline);
	}


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		EntityWrapper<CompetitionOnline> ew = new EntityWrapper<>();
		Page<CompetitionOnline> page = this.selectPage(
				new Query<CompetitionOnline>(params).getPage(),ew);
		return new PageUtils(page);
	}


	@Override
	@SysLog("查询，添加，删除")
	@Transactional(rollbackFor = Exception.class)
	public void deleteAll() {
		//因为对战题目配置表里面的 数据 还有的是  打擂台的  数据 ，不仅仅是属于在线比武配置的，所以 要反查，先根据在线比武设置表id去找对战题目配置表数据，先删题目配置 再删在线比武配置
		//去作用域中取user
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		User u= (User) request.getSession().getAttribute("user");
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		//删除主表 连附表的数据要一起删除
		//但是涉及到bak备份表   所以我他妈的 还要先查 在插  在 删   （反正操作的是所有，不用考虑条件）


		List<CompetitionOnlineBak> competitionOnlinelistbak=new ArrayList<CompetitionOnlineBak>();
		List<BattleTopicSettingBak> battleTopicSettingbakList=new ArrayList<BattleTopicSettingBak>();

		//1.查
		  EntityWrapper<CompetitionOnline> ew = new EntityWrapper<>();
//		  ew.setSqlSelect("id");
	      List<CompetitionOnline> competitionOnlinelist=this.selectList(ew);

	      if(competitionOnlinelist.size()!=0)
	      {
			  for(CompetitionOnline competitionOnline:competitionOnlinelist)
			  {
				  CompetitionOnlineBak competitionOnlineBak=new CompetitionOnlineBak();
				  BeanUtils.copyProperties(competitionOnline, competitionOnlineBak);
				  competitionOnlineBak.setDelPeople(u.getId());
				  competitionOnlineBak.setDelTime(new Date());
				  competitionOnlinelistbak.add(competitionOnlineBak);
			  }
//插
			  competitionOnlineBakService.insertBatch(competitionOnlinelistbak);

			String[] ids=new String[competitionOnlinelist.size()];
			for(int i=0;i<competitionOnlinelist.size();i++)
			{
				ids[i]=competitionOnlinelist.get(i).getId();
			}

			//查
			  List<BattleTopicSetting> battleTopicSettinglist=	  battleTopicSettingService.selectList(new EntityWrapper<BattleTopicSetting>().in("FOREIGN_KEY_ID",Arrays.asList(ids)));
			  for(BattleTopicSetting battleTopicSetting:battleTopicSettinglist)
			  {
				  BattleTopicSettingBak battleTopicSettingBak=new BattleTopicSettingBak();
				  BeanUtils.copyProperties(battleTopicSetting, battleTopicSettingBak);
				  battleTopicSettingBak.setDelPeople(u.getId());
				  battleTopicSettingBak.setDelTime(new Date());
				  battleTopicSettingbakList.add(battleTopicSettingBak);
			  }
			  //插
			  battleTopicSettingBakService.insertBatch(battleTopicSettingbakList);
//删
			  battleTopicSettingService.delete(new EntityWrapper<BattleTopicSetting>().in("FOREIGN_KEY_ID",Arrays.asList(ids)));
			  this.delete(new EntityWrapper<CompetitionOnline>());


			  battleRecordService.updaterecord("OnlinPk");


		  }
		  else
		  {
		  	return;
		  }

	}


	//根据id来找子类数据集合
	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<BattleTopicSetting> getSonList(String id) {

//		 List<RecruitCheckpointConfiguration> list= recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",id).orderBy("HOW_MANY_SMALL",true));
		//通过他爸爸的id找数据
		List<BattleTopicSetting> list= battleTopicSettingService.selectListByBaBaId(id);
		return list;
	}


	@Override
	public CompetitionOnline findAll2() {
		CompetitionOnline  competitionOnline=this.selectOne(new EntityWrapper<CompetitionOnline>());//得到在线比武的的list
		//通过配置大关的id找到关联的小关配置信息,
		List<BattleTopicSetting> battleTopicSettingList =battleTopicSettingService.selectListByBaBaId(competitionOnline.getId());
		if(battleTopicSettingList.size()==0)
		{
			return null;
		}
		//将小关信息放入对应的大关里面  一起返回给前端
		competitionOnline.setBattleTopicSettingList(battleTopicSettingList);
		return competitionOnline;
	}



	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<TestQuestions> getQuest() {

		List<TestQuestions> qList= new ArrayList<TestQuestions>();
		CompetitionOnline competitionOnline=findAll2();//获取这个大关里面的所有小关的信息

		//循环咯
		for(BattleTopicSetting battleTopicSetting:competitionOnline.getBattleTopicSettingList())
		{
			TestQuestions tq=new TestQuestions();
			tq.setQuestionDifficulty(battleTopicSetting.getQuestionDifficulty());
			tq.setQuestionType(battleTopicSetting.getQuestionType());
			tq.setSpecialKnowledgeId(battleTopicSetting.getKnowledgeId());

			TestQuestions testquestions=testQuestionService.findByEntity(tq);
			if(testquestions==null)
			{
				qList=null;
				break;
			}
			else{
				testquestions.setAnswerList(answerService.getAnswerByQid(testquestions.getId()));
				qList.add(testquestions);
			}

		}

		return qList;
	}




	@Override
	public void saveQuestion(TestQuestions testQuestions, String myanswer,String userid,String Source) {
	try{
		UserQuestRecord userQuestRecord=new UserQuestRecord();
		userQuestRecord.setId(IdWorker.getIdStr());//id
		userQuestRecord.setUserId(userid);//userid
		userQuestRecord.setQuestId(testQuestions.getId());//题目id
		userQuestRecord.setOptTime(new Date());//保存时间
		userQuestRecord.setMyAswerId(myanswer);//对应题目答案表的id
		userQuestRecord.setRightAnswerId(testQuestions.getAnswerId());//这题的正确答案，对应题目答案表的id
		userQuestRecord.setQuestionDifficulty(testQuestions.getQuestionDifficulty());//难度
		userQuestRecord.setQuestionType(testQuestions.getQuestionType());//题目类型
		userQuestRecord.setSpecialKnowledgeId(testQuestions.getSpecialKnowledgeId());//知识点
		userQuestRecord.setSource(Source);//添加来源
		userQuestRecordService.insert(userQuestRecord);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}

	//保存分数记录
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String recordScore(String battlePlatformId,String win,String score,String type,String uid) {

		//判断   这边 他妈的 又4种 类型     1对1  OnlinPk  ;  1对1code码 OnlinPkByCode ;    组队 teamOnline   ;擂台    leitai （擂台还要考虑擂主 连胜）;
		String s="";


		if(type.equals("OnlinPk") || type.equals("OnlinPkByCode") || type.equals("teamOnline")){
			//去己领表中 找当天的 这个人这个 闯关 所有数据  积分看看有没有 超过了上限
			//得到 上限
			Result result=fractionService.getFractionByType("1", Source.COMPEITIONONLINE);
			Fraction fraction=(Fraction)result.get("fraction");

			Float f=  fraction.getDailyLimit();//上限
			Float f2=Float.parseFloat(score); //现在的 得分
			Float f3=0f; //零时的
//			List<BattleRecord> list=battleRecordService.selectList(new EntityWrapper<BattleRecord>().eq("USER_ID",uid).eq("STATUS",1).eq("TYPE","OnlinPk").addFilter( "to_char(CREATE_TIME,'dd')=to_char(sysdate,'dd')"));
			List<BattleRecord> list=battleRecordService.selectList(new EntityWrapper<BattleRecord>().eq("USER_ID",uid).ne("TYPE","leitai").eq("STATUS",1).addFilter( "to_char(CREATE_TIME,'dd')=to_char(sysdate,'dd')"));


			for(BattleRecord battleRecord:list)
			{
				f3=f3+Float.parseFloat(battleRecord.getScore());
			}

			User u= userService.selectById(uid);
			BattleRecord battleRecord=new BattleRecord();
			battleRecord.setId(IdWorker.getIdStr());
			battleRecord.setBattlePlatformId(battlePlatformId);
			battleRecord.setCreateTime(new Date());
			battleRecord.setStatus("1");
			battleRecord.setType(type);
			battleRecord.setScore(score);
			battleRecord.setWhetherWin(win);
			battleRecord.setUserId(uid);

			battleRecordService.insert(battleRecord);

			//得到了我记录表里 有多少分了   与  上限 对比

			//如果 记录里的分数 大于了上限   ，，保存 记录 但是不添加积分
			if(f3>=f){
				s="已达到今日上限";
			}
			//如果记录里的分数 不大于上限
			else if(f3<f){
				//(f-f3)//只能再加上这么多分
				//如果这次的分数小于 还能加的分数  ，那太好了 直接加
				if(f2<=(f-f3)){

					//加完后在添加 另外的积分表
					Integral integral=new Integral();
					integral.setType("0");
					integral.setPoint(Float.parseFloat(score));
					integral.setSrc(type);
					integralService.addIntegralRecord(integral,u);

					s="获得"+score+"积分";
				}
				//如果这次的分数大于还能加的分数  ，那就只能 加 能加的分数  多了 加不了，但是记录 还是 没关系的 只是积分表
				else if(f2>(f-f3)){
					//加完后在添加 另外的积分表
					Integral integral=new Integral();
					integral.setType("0");
					integral.setPoint((f-f3));
					integral.setSrc(type);
					integralService.addIntegralRecord(integral,u);

					s="超过上限，获得"+(f-f3)+"积分";
				}
			}
		}

		if(type.equals("leitai")){
			//去己领表中 找当天的 这个人这个 闯关 所有数据  积分看看有没有 超过了上限
			//得到 上限
			Result result=fractionService.getFractionByType("1", Source.MATCH);
			Fraction fraction=(Fraction)result.get("fraction");
			Float f=  fraction.getDailyLimit();//上限
			Float f2=Float.parseFloat(score); //现在的 得分
			Float f3=0f; //零时的
			List<BattleRecord> list=battleRecordService.selectList(new EntityWrapper<BattleRecord>().eq("USER_ID",uid).eq("TYPE","leitai").eq("STATUS",1).addFilter( "to_char(CREATE_TIME,'dd')=to_char(sysdate,'dd')"));


			for(BattleRecord battleRecord:list)
			{
				f3=f3+Float.parseFloat(battleRecord.getScore());
			}

			User u= userService.selectById(uid);
			BattleRecord battleRecord=new BattleRecord();
			battleRecord.setId(IdWorker.getIdStr());
			battleRecord.setBattlePlatformId(battlePlatformId);
			battleRecord.setCreateTime(new Date());
			battleRecord.setStatus("1");
			battleRecord.setType(type);
			battleRecord.setScore(score);
			battleRecord.setWhetherWin(win);
			battleRecord.setUserId(uid);

			battleRecordService.insert(battleRecord);

			//得到了我记录表里 有多少分了   与  上限 对比

			//如果 记录里的分数 大于了上限   ，，保存 记录 但是不添加积分

			//这时候 要去看看连胜
			Boolean boo=false;
			MatchSetting matchSetting=	matchSettingService.selectOne(new EntityWrapper<MatchSetting>());
			if(Integer.parseInt(matchSetting.getWinCount())>=fraction.getQueNum())
			{
				//完成连胜的 任务
				boo=true;
			}

			if(f3>=f){
				if(boo){
					//加完后在添加 另外的积分表
					Integral integral=new Integral();
					integral.setType("0");
					integral.setPoint(fraction.getScore());
					integral.setSrc(type);
					integralService.addIntegralRecord(integral,u);

					s="已达到今日上限,获得连胜奖励"+fraction.getScore()+"积分";
				}
				else{
					s="已达到今日上限";
				}

			}
			//如果记录里的分数 不大于上限
			else if(f3<f){
				//(f-f3)//只能再加上这么多分
				//如果这次的分数小于 还能加的分数  ，那太好了 直接加
				if(f2<=(f-f3)){
					if(boo){
						//加完后在添加 另外的积分表
						Integral integral=new Integral();
						integral.setType("0");
						integral.setPoint(Float.parseFloat(score)+fraction.getScore());
						integral.setSrc(type);
						integralService.addIntegralRecord(integral,u);

						s="获得"+Float.parseFloat(score)+"积分,获得连胜奖励"+fraction.getScore()+"积分";
					}
					else{
						//加完后在添加 另外的积分表
						Integral integral=new Integral();
						integral.setType("0");
						integral.setPoint(Float.parseFloat(score));
						integral.setSrc(type);
						integralService.addIntegralRecord(integral,u);

						s="获得"+score+"积分";
					}

				}
				//如果这次的分数大于还能加的分数  ，那就只能 加 能加的分数  多了 加不了，但是记录 还是 没关系的 只是积分表
				else if(f2>(f-f3)){
					if(boo){
						//加完后在添加 另外的积分表
						Integral integral=new Integral();
						integral.setType("0");
						integral.setPoint((f-f3)+fraction.getScore());
						integral.setSrc(type);
						integralService.addIntegralRecord(integral,u);

						s="超过上限，获得"+(f-f3)+"积分,获得连胜奖励"+fraction.getScore()+"积分";
					}
					else{
						//加完后在添加 另外的积分表
						Integral integral=new Integral();
						integral.setType("0");
						integral.setPoint((f-f3));
						integral.setSrc(type);
						integralService.addIntegralRecord(integral,u);

						s="超过上限，获得"+(f-f3)+"积分";
					}

				}
			}







		}
//		User u= userService.selectById(uid);
//		BattleRecord battleRecord=new BattleRecord();
//		battleRecord.setId(IdWorker.getIdStr());
//		battleRecord.setBattlePlatformId(battlePlatformId);
//		battleRecord.setCreateTime(new Date());
//		battleRecord.setStatus("1");
//		battleRecord.setType(type);
//		battleRecord.setScore(score);
//		battleRecord.setWhetherWin(win);
//		battleRecord.setUserId(uid);
//
//		battleRecordService.insert(battleRecord);
//
//		//加完后在添加 另外的积分表
//		Integral integral=new Integral();
//		integral.setType("0");
//		integral.setPoint(Float.parseFloat(score));
//		integral.setSrc(type);
//


//		integralService.addIntegralRecord(integral,u);

		return s;

	}

}
