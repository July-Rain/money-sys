package com.lawschool.service;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.CompetitionRecord;

import java.util.List;

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

}
