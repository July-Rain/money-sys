package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Descriptin  在线比武配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CompetitionOnlineService extends IService<CompetitionOnline> {
    public CompetitionOnline findAll();
    public List<CompetitionOnline>  list();

    public CompetitionOnline info(String id);


    public void save(CompetitionOnline competitionOnline);

    public  void deleteComOnline(String id);

    public void  updateComOnline();

    PageUtils queryPage(Map<String, Object> params);

    public List<BattleTopicSetting> getSonList(String id);

    public  void deleteAll();
}
