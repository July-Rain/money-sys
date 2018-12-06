package com.lawschool.service;


import com.lawschool.base.AbstractService;
import com.lawschool.base.Page;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  试题service
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface TestQuestionService extends AbstractService<TestQuestions> {
    /**
     * 启用禁用
     */
    void updateStatus(String id, String isEnble);

    /**
     * 批量导入试题
     */
    void importTestQuestions(List<TestQuestions> TestQuestions);

}
