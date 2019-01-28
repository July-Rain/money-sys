package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Answer;
import com.lawschool.beans.TestQuestions;
import com.lawschool.dao.TestQuestionsDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TestQuestionServiceImpl extends AbstractServiceImpl<TestQuestionsDao,TestQuestions> implements TestQuestionService {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TestQuestionsDao testQuestionsDao;

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

        result = this.handleAnswers(result, answerList);

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

    /**
     * 处理题目和答案
     * @param questForms
     * @param answerList
     * @return
     */
    @Override
    public List<QuestForm> handleAnswers(List<QuestForm> questForms, List<AnswerForm> answerList){

        for(QuestForm qf : questForms){
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

        return questForms;
    }

    /**
     * 根据各种条件统计满足条件的题目数量
     *
     * @param params
     * @return
     */
    public Integer getNumByConditions(Map<String, String> params){

        return dao.getNumByConditions(params);
    }

    /**
     * 查询满足条件的题目IDS（带分页效果）
     * 请勿随便改动
     * @param params
     *      params包含以下参数：
     *          type: 题目类型（字典表的code,文字 OR 视频），多选
     *          themeId: 题目主题（law_sys_topic的ID），多选
     *          difficulty：题目难度（字典表的code），多选
     *          classify：题目类型（字典表code，多选 OR 单选...），多选
     * @return
     */
    @Override
    public List<String> selectIdsForPage(Map<String, Object> params){

        return dao.selectIdsForPage(params);
    }

    /**
     * 根据主题分组统计题目数量
     * @return
     */
    @Override
    public Map<String, String> countByThemeId(){
        Map<String, String> result = new HashMap<String, String>();

        List<CommonForm> formList = dao.countByThemeId();
        for(CommonForm form : formList){

            result.put(form.getKey(), form.getValue());
        }

        return result;
    }

    /**
     * 更新答案信息
     * @param id
     * @param answerId
     * @return
     */
    public boolean updateAnswerId(String id, String answerId){

        return dao.updateAnswerId(id, answerId);
    }

    public boolean mySave(TestQuestions entity){
        List<Answer> answerList = entity.getAnswerList();
        if(org.apache.commons.collections.CollectionUtils.isEmpty(answerList)){
            return false;
        } else {
            entity.setAnswerChoiceNumber(answerList.size()+"");
        }

        this.save(entity);

        // 先删除问题下的答案
        answerService.deleteByQuestionId(entity.getId());

        String answerId = "";// 正确答案ID
        // 重新保存答案
        for (Answer answer : answerList){
            answer.setQuestionId(entity.getId());
            answer.setId(null);
            answerService.save(answer);
            if(answer.getIsAnswer() == 1){
                answerId += answer.getId() + ",";
            }
        }
        if(StringUtils.isNotBlank(answerId)){
            // 更新实体信息
            this.updateAnswerId(entity.getId(), answerId.substring(0, answerId.length()-1));
        } else {
            return false;
        }
        return true;
    }
}
