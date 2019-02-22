package com.lawschool.service.exam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.form.CheckSetForm;
import com.lawschool.form.QuestForm;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.apache.ibatis.annotations.Param;

/***
 *
 * @Title:ExamConfigService.java
 * @Description: 考试配置接口
 * @author: 王帅奇
 * @date 2018年11月29日
 */
public interface ExamConfigService extends AbstractService<ExamConfig> {

    List<QuestForm> preview(ExamConfig examConfig) throws Exception;

    void generate(ExamConfig examConfig) throws Exception;

    List<QuestForm> getList(List<String> idList);

    List<QuestForm> getQueList(List<String> idList,String examDetailId);

    void checkset(CheckSetForm checkSetForm);

    ExamConfig findByCheckPassword( String checkPassword,
                                    String checkUserType);

    Result getExamList(Map<String, Object> params)  throws ParseException;

    CheckSetForm getCheckSetting(String id);

    ExamConfig getExamDetail( String id );
    Result deleteExamConfig(String id) ;
}
