package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.PracticeConfigurationMapper;
import com.lawschool.service.PracticeConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author liuhuan
 * @Descriptin 练习卷配置
 * @version 1.0
 * @Time 2018/12/04
 */
@Service
public class PracticeConfigurationServiceImpl implements PracticeConfigurationService {

    @Autowired
    PracticeConfigurationMapper practiceConfigurationMapper;
    /**
     * 展示全部练习配置
     */
    @Override
    public List<PracticeConfiguration> listAll() {
        return practiceConfigurationMapper.selectList(new EntityWrapper<PracticeConfiguration>());
    }

    @Override
    public void save(PracticeConfiguration practiceConfiguration) {
        /**
         * 保存练习配置
         */
        practiceConfigurationMapper.insert(practiceConfiguration);
    }

    /**
     * 根据知识点查询题目
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestQuestions> selectByKnowledgeId(Map map) {
        return practiceConfigurationMapper.selectQuestionsByKnowledgeId(map);
    }
}
