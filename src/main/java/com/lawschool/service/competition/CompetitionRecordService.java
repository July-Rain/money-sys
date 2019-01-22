package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.CompetitionRecord;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Descriptin  竞赛记录配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface CompetitionRecordService extends IService<CompetitionRecord> {


    public List<CompetitionRecord> findAll();

    public CompetitionRecord info(String id);

    public void save();

    public void deleteId(String id);

    public void updatedata();

    public void recordScore(String foreignKeyId,String nowbig,String nowlit,User u,String sorce);
    public void updateRecordStatus();

    public int chuangguanCountByUser(String uid);
    PageUtils queryPage(Map<String, Object> params,String uid);

    public int chuangguanCountBydept(String deptcode);
    public int chuangguanSorceBydept(String deptcode);

    List<CompetitionRecord> chuangGuanRanking();
    CompetitionRecord chuangGuanRankingByUser();
}
