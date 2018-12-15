package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.dao.competition.CompetitionOnlineDao;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.CompetitionOnlineService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionOnlineServiceImpl extends ServiceImpl<CompetitionOnlineDao, CompetitionOnline> implements CompetitionOnlineService {


	@Autowired
	private CompetitionOnlineDao ompetitionOnlineDao;
	@Autowired
	private BattleTopicSettingService battleTopicSettingService;


	@Override
	public List<CompetitionOnline> list() {
		return this.selectList(new EntityWrapper<CompetitionOnline>());
	}

	@Override
	public CompetitionOnline info(String id) {
		CompetitionOnline competitiononline=  this.selectOne(new EntityWrapper<CompetitionOnline>().eq("ID",id));
		return competitiononline;
	}

	@Override
	public void save(CompetitionOnline competitionOnline) {
		competitionOnline.setId(IdWorker.getIdStr());
		System.out.println(competitionOnline);

		this.insert(competitionOnline);

		//加完之后  还要循环加对战题目配置
		int topicNum = Integer.parseInt(competitionOnline.getTopicNum());//得到题量   决定循环对战题目的次数，其实和 对象里面的 对战题目的集合是一个数字
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

				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(i).getKnowledgeId());   //知识点 //像这种的数据来源 就是在之前实体里面的取   不一样的配置  所以下标要一致
				battleTopicSetting.setQuestionDifficulty(competitionOnline.getBattleTopicSettingList().get(i).getQuestionDifficulty());//试题难度
				battleTopicSetting.setWhetherGetIntegral(competitionOnline.getBattleTopicSettingList().get(i).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setScore(competitionOnline.getBattleTopicSettingList().get(i).getScore());//获得分值
				battleTopicSetting.setQuestionType(competitionOnline.getBattleTopicSettingList().get(i).getQuestionType());//获得分值

				battleTopicSettingService.insert(battleTopicSetting);
			}

			else if(uniformRules.equals("1"))
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignKeyId(competitionOnline.getId());
				battleTopicSetting.setType("99992");  //存字典掉的code //加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
				battleTopicSetting.setHowManySmall(i+1);//第几关

				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(0).getKnowledgeId());//像这种的数据来源 就是在之前实体里面的取   一样的配置  所以下标就找第一个  也就只有一个数据
				battleTopicSetting.setQuestionDifficulty(competitionOnline.getBattleTopicSettingList().get(0).getQuestionDifficulty());//试题难度
				battleTopicSetting.setWhetherGetIntegral(competitionOnline.getBattleTopicSettingList().get(0).getWhetherGetIntegral());//是否获得积分
				battleTopicSetting.setScore(competitionOnline.getBattleTopicSettingList().get(0).getScore());//获得分值
				battleTopicSetting.setQuestionType(competitionOnline.getBattleTopicSettingList().get(0).getQuestionType());//获得分值
				battleTopicSettingService.insert(battleTopicSetting);
			}
		}




	}

	@Override
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
	public void deleteAll() {
		//因为对战题目配置表里面的 数据 还有的是  打擂台的  数据 ，不仅仅是属于在线比武配置的，所以 要反查，先根据在线比武设置表id去找对战题目配置表数据，先删题目配置 再删在线比武配置
		EntityWrapper<CompetitionOnline> ew = new EntityWrapper<>();
		  ew.setSqlSelect("id");
	      List<CompetitionOnline> competitionOnlinelist=this.selectList(ew);
	      if(competitionOnlinelist.size()!=0)
	      {
			String[] ids=new String[competitionOnlinelist.size()];
			for(int i=0;i<competitionOnlinelist.size();i++)
			{
				ids[i]=competitionOnlinelist.get(i).getId();
			}
			  battleTopicSettingService.delete(new EntityWrapper<BattleTopicSetting>().in("FOREIGN_KEY_ID",Arrays.asList(ids)));
			  this.delete(new EntityWrapper<CompetitionOnline>());
		  }
		  else
		  {
		  	return;
		  }

	}
}
