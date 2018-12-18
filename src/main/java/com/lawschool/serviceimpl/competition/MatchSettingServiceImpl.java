package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.dao.competition.MatchSettingDao;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MatchSettingServiceImpl  extends ServiceImpl<MatchSettingDao, MatchSetting> implements MatchSettingService {

	@Autowired
	private MatchSettingDao matchSettingDao;
	@Autowired
	private BattleTopicSettingService battleTopicSettingService;

	@Override
	public List<MatchSetting> list() {

		List<MatchSetting> list=matchSettingDao.list();

		return list;
	}


	@Override
	public void deleteAll() {
		//因为对战题目配置表里面的 数据 还有的是  打擂台的  数据 ，不仅仅是属于在线比武配置的，所以 要反查，先根据在线比武设置表id去找对战题目配置表数据，先删题目配置 再删在线比武配置
		EntityWrapper<MatchSetting> ew = new EntityWrapper<>();
		ew.setSqlSelect("id");
		List<MatchSetting> matchSettinglist=this.selectList(ew);
		if(matchSettinglist.size()!=0)
		{
			String[] ids=new String[matchSettinglist.size()];
			for(int i=0;i<matchSettinglist.size();i++)
			{
				ids[i]=matchSettinglist.get(i).getId();
			}
			battleTopicSettingService.delete(new EntityWrapper<BattleTopicSetting>().in("FOREIGN_KEY_ID", Arrays.asList(ids)));
			this.delete(new EntityWrapper<MatchSetting>());
		}
		else
		{
			return;
		}

	}


	@Override
	public void save(MatchSetting matchSetting) {
		matchSetting.setId(IdWorker.getIdStr());
		System.out.println(matchSetting);

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
}
