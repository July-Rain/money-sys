package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.Answer;
import com.lawschool.form.AnswerForm;

import java.util.List;

public interface AnswerDao extends AbstractDao<Answer> {
    //int insert(Answer record);

    //int insertSelective(Answer record);

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    List<AnswerForm> findByQuestionIds(List<String> list);

    void deleteByQuestionId(String questionId);
}