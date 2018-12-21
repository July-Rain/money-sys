package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Answer;
import com.lawschool.dao.AnswerDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: AnswerServiceImpl
 * @ProjectName law_school
 * @Description: TODO
 * @author zjw
 * @date 2018-12-418:22
 */
@Service
public class AnswerServiceImpl extends AbstractServiceImpl<AnswerDao, Answer> implements AnswerService {

    //获取题目的答案
    @Override
    public List<Answer> getAnswerByQid(String id) {
        List<Answer> answers = dao.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", id));
        return answers;
    }

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    public List<AnswerForm> findByQuestionIds(List<String> list){
        return dao.findByQuestionIds(list);
    }

    public void deleteByQuestionId(String questionId){
        dao.deleteByQuestionId(questionId);
    }

    public void deleteByQuestionIds(List<String> questionIds){
        dao.deleteByQuestionIds(questionIds);
    }
}
