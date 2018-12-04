package com.lawschool.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.CompetitionOnline;
import com.lawschool.beans.RecruitCheckpointConfiguration;
import com.lawschool.beans.RecruitConfiguration;
import com.lawschool.beans.SysMenu;
import com.lawschool.dao.RecruitConfigurationDao;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.service.RecruitCheckpointConfigurationService;
import com.lawschool.service.RecruitConfigurationService;
import com.lawschool.service.SysMenuService;
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
	@Override
	public List<RecruitConfiguration> findAll() {
		 	return this.selectList(new EntityWrapper<RecruitConfiguration>());
	}


	@Override
	public RecruitConfiguration info(String id) {
		RecruitConfiguration recruitConfiguration=  this.selectOne(new EntityWrapper<RecruitConfiguration>().eq("ID",id));
		return recruitConfiguration;
	}


	@Override
	public void save() {

		//这边假如我已经得到json串了  已经处理了json  得到了一个list<RecruitConfiguration>集合
		List<RecruitConfiguration> list=new ArrayList<RecruitConfiguration>();

		//还有一步确定是不是统一配置
		Boolean flag=true;//现在给个死值  默认为true



		for(int i=0;i<list.size();i++)
		{
			RecruitConfiguration reConfation=list.get(i);
			reConfation.setId(IdWorker.getIdStr());
			reConfation.setMarkNumOrder((i+1)+"");

			this.insert(reConfation);
			//甲烷大关信息  不要忘了小关

			//得到小关数量
			int smallNum= Integer.parseInt(reConfation.getSmallNum());
			if(flag)
			{
				for(int k=0;k<smallNum;k++)
				{
					RecruitCheckpointConfiguration recruitCheckpointConfiguratio=new RecruitCheckpointConfiguration();

					recruitCheckpointConfiguratio.setHowManySmall((k+1)+"");//第几小关
//					reConfation.getRecruitCheckpointConfigurationList().get(0);   其他的属性 在这里面取   因为是统一 配置   就一个 默认下标0
					recruitCheckpointConfigurationService.insert(recruitCheckpointConfiguratio);//存吧
				}
			}
			else
			{
				for(int k=0;k<smallNum;k++)
				{
					RecruitCheckpointConfiguration recruitCheckpointConfiguratio=new RecruitCheckpointConfiguration();
					recruitCheckpointConfiguratio.setHowManySmall((k+1)+"");//第几小关
					recruitCheckpointConfiguratio.setId(IdWorker.getIdStr());
//					reConfation.getRecruitCheckpointConfigurationList().get(k);   其他的属性 在这里面取   因为不是统一 配置  数量和 小关数量是一样的  下表页数一样的   可以通用
					recruitCheckpointConfigurationService.insert(recruitCheckpointConfiguratio);//存吧
				}
			}

		}





	}
}
