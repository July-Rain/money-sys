package com.lawschool.service;

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
public interface PracticeConfigurationService {
    /**
     * 显示所有练习卷配置
     */
    public List<PracticeConfiguration> listAll();

    /**
     * 保存练习卷配置
     */
    public void save(PracticeConfiguration practiceConfiguration);

    /**
     * g根据知识ID查询题目
     */
    public List<TestQuestions> selectByKnowledgeId(Map map);
}
