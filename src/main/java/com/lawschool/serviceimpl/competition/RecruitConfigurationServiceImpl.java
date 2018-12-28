package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.UserQuestRecord;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.beans.competition.bak.RecruitCheckpointConfigurationBak;
import com.lawschool.beans.competition.bak.RecruitConfigurationBak;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.dao.competition.RecruitConfigurationDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.UserQuestRecordService;
import com.lawschool.service.competition.CompetitionRecordService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.service.competition.RecruitConfigurationService;
import com.lawschool.service.competition.bak.RecruitCheckpointConfigurationBakService;
import com.lawschool.service.competition.bak.RecruitConfigurationBakService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RecruitConfigurationServiceImpl  extends ServiceImpl<RecruitConfigurationDao, RecruitConfiguration> implements RecruitConfigurationService {
	
	@Autowired
	private RecruitConfigurationDao recruitconfigurationDao;
	@Autowired
	private RecruitCheckpointConfigurationService recruitCheckpointConfigurationService;
	@Autowired
	private TopicTypeService topicTypeService;


	@Autowired
	private RecruitConfigurationBakService recruitConfigurationbakService;
	@Autowired
	private RecruitCheckpointConfigurationBakService recruitCheckpointConfigurationbakService;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private TestQuestionService testQuestionService;
	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserQuestRecordService userQuestRecordService;

	@Autowired
	private CompetitionRecordService competitionRecordService;

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<RecruitConfiguration> findAll() {
			List<RecruitConfiguration>  list=this.selectList(new EntityWrapper<RecruitConfiguration>());//得到闯关配置大关的list
			for(int i=0;i<list.size();i++)
			{
				//通过配置大关的id找到关联的小关配置信息,
//			     	List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",list.get(i).getId()).orderBy("HOW_MANY_SMALL",true));
			        	List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectListByBaBaId(list.get(i).getId());
			     		List<RecruitCheckpointConfiguration> list2=new ArrayList();
			     	if(recruitCheckpointConfigurationList.size()==0)
			     	{
						return null;
					}
			     	if(list.get(i).getUnifyConfiguration().equals("1"))//如果这个大关是统一配置  那么就存一个在list
					{
						list2.add(recruitCheckpointConfigurationList.get(0));
						//将小关信息放入对应的大关里面  一起返回给前端
						list.get(i).setRecruitCheckpointConfigurationList(list2);
					}
					else
					{
						//将小关信息放入对应的大关里面  一起返回给前端
						list.get(i).setRecruitCheckpointConfigurationList(recruitCheckpointConfigurationList);
					}

			}
		 	return list;
	}

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<RecruitConfiguration> findAll2() {
		List<RecruitConfiguration>  list=this.selectList(new EntityWrapper<RecruitConfiguration>());//得到闯关配置大关的list
		for(int i=0;i<list.size();i++)
		{
			//通过配置大关的id找到关联的小关配置信息,
//			 List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",list.get(i).getId()).orderBy("HOW_MANY_SMALL",true));
			List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectListByBaBaId(list.get(i).getId());
			list.get(i).setRecruitCheckpointConfigurationList(recruitCheckpointConfigurationList);
		}
		return list;
	}
	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public RecruitConfiguration info(String id) {
		//其实插单个 和 插findall一样  技术少个循环

		RecruitConfiguration recruitConfiguration=  this.selectOne(new EntityWrapper<RecruitConfiguration>().eq("ID",id));
		//通过配置大关的id找到关联的小关配置信息,其实条件里面还有个应该是第几小题排序，懒的写
		List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",id));
		//将小关信息放入对应的大关里面  一起返回给前端
		recruitConfiguration.setRecruitCheckpointConfigurationList(recruitCheckpointConfigurationList);
		return recruitConfiguration;
	}


	@Override
	@SysLog("保存")
	@Transactional(rollbackFor = Exception.class)
	public void save(List<RecruitConfiguration> list) {
        //去作用域中取user
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		User u= (User) request.getSession().getAttribute("user");
		for(int i=0;i<list.size();i++)
		{
			RecruitConfiguration reConfation=list.get(i);
			reConfation.setId(IdWorker.getIdStr());
			reConfation.setCreatePeople(u.getId());     //创建人id
			reConfation.setCreateTime(new Date());   	//创建时间
			reConfation.setCreateDept(u.getOrgCode());  //所属部门code

//			reConfation.setMarkNumOrder(i+1);

			this.insert(reConfation);
			//甲烷大关信息  不要忘了小关

			//得到小关数量
			int smallNum= Integer.parseInt(reConfation.getSmallNum());
			if(reConfation.getUnifyConfiguration().equals("1"))
			{
				for(int k=0;k<smallNum;k++)
				{
					RecruitCheckpointConfiguration recruitCheckpointConfiguratio=new RecruitCheckpointConfiguration();
					recruitCheckpointConfiguratio.setId(IdWorker.getIdStr());
					recruitCheckpointConfiguratio.setRecruitConfigurationId(reConfation.getId());//设置闯关配置对应的大关的id
					recruitCheckpointConfiguratio.setHowManySmall(k+1);//第几小关
					recruitCheckpointConfiguratio.setUnifyConfiguration("1");//是统一配置
					recruitCheckpointConfiguratio.setCreatePeople(u.getId());     //创建人id
					recruitCheckpointConfiguratio.setCreateTime(new Date());   	//创建时间
					recruitCheckpointConfiguratio.setCreateDept(u.getOrgCode());  //所属部门code

					recruitCheckpointConfiguratio.setSpecialKnowledgeId(reConfation.getRecruitCheckpointConfigurationList().get(0).getSpecialKnowledgeId());//专项知识id
					recruitCheckpointConfiguratio.setItemType(reConfation.getRecruitCheckpointConfigurationList().get(0).getItemType());//试题类型
					recruitCheckpointConfiguratio.setItemDifficulty(reConfation.getRecruitCheckpointConfigurationList().get(0).getItemDifficulty());//试题难度
					recruitCheckpointConfiguratio.setCrossingPoints(reConfation.getRecruitCheckpointConfigurationList().get(0).getCrossingPoints());//关卡积分
					recruitCheckpointConfiguratio.setMarkReward(reConfation.getMarkReward());//大关是否奖励
					recruitCheckpointConfiguratio.setRewardScore(reConfation.getRewardScore());//大关奖励分值

//					reConfation.getRecruitCheckpointConfigurationList().get(0);   其他的属性 在这里面取   因为是统一 配置   就一个 默认下标0
					recruitCheckpointConfigurationService.insert(recruitCheckpointConfiguratio);//存吧
				}
			}
			else
			{
				for(int k=0;k<smallNum;k++)
				{
					RecruitCheckpointConfiguration recruitCheckpointConfiguratio=new RecruitCheckpointConfiguration();
					recruitCheckpointConfiguratio.setId(IdWorker.getIdStr());
					recruitCheckpointConfiguratio.setRecruitConfigurationId(reConfation.getId());//设置闯关配置对应的大关的id
					recruitCheckpointConfiguratio.setHowManySmall(k+1);//第几小关
					recruitCheckpointConfiguratio.setUnifyConfiguration("0");//不是统一配置

					recruitCheckpointConfiguratio.setCreatePeople(u.getId());     //创建人id
					recruitCheckpointConfiguratio.setCreateTime(new Date());   	//创建时间
					recruitCheckpointConfiguratio.setCreateDept(u.getOrgCode());  //所属部门code

//					reConfation.getRecruitCheckpointConfigurationList().get(k);   其他的属性 在这里面取   因为不是统一 配置  数量和 小关数量是一样的  下表页数一样的   可以通用
					recruitCheckpointConfiguratio.setSpecialKnowledgeId(reConfation.getRecruitCheckpointConfigurationList().get(k).getSpecialKnowledgeId());//专项知识id
					recruitCheckpointConfiguratio.setItemType(reConfation.getRecruitCheckpointConfigurationList().get(k).getItemType());//试题类型
					recruitCheckpointConfiguratio.setItemDifficulty(reConfation.getRecruitCheckpointConfigurationList().get(k).getItemDifficulty());//试题难度
					recruitCheckpointConfiguratio.setCrossingPoints(reConfation.getRecruitCheckpointConfigurationList().get(k).getCrossingPoints());//关卡积分
					recruitCheckpointConfiguratio.setMarkReward(reConfation.getMarkReward());//大关是否奖励
					recruitCheckpointConfiguratio.setRewardScore(reConfation.getRewardScore());//大关奖励分值
					recruitCheckpointConfigurationService.insert(recruitCheckpointConfiguratio);//存吧
				}
			}

		}
	}

    //删除
	@Override
	@SysLog("查。添加。删除")
	@Transactional(rollbackFor = Exception.class)
	public void deleteAll() {
		//去作用域中取user
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		User u= (User) request.getSession().getAttribute("user");
		//删除主表 连附表的数据要一起删除
		//但是涉及到bak备份表   所以我他妈的 还要先查 在插  在 删   （反正操作的是所有，不用考虑条件）
		List<RecruitConfigurationBak> recruitConfigurationbakAll=new ArrayList<>();
		List<RecruitCheckpointConfigurationBak> recruitCheckpointConfigurationbakAll=new ArrayList<>();

		//1.查
		List<RecruitConfiguration> recruitConfigurationAll=this.selectList(new EntityWrapper<RecruitConfiguration>());
		List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationAll=recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>());
		//2.插
		for(RecruitConfiguration recruitConfiguration:recruitConfigurationAll)
		{
			RecruitConfigurationBak recruitConfigurationbak=new RecruitConfigurationBak();
			BeanUtils.copyProperties(recruitConfiguration, recruitConfigurationbak);
			recruitConfigurationbak.setDelPeople(u.getId());
			recruitConfigurationbak.setDelTime(new Date());
			recruitConfigurationbakAll.add(recruitConfigurationbak);
		}
		for(RecruitCheckpointConfiguration recruitCheckpointConfiguration:recruitCheckpointConfigurationAll)
		{
			RecruitCheckpointConfigurationBak recruitCheckpointConfigurationbak=new RecruitCheckpointConfigurationBak();
			BeanUtils.copyProperties(recruitCheckpointConfiguration, recruitCheckpointConfigurationbak);
			recruitCheckpointConfigurationbak.setDelPeople(u.getId());
			recruitCheckpointConfigurationbak.setDelTime(new Date());
			recruitCheckpointConfigurationbakAll.add(recruitCheckpointConfigurationbak);
		}
		if(recruitConfigurationbakAll.size()>0)
		{
			recruitConfigurationbakService.insertBatch(recruitConfigurationbakAll);
			recruitCheckpointConfigurationbakService.insertBatch(recruitCheckpointConfigurationbakAll);
		}



		//3.删  原来的表
		this.delete(new EntityWrapper<RecruitConfiguration>());
		//删完自己的 还要删关联的 小关表啊
		recruitCheckpointConfigurationService.delete(new EntityWrapper<RecruitCheckpointConfiguration>());

		//还有把之前配置下的成绩记录状态位 位0

		competitionRecordService.updatedata();


	}

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public PageUtils queryPage(Map<String, Object> params) {
		EntityWrapper<RecruitConfiguration> ew = new EntityWrapper<>();
		ew.orderBy("MARK_NUM_ORDER",true);

		Page<RecruitConfiguration> page = this.selectPage(
				new Query<RecruitConfiguration>(params).getPage(),ew);

		return new PageUtils(page);
	}


	//根据id来找子类数据集合
	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<RecruitCheckpointConfiguration> getSonList(String id) {

//		 List<RecruitCheckpointConfiguration> list= recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",id).orderBy("HOW_MANY_SMALL",true));
		//通过他爸爸的id找数据
		List<RecruitCheckpointConfiguration> list= recruitCheckpointConfigurationService.selectListByBaBaId(id);
		return list;
	}


	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<CommonForm> findAllTopic() {
			List list=new ArrayList();
		List<CommonForm> CommonFormList= topicTypeService.findAll(list);
		return CommonFormList;
	}


	@Override
	@SysLog("查询闯关题目")
	@Transactional(rollbackFor = Exception.class)
	public List<TestQuestions> getQuest(RecruitConfiguration recruitConfiguration) {

		List<TestQuestions> qList= new ArrayList<TestQuestions>();
		List<RecruitCheckpointConfiguration> xiaoguanList=recruitConfiguration.getRecruitCheckpointConfigurationList();//获取这个大关里面的所有小关的信息
		//循环咯
		for(RecruitCheckpointConfiguration recruitCheckpointConfiguration:xiaoguanList)
		{
			TestQuestions tq=new TestQuestions();
			tq.setQuestionDifficulty(recruitCheckpointConfiguration.getItemDifficulty());
			tq.setQuestionType(recruitCheckpointConfiguration.getItemType());
			tq.setSpecialKnowledgeId(recruitCheckpointConfiguration.getSpecialKnowledgeId());

			TestQuestions testquestions=testQuestionService.findByEntity(tq);
			testquestions.setAnswerList(answerService.getAnswerByQid(testquestions.getId()));
			qList.add(testquestions);
		}

		return qList;
	}


	@Override
	@SysLog("保存做过的闯关题目")
	@Transactional(rollbackFor = Exception.class)
	public void saveQuestion(TestQuestions testQuestions, String myanswer) {
		//去作用域中取user
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		User u= (User) request.getSession().getAttribute("user");
		UserQuestRecord userQuestRecord=new UserQuestRecord();
		userQuestRecord.setId(IdWorker.getIdStr());//id
		userQuestRecord.setUserId(u.getId());//userid
		userQuestRecord.setQuestId(testQuestions.getId());//题目id
		userQuestRecord.setOptTime(new Date());//保存时间
		userQuestRecord.setMyAswerId(myanswer);//对应题目答案表的id
		userQuestRecord.setRightAnswerId(testQuestions.getAnswerId());//这题的正确答案，对应题目答案表的id
		userQuestRecord.setQuestionDifficulty(testQuestions.getQuestionDifficulty());//难度
		userQuestRecord.setQuestionType(testQuestions.getQuestionType());//题目类型
		userQuestRecord.setSpecialKnowledgeId(testQuestions.getSpecialKnowledgeId());//知识点
		userQuestRecord.setSource("Checkpoint");//添加来源
		userQuestRecordService.insert(userQuestRecord);
	}
}
