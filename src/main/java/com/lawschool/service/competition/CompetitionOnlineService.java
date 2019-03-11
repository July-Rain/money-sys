package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.util.PageUtils;
import org.springframework.web.bind.annotation.RequestParam;

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
    public CompetitionOnline findAll2();
    public List<CompetitionOnline>  list();

    public CompetitionOnline info(String id);


    public void save(CompetitionOnline competitionOnline);

    public  void deleteComOnline(String id);

    public void  updateComOnline();

    PageUtils queryPage(Map<String, Object> params);

    public List<BattleTopicSetting> getSonList(String id);

    public  void deleteAll();

    public List<TestQuestions> getQuest();
    public void saveQuestion(TestQuestions testQuestions,String myanswer,String userid,String Source);
    public String recordScore(String battlePlatformId,String win,String score,String type,String uid);

}
