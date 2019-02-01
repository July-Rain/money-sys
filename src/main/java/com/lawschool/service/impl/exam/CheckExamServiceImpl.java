package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.exam.CheckExam;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.dao.exam.CheckExamDao;
import com.lawschool.dao.exam.UserExamAnswerDao;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.CheckExamForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.service.exam.CheckExamService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CheckExamServiceImpl
 * Description: TODO
 * date: 2019/1/2915:23
 *
 * @author 王帅奇
 */
@Service
public class CheckExamServiceImpl extends AbstractServiceImpl<CheckExamDao,CheckExam> implements CheckExamService {

    @Autowired
    private UserExamDao userExamDao;

    @Autowired
    private UserExamAnswerDao userExamAnswerDao;

    @Autowired
    private UserExamService userExamService;

    @Autowired
    private ExamConfigService examConfigService;

    @Override
    public List<String> getUserExamIdBuCheckUserId(String checkUserId) {
        return dao.getUserExamIdByCheckUserId(checkUserId);
    }

    @Override
    public Result startCheckExam(String userExamId) {
        UserExam userExam = userExamDao.selectById(userExamId);
        //主观
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        if(userSubjectList==null){
            userSubjectList = new ArrayList<>();
        }

    //    List<QuestForm> subjectList = userExamService.buildExam(userSubjectList)==null?new ArrayList<>():userExamService.buildExam(userSubjectList);

        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());


        return Result.ok().put("subjectList", userSubjectList).put("examConfig", examConfig).put("userExam", userExam);
    }

    @Override
    public Result commitCheckExam(UserAnswerForm userAnswerForm) {

        List<CheckExamForm> checkExamForms= userAnswerForm.getCheckExamForm();
        UserExam userExam = updateScore(checkExamForms,userAnswerForm);
        userExam.setCheckStatus("2");
        CheckExam checkExam = dao.selectById(userAnswerForm.getCheckExamId());
        checkExam.setCheckStatus("1");
        dao.updateById(checkExam);
        userExamDao.update(userExam);

        return Result.ok();
    }

    @Override
    public Result saveCheckExam(UserAnswerForm userAnswerForm) {
        List<CheckExamForm> checkExamForms= userAnswerForm.getCheckExamForm();
        UserExam userExam = updateScore(checkExamForms,userAnswerForm);
        userExam.setCheckStatus("1");
        CheckExam checkExam = dao.selectById(userAnswerForm.getCheckExamId());
        checkExam.setCheckStatus("1");
        dao.updateById(checkExam);
        userExamDao.updateById(userExam);

        return Result.ok();
    }

    private UserExam updateScore(List<CheckExamForm> checkExamForms,UserAnswerForm userAnswerForm){
        UserExam userExam = new UserExam();
        for(CheckExamForm checkExamForm : checkExamForms){
            UserExamAnswer userExamAnswer = userExamAnswerDao.selectById(checkExamForm.getQueId());
            userExam = userExamDao.selectById(userExamAnswer.getUserExamId());
            if("".equals(userExamAnswer.getFirCheckScore())||(userExamAnswer.getFirCheckScore()==0.0&&("".equals(userExamAnswer.getFirCheckUserId())||userExamAnswer.getFirCheckUserId()==null))){
                userExamAnswer.setFirCheckScore(checkExamForm.getScore());
                userExamAnswer.setFirCheckUserId(userAnswerForm.getCheckExamUserId());
            }else {
                userExamAnswer.setSecCheckScore(checkExamForm.getScore());
                userExamAnswer.setSecCheckUserId(userAnswerForm.getCheckExamUserId());
            }
            userExamAnswerDao.updateAllColumnById(userExamAnswer);
        }
        return userExam;
    }

    @Override
    public Result continueCheckExam(String userExamId, String checkExamUserId) {
        UserExam userExam = userExamDao.selectById(userExamId);
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        return Result.ok().put("subjectList",userSubjectList);
    }
}
