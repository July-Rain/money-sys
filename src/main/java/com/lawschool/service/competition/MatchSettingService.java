package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.BattleTopicSetting;
import com.lawschool.beans.competition.CompetitionOnline;
import com.lawschool.beans.competition.MatchSetting;

import java.util.List;


/**
 * 
 * @Descriptin  擂台配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface MatchSettingService extends IService<MatchSetting> {
    public List<MatchSetting>  list();
    public  void deleteAll();
    public void save(MatchSetting matchSetting);
    public List<BattleTopicSetting> getSonList(String id);
    public MatchSetting findAll();
    public List<TestQuestions> getQuest();
    public MatchSetting findAll2();
    public void updateWin(MatchSetting matchSetting,String uid);
    public void wincountjia(MatchSetting matchSetting,String uid);
    public void saveQuestion(TestQuestions testQuestions,String myanswer,String userid);

}
