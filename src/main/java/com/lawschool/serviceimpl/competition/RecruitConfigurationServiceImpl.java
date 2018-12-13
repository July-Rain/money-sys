package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.dao.competition.RecruitConfigurationDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.service.competition.RecruitConfigurationService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	@Override
	public List<RecruitConfiguration> findAll() {
			List<RecruitConfiguration>  list=this.selectList(new EntityWrapper<RecruitConfiguration>());//得到闯关配置大关的list
			for(int i=0;i<list.size();i++)
			{
				//通过配置大关的id找到关联的小关配置信息,其实条件里面还有个应该是第几小题排序，懒的写
				List<RecruitCheckpointConfiguration> recruitCheckpointConfigurationList =recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",list.get(0).getId()));
				//将小关信息放入对应的大关里面  一起返回给前端
				list.get(0).setRecruitCheckpointConfigurationList(recruitCheckpointConfigurationList);
			}
		 	return list;
	}


	@Override
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
	public void save(List<RecruitConfiguration> list) {
		//确定是不是统一配置
		Boolean flag=true;//现在给个死值  默认为true
		for(int i=0;i<list.size();i++)
		{
			RecruitConfiguration reConfation=list.get(i);
			reConfation.setId(IdWorker.getIdStr());
			reConfation.setMarkNumOrder(i+1);

			this.insert(reConfation);
			//甲烷大关信息  不要忘了小关

			//得到小关数量
			int smallNum= Integer.parseInt(reConfation.getSmallNum());
			if(flag)
			{
				for(int k=0;k<smallNum;k++)
				{
					RecruitCheckpointConfiguration recruitCheckpointConfiguratio=new RecruitCheckpointConfiguration();
					recruitCheckpointConfiguratio.setId(IdWorker.getIdStr());
					recruitCheckpointConfiguratio.setRecruitConfigurationId(reConfation.getId());//设置闯关配置对应的大关的id
					recruitCheckpointConfiguratio.setHowManySmall(k+1);//第几小关

					recruitCheckpointConfiguratio.setSpecialKnowledgeId(reConfation.getRecruitCheckpointConfigurationList().get(0).getSpecialKnowledgeId());//专项知识id
					recruitCheckpointConfiguratio.setItemType(reConfation.getRecruitCheckpointConfigurationList().get(0).getItemType());//试题类型
					recruitCheckpointConfiguratio.setItemDifficulty(reConfation.getRecruitCheckpointConfigurationList().get(0).getItemDifficulty());//试题难度
					recruitCheckpointConfiguratio.setCrossingPoints(reConfation.getRecruitCheckpointConfigurationList().get(0).getCrossingPoints());//关卡积分
					recruitCheckpointConfiguratio.setMarkReward(reConfation.getRecruitCheckpointConfigurationList().get(0).getMarkReward());//大关是否奖励
					recruitCheckpointConfiguratio.setRewardScore(reConfation.getRecruitCheckpointConfigurationList().get(0).getRewardScore());//大关奖励分值

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
//					reConfation.getRecruitCheckpointConfigurationList().get(k);   其他的属性 在这里面取   因为不是统一 配置  数量和 小关数量是一样的  下表页数一样的   可以通用
					recruitCheckpointConfiguratio.setSpecialKnowledgeId(reConfation.getRecruitCheckpointConfigurationList().get(k).getSpecialKnowledgeId());//专项知识id
					recruitCheckpointConfiguratio.setItemType(reConfation.getRecruitCheckpointConfigurationList().get(k).getItemType());//试题类型
					recruitCheckpointConfiguratio.setItemDifficulty(reConfation.getRecruitCheckpointConfigurationList().get(k).getItemDifficulty());//试题难度
					recruitCheckpointConfiguratio.setCrossingPoints(reConfation.getRecruitCheckpointConfigurationList().get(k).getCrossingPoints());//关卡积分
					recruitCheckpointConfiguratio.setMarkReward(reConfation.getRecruitCheckpointConfigurationList().get(k).getMarkReward());//大关是否奖励
					recruitCheckpointConfiguratio.setRewardScore(reConfation.getRecruitCheckpointConfigurationList().get(k).getRewardScore());//大关奖励分值
					recruitCheckpointConfigurationService.insert(recruitCheckpointConfiguratio);//存吧
				}
			}

		}
	}

    //删除
	@Override
	public void deleteAll() {
		this.delete(new EntityWrapper<RecruitConfiguration>());

		//删完自己的 还要删关联的 小关表啊
		recruitCheckpointConfigurationService.delete(new EntityWrapper<RecruitCheckpointConfiguration>());

	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
//		String code = (String)params.get("code");
//		String value = (String)params.get("value");
//		String status = (String)params.get("status");
		EntityWrapper<RecruitConfiguration> ew = new EntityWrapper<>();
		ew.orderBy("MARK_NUM_ORDER",true);
//		if(UtilValidate.isNotEmpty(code)){
//			ew.like("code",code);
//		}
//		if(UtilValidate.isNotEmpty(value)){
//			ew.like("value",value);
//		}
//		if(UtilValidate.isNotEmpty(status)){
//			ew.like("status",status);
//		}
		Page<RecruitConfiguration> page = this.selectPage(
				new Query<RecruitConfiguration>(params).getPage(),ew);

		return new PageUtils(page);
	}


	//根据id来找子类数据集合
	@Override
	public List<RecruitCheckpointConfiguration> getSonList(String id) {

		 List<RecruitCheckpointConfiguration> list= recruitCheckpointConfigurationService.selectList(new EntityWrapper<RecruitCheckpointConfiguration>().eq("RECRUIT_CONFIGURATION_ID",id).orderBy("HOW_MANY_SMALL",true));

		return list;
	}


	@Override
	public List<CommonForm> findAllTopic() {
			List list=new ArrayList();
		List<CommonForm> CommonFormList= topicTypeService.findAll(list);
		return CommonFormList;
	}
}
