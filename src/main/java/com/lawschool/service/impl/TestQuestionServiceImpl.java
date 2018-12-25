package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.constants.StatusConstant;
import com.lawschool.dao.TestQuestionsDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl extends AbstractServiceImpl<TestQuestionsDao,TestQuestions> implements TestQuestionService {

    @Autowired
    private AnswerService answerService;

    @Autowired
    TestQuestionsDao testQuestionsDao;
    /**
     * 启用禁用
     * @param id
     * @param isEnble
     */
    public void updateStatus(String id, String isEnble) {
        dao.updateStatus(id, isEnble);
    }

    /**
     * 批量导入试题
     * @param testQuestionsList
     */
    public void importTestQuestions(List<TestQuestions> testQuestionsList) {
        if (!CollectionUtils.isEmpty(testQuestionsList)) {
            for (TestQuestions testQuestion : testQuestionsList) {
                dao.insert(testQuestion);
            }
        }
    }

    /**
     * 查询某类型的试题id
     * @param param
     */
    public List<String> findIdBySpecialKnowledgeId(Map<String, Object> param) {
        return dao.findIdBySpecialKnowledgeId(param);
    }

    /**
     * 查询某类型的试题id
     * @param param
     */
    public List<TestQuestions> findBySpecialKnowledgeId(Map<String, Object> param) {
        return dao.findBySpecialKnowledgeId(param);
    }

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    public List<QuestForm> findByIds(List<String> list){
        return dao.findByIds(list);
    }


    @Override
    public TestQuestions findByEntity(TestQuestions entity) {
        return dao.findByEntity(entity);
    }

    @Override
    public List<CommonForm> selectByTopicAndNum(String topic, Integer num){
        List<CommonForm> list = dao.selectByTopicAndNum(topic, num);

        return list;
    }

    @Override
    public List<QuestForm> getQuestions(List<String> ids){
        List<QuestForm> result = new ArrayList<>();

        result = this.findByIds(ids);

        List<AnswerForm> answerList = answerService.findByQuestionIds(ids);

        for(QuestForm qf : result){
            String qid = qf.getId();
            List<AnswerForm> tempList = new ArrayList<>();

            for(AnswerForm af : answerList){
                String aqid = af.getQuestionId();
                if(qid.equals(aqid)){
                    tempList.add(af);
                }
            }

            qf.setAnswer(tempList);
            answerList.removeAll(tempList);
        }

        return result;
    }


    /**
     * 根据专项知识ID和题目类型查询指定数量的题目
     * @param param
     * @return
     */
    public List<String> findByNum(Map<String, Object> param){
        return dao.findByNum(param);
    }

    /**
     * 根据ID查询单个题目
     * @param id
     * @return
     */
    @Override
    public QuestForm findTestQuestionById(String id) {
        QuestForm question =  testQuestionsDao.findTestQuestionById(id);
        return question;
    }
}
