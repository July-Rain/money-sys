package com.lawschool.service;

import com.lawschool.beans.Answer;

import java.util.List;

/**
 * @Title: AnswerService
 * @ProjectName law_school
 * @Description: TODO
 * @author zhujunwen
 * @date 2018-12-418:21
 */

public interface AnswerService {
    List<Answer>  getAnswerByQid(String id);
}
