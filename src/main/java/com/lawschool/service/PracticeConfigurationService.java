package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.PracticeConfiguration;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
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
public interface PracticeConfigurationService extends AbstractService {
    /**
     * 显示所有练习卷配置
     */
    public PageUtils listAll();

    /**
     * 保存练习卷配置
     */
    public void save(PracticeConfiguration practiceConfiguration);

    /**
     * g根据知识ID查询题目
     */
    public List<TestQuestions> selectQuestionsByKnowledgeId(String id);

    /**
     *随机抽题
     */
    public List queryRandom(Map map);

    /**
     * 生成试题
     * @param list
     */
    public Result createPracticePaper(List<PracticeConfiguration> list, User user);

    /**
     * 生成练习卷名称
     * @param prefix
     */
    public Result createPaperName(String prefix);

}
