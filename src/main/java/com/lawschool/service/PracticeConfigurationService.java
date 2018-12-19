package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.form.DiyPracPaperForm;
import com.lawschool.form.QuestForm;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.util.List;
import java.util.Map;

/**
 * @author liuhuan
 * @Descriptin
 * @version
 * @Time 2018/12/04
 */
public interface PracticeConfigurationService extends IService<PracticeConfiguration> {

    List<PracticeConfiguration> list();
    /**
     * 显示所有练习卷配置
     */
    PageUtils listAll();

    /**
     * 保存练习卷配置
     */
    void save(PracticeConfiguration practiceConfiguration);

    /**
     * g根据知识ID查询题目
     */
    List<TestQuestions> selectQuestionsByKnowledgeId(String id);

    /**
     *随机抽题
     */
    List queryRandom(Map map);

    /**
     * 生成试题
     * @param configId//传入练习卷主表配置ID
     */
    List<QuestForm> createPracticePaper(String configId);

    /**
     * 生成练习卷名称
     * @param prefix
     */
     Result createPaperName(String prefix);

    /**
     * 信息
     */
     PracticeConfiguration selectById(String id);

     void insertDiy(PracticeConfiguration practiceConfiguration);

}
