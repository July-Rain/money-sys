package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.TestQuestions;

import java.util.List;
import java.util.Map;

/**
 * @author liuhuan
 * @Descriptin
 * @version
 * @Time 2018/12/04
 */
public interface PracticeConfigurationDao extends BaseMapper<PracticeConfiguration> {

    public List<TestQuestions> selectQuestionsByKnowledgeId(String id);

    //随机查询
    public List<String> queryRandom(Map map);

    //根据传入ID数组查询
//    public List<TestQuestions> selectByIds(Map map);

    //插入实体
    void insertDiy(PracticeConfiguration practiceConfiguration);
}
