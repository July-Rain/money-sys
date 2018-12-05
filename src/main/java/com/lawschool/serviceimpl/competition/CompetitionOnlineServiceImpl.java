package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.dao.competition.CompetitionOnlineDao;
import com.lawschool.service.competition.BattleTopicSettingService;
import com.lawschool.service.competition.CompetitionOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public void save() {
		CompetitionOnline competitionOnline = new CompetitionOnline();
		competitionOnline.setId("3");
		competitionOnline.setTopicNum("32");
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
				battleTopicSetting.setForeignkeyId(competitionOnline.getId());
				battleTopicSetting.setHowManySmall((i+1)+"");//第几关

				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(i).getKnowledgeId());//像这种的数据来源 就是在之前实体里面的取   不一样的配置  所以下标要一致
				//还要加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置

			}

			else if(uniformRules.equals("1"))
			{
				BattleTopicSetting battleTopicSetting =new BattleTopicSetting();
				battleTopicSetting.setId(IdWorker.getIdStr());
				battleTopicSetting.setForeignkeyId(competitionOnline.getId());
				battleTopicSetting.setHowManySmall((i+1)+"");//第几关
				battleTopicSetting.setKnowledgeId(competitionOnline.getBattleTopicSettingList().get(0).getKnowledgeId());//像这种的数据来源 就是在之前实体里面的取   一样的配置  所以下标就找第一个  也就只有一个数据
				//还要加type   后面来区分这条数据是属于对战的  还是擂台的   元数据现在还没有  没法设置
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
}
