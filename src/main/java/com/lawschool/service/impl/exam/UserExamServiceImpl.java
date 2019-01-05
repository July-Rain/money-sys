package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.QuestForm;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UserExamServiceImpl
 * Description: TODO
 * date: 2018/12/2910:20
 *
 * @author 王帅奇
 */
@Service
public class UserExamServiceImpl extends AbstractServiceImpl<UserExamDao, UserExam> implements UserExamService {

    @Autowired
    private ExamConfigService examConfigService;

    @Override
    public Result getExam(String examConfigId) {
        //根据考试配置ID找到考试详情ID
        String examDetailId = dao.getExamDetailId(examConfigId);
        //根据考试详情ID获取所有题目ID
        List<String> idList = dao.getQueIdList(examDetailId);
        //获取所有题目详情
        List<QuestForm> queList = examConfigService.getList(idList);
        //获取考试配置信息
        ExamConfig examConfig = examConfigService.selectById(examConfigId);

        return Result.ok().put("queList", queList).put("examConfig", examConfig);
    }
}
