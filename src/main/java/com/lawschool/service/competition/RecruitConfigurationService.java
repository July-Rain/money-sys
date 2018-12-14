package com.lawschool.service.competition;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Descriptin  闯关配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface RecruitConfigurationService  extends IService<RecruitConfiguration> {

    public List<RecruitConfiguration> findAll();



    public RecruitConfiguration info(String id);

    public void save(List<RecruitConfiguration> list);

    public void deleteAll();

    PageUtils queryPage(Map<String, Object> params);

    public List<RecruitCheckpointConfiguration> getSonList(String id);
    public List<CommonForm> findAllTopic();
}
