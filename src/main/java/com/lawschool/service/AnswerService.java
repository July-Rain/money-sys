package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.Answer;
import com.lawschool.form.AnswerForm;

import java.util.List;

/**
 * @Title: AnswerService
 * @ProjectName law_school
 * @Description: TODO
 * @author zhujunwen
 * @date 2018-12-418:21
 */

public interface AnswerService extends AbstractService<Answer> {
    List<Answer>  getAnswerByQid(String id);

    /**
     * 根据题目的多个id查询题目
     * @param list
     * @return
     */
    List<AnswerForm> findByQuestionIds(List<String> list);

    void deleteByQuestionId(String questionId);

    void deleteByQuestionIds(List<String> questionIds);
}
