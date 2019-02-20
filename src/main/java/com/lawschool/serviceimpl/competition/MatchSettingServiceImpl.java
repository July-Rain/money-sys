package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.UserQuestRecord;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.beans.competition.bak.BattleTopicSettingBak;
import com.lawschool.beans.competition.bak.CompetitionOnlineBak;
import com.lawschool.beans.competition.bak.MatchSettingBak;
import com.lawschool.dao.competition.MatchSettingDao;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.UserQuestRecordService;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.MatchSettingService;
import com.lawschool.service.competition.bak.BattleTopicSettingBakService;
import com.lawschool.service.competition.bak.CompetitionOnlineBakService;
import com.lawschool.service.competition.bak.MatchSettingBakService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MatchSettingServiceImpl  extends ServiceImpl<MatchSettingDao, MatchSetting> implements MatchSettingService {

	@Autowired
	private MatchSettingDao matchSettingDao;

	@Autowired
	private BattleTopicSettingService battleTopicSettingService;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MatchSettingBakService matchSettingBakService;

	@Autowired
	private BattleTopicSettingBakService battleTopicSettingBakService;
	@Autowired
	private TestQuestionService testQuestionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private UserQuestRecordService userQuestRecordService;
	@Override
	public List<MatchSetting> list() {

		List<MatchSetting> list=matchSettingDao.list();

		return list;
	}


	@Override
	@SysLog("查询,添加，删除")
	@Transactional(rollbackFor = Exception.class)
	public void deleteAll() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		User u= (User) request.getSession().getAttribute("user");
		User u = (User) SecurityUtils.getSubject().getPrincipal();

		//因为对战题目配置表里面的 数据 还有的是  打擂台的  数据 ，不仅仅是属于在线比武配置的，所以 要反查，先根据在线比武设置表id去找对战题目配置表数据，先删题目配置 再删在线比武配置

		List<MatchSettingBak> MatchSettingbakList=new ArrayList<MatchSettingBak>();
		List<BattleTopicSettingBak> battleTopicSettingbakList=new ArrayList<BattleTopicSettingBak>();

		EntityWrapper<MatchSetting> ew = new EntityWrapper<>();
//		ew.setSqlSelect("id");
		List<MatchSetting> matchSettinglist=this.selectList(ew);
		if(matchSettinglist.size()!=0)
		{
			for(MatchSetting matchSetting:matchSettinglist)
			{
				MatchSettingBak matchSettingBakBak=new MatchSettingBak();
				BeanUtils.copyProperties(matchSetting, matchSettingBakBak);
				matchSettingBakBak.setDelPeople(u.getId());
				matchSettingBakBak.setDelTime(new Date());
				MatchSettingbakList.add(matchSettingBakBak);
			}
//插
			matchSettingBakService.insertBatch(MatchSettingbakList);

			String[] ids=new String[matchSettinglist.size()];
			for(int i=0;i<matchSettinglist.size();i++)
			{
				ids[i]=matchSettinglist.get(i).getId();
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

			battleTopicSettingService.delete(new EntityWrapper<BattleTopicSetting>().in("FOREIGN_KEY_ID", Arrays.asList(ids)));
			this.delete(new EntityWrapper<MatchSetting>());
		}
		else
		{
			return;
		}

	}


	@Override
	@SysLog("保存")
	@Transactional(rollbackFor = Exception.class)
	public void save(MatchSetting matchSetting) {

		//去作用域中取user
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		User u= (User) request.getSession().getAttribute("user");
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		matchSetting.setId(IdWorker.getIdStr());

		matchSetting.setCreatePeople(u.getId());     //创建人id
		matchSetting.setCreateTime(new Date());   	//创建时间
		matchSetting.setCreateDept(u.getOrgCode());  //所属部门code



		this.insert(matchSetting);

		//加完之后  还要循环加对战题目配置
		int topicNum = Integer.parseInt(matchSetting.getTopicNum());//得到题量   决定循环对战题目的次数，其实和 对象里面的 对战题目的集合是一个数字.
		for (int i=0;i<topicNum;i++)
		{
			//判断是否统一配置
			String uniformRules=	matchSetting.getUniformRules();
			if(uniformRules.equals("0"))//不是统一配置
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignKeyId(matchSetting.getId());
				battleTopicSetting.setType("99991");  //存字典掉的code //加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
				battleTopicSetting.setHowManySmall((i+1));//第几关
				battleTopicSetting.setCreatePeople(u.getId());     //创建人id
				battleTopicSetting.setCreateTime(new Date());   	//创建时间
				battleTopicSetting.setCreateDept(u.getOrgCode());  //所属部门code



				battleTopicSetting.setKnowledgeId(matchSetting.getBattleTopicSettingList().get(i).getKnowledgeId());   //知识点 //像这种的数据来源 就是在之前实体里面的取   不一样的配置  所以下标要一致
				battleTopicSetting.setQuestionDifficulty(matchSetting.getBattleTopicSettingList().get(i).getQuestionDifficulty());//试题难度
				battleTopicSetting.setWhetherGetIntegral(matchSetting.getBattleTopicSettingList().get(i).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setScore(matchSetting.getBattleTopicSettingList().get(i).getScore());//获得分值
				battleTopicSetting.setQuestionType(matchSetting.getBattleTopicSettingList().get(i).getQuestionType());//获得分值

				battleTopicSettingService.insert(battleTopicSetting);
			}

			else if(uniformRules.equals("1"))
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignKeyId(matchSetting.getId());
				battleTopicSetting.setType("99991");  //存字典掉的code //加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
				battleTopicSetting.setHowManySmall(i+1);//第几关
				battleTopicSetting.setCreatePeople(u.getId());     //创建人id
				battleTopicSetting.setCreateTime(new Date());   	//创建时间
				battleTopicSetting.setCreateDept(u.getOrgCode());  //所属部门code

				battleTopicSetting.setKnowledgeId(matchSetting.getBattleTopicSettingList().get(0).getKnowledgeId());//像这种的数据来源 就是在之前实体里面的取   一样的配置  所以下标就找第一个  也就只有一个数据
				battleTopicSetting.setQuestionDifficulty(matchSetting.getBattleTopicSettingList().get(0).getQuestionDifficulty());//试题难度
				battleTopicSetting.setWhetherGetIntegral(matchSetting.getBattleTopicSettingList().get(0).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setScore(matchSetting.getBattleTopicSettingList().get(0).getScore());//获得分值
				battleTopicSetting.setQuestionType(matchSetting.getBattleTopicSettingList().get(0).getQuestionType());//获得分值
				battleTopicSettingService.insert(battleTopicSetting);
			}
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
	public MatchSetting findAll() {
		MatchSetting  matchSetting=this.selectOne(new EntityWrapper<MatchSetting>());//得到list
//		for(int i=0;i<list.size();i++)
//		{
		//通过配置大关的id找到关联的小关配置信息,
//			     	List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",list.get(i).getId()).orderBy("HOW_MANY_SMALL",true));
		List<BattleTopicSetting> battleTopicSettingList =battleTopicSettingService.selectListByBaBaId(matchSetting.getId());
		List<BattleTopicSetting> list2=new ArrayList();
		if(battleTopicSettingList.size()==0)
		{
			return null;
		}
		if(matchSetting.getUniformRules().equals("1"))//如果这个大关是统一配置  那么就存一个在list
		{
			list2.add(battleTopicSettingList.get(0));
			//将小关信息放入对应的大关里面  一起返回给前端
			matchSetting.setBattleTopicSettingList(list2);
		}
		else
		{
			//将小关信息放入对应的大关里面  一起返回给前端
			matchSetting.setBattleTopicSettingList(battleTopicSettingList);
		}

//		}
		return matchSetting;
	}
	@Override
	public MatchSetting findAll2() {
		MatchSetting  matchSetting=this.list().get(0);
//		for(int i=0;i<list.size();i++)
//		{
		//通过配置大关的id找到关联的小关配置信息,
//			     	List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",list.get(i).getId()).orderBy("HOW_MANY_SMALL",true));
		List<BattleTopicSetting> battleTopicSettingList =battleTopicSettingService.selectListByBaBaId(matchSetting.getId());
		List<BattleTopicSetting> list2=new ArrayList();
		if(battleTopicSettingList.size()==0)
		{
			return null;
		}

			//将小关信息放入对应的大关里面  一起返回给前端
			matchSetting.setBattleTopicSettingList(battleTopicSettingList);
//		}
		return matchSetting;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<TestQuestions> getQuest() {

		List<TestQuestions> qList= new ArrayList<TestQuestions>();
		MatchSetting matchSetting=findAll2();//获取这个大关里面的所有小关的信息
		//循环咯
		for(BattleTopicSetting battleTopicSetting:matchSetting.getBattleTopicSettingList())
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
	public void updateWin(MatchSetting matchSetting, String uid) {
		matchSetting.setWinId(uid);
		matchSetting.setLastTime(new Date());
		this.updateById(matchSetting);
	}

	@Override
	public void saveQuestion(TestQuestions testQuestions, String myanswer,String userid) {
		//去作用域中取user
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		User u= (User) request.getSession().getAttribute("user");
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
		userQuestRecord.setSource("leitai");//添加来源
		userQuestRecordService.insert(userQuestRecord);
	}
}
