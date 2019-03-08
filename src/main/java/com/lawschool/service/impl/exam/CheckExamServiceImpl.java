package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.CheckExam;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.dao.exam.CheckExamDao;
import com.lawschool.dao.exam.UserExamAnswerDao;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.CalcScoreForm;
import com.lawschool.form.CheckExamForm;
import com.lawschool.form.QuestForm;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserService;
import com.lawschool.service.exam.CheckExamService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;

    @Autowired
    private IntegralService integralService;

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

        List<QuestForm> subjectList = userExamService.buildExam(userSubjectList)==null?new ArrayList<>():userExamService.buildExam(userSubjectList);

        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());


        return Result.ok().put("subjectList", subjectList).put("examConfig", examConfig).put("userExam", userExam);
    }

    @Override
    public Result commitCheckExam(UserAnswerForm userAnswerForm) {
        String userExamId = userAnswerForm.getUserExamId();
        UserExam userExam = userExamDao.selectById(userExamId);
        ExamConfig examConfig =examConfigService.selectById(userExam.getExamConfigId());
        List<CheckExamForm> checkExamForms= userAnswerForm.getCheckExamForm();
        CalcScoreForm scoreForm = new CalcScoreForm();
        if(examConfig.getPaperPerSetNum()==2) {
            scoreForm = updateScoreByTwo(checkExamForms, userAnswerForm, examConfig.getCheckScoreDifference());
        }else {
            scoreForm  = updateScoreByOne(checkExamForms, userAnswerForm);
        }
        String isFinsh = scoreForm.getIsFinMark();
        float countScore = 0 ;
        float endScore = 0;
        if("0".equals(isFinsh)){
            //阅卷完成计算分数
            float firScore = userExamAnswerDao.getScoreByUserExamId(userExamId);
            countScore = firScore + scoreForm.getScore();

            if(!"10038".equals(examConfig.getReachRewardType())){
                endScore = userExamService.calcGetInt( examConfig, countScore , userExam.getUserId());
            }
        }
        userExam.setIsFinMark(isFinsh);
        userExam.setScore(countScore);
        userExam.setRewards(endScore);
        CheckExam checkExam = dao.selectById(userAnswerForm.getCheckExamId());
        //完成阅卷
        checkExam.setCheckStatus("2");
        dao.updateById(checkExam);
        userExamDao.updateById(userExam);

        return Result.ok();
    }

    @Override
    public Result saveCheckExam(UserAnswerForm userAnswerForm) {
//        List<CheckExamForm> checkExamForms= userAnswerForm.getCheckExamForm();
//        updateScore(checkExamForms,userAnswerForm);
//        userExam.setCheckStatus("1");
//        CheckExam checkExam = dao.selectById(userAnswerForm.getCheckExamId());
//        checkExam.setCheckStatus("1");
//        dao.updateById(checkExam);
//        userExamDao.updateById(userExam);

        return Result.ok();
    }

    private CalcScoreForm updateScoreByTwo(List<CheckExamForm> checkExamForms, UserAnswerForm userAnswerForm, double diffScore){
        CalcScoreForm scoreForm = new CalcScoreForm();
        String isFinsh = "1";
        float score = 0;
        for(CheckExamForm checkExamForm : checkExamForms){
            UserExamAnswer userExamAnswer = userExamAnswerDao.selectById(checkExamForm.getQueId());
            if(UtilValidate.isEmpty(userExamAnswer.getFirCheckUserId())){
                userExamAnswer.setFirCheckScore(checkExamForm.getScore());
                userExamAnswer.setFirCheckUserId(userAnswerForm.getCheckExamUserId());
                userExamAnswer.setFirCheckBase(checkExamForm.getCheckBase());
            }else {
                if("1".equals(isFinsh)){
                    isFinsh = "0";
                }
                userExamAnswer.setSecCheckScore(checkExamForm.getScore());
                userExamAnswer.setSecCheckUserId(userAnswerForm.getCheckExamUserId());
                userExamAnswer.setSecCheckBase(checkExamForm.getCheckBase());
                if(Math.abs(checkExamForm.getScore()-userExamAnswer.getFirCheckScore())<=diffScore){
                    userExamAnswer.setUserScore((checkExamForm.getScore()+userExamAnswer.getFirCheckScore())/2);
                    userExamAnswer.setCheckStatus("0");
                    score += userExamAnswer.getUserScore();
                }else {
                    isFinsh = "2";
                    userExamAnswer.setCheckStatus("2");
                }
            }
            userExamAnswerDao.updateById(userExamAnswer);
        }
        scoreForm.setIsFinMark(isFinsh);
        scoreForm.setScore(score);
        return scoreForm;
    }

    private CalcScoreForm  updateScoreByOne(List<CheckExamForm> checkExamForms,UserAnswerForm userAnswerForm){
        CalcScoreForm scoreForm = new CalcScoreForm();
        float score = 0;
        String isFinsh = "1";
        for(CheckExamForm checkExamForm : checkExamForms){
            UserExamAnswer userExamAnswer = userExamAnswerDao.selectById(checkExamForm.getQueId());
            if("".equals(userExamAnswer.getFirCheckScore())||(userExamAnswer.getFirCheckScore()==0.0&&("".equals(userExamAnswer.getFirCheckUserId())||userExamAnswer.getFirCheckUserId()==null))){
                userExamAnswer.setFirCheckScore(checkExamForm.getScore());
                userExamAnswer.setFirCheckUserId(userAnswerForm.getCheckExamUserId());
                userExamAnswer.setUserScore(checkExamForm.getScore());
                score += userExamAnswer.getUserScore();
                isFinsh ="0";
            }
            userExamAnswerDao.updateById(userExamAnswer);
        }
        scoreForm.setIsFinMark(isFinsh);
        scoreForm.setScore(score);
        return scoreForm;
    }

    @Override
    public Result continueCheckExam(String userExamId, String checkExamUserId) {
        UserExam userExam = userExamDao.selectById(userExamId);
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        return Result.ok().put("subjectList",userSubjectList);
    }

    @Override
    public Result auditCheckExam(String userExamId) {
        UserExam userExam = userExamDao.selectById(userExamId);
        //主观
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        if(userSubjectList==null){
            userSubjectList = new ArrayList<>();
        }

        List<QuestForm> subjectList = userExamService.buildExam(userSubjectList)==null?new ArrayList<>():userExamService.buildExam(userSubjectList);

        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());


        return Result.ok().put("subjectList", subjectList).put("examConfig", examConfig).put("userExam", userExam);
    }

    @Override
    public Result commitAuditExam(UserAnswerForm userAnswerForm) {
        String userExamId = userAnswerForm.getUserExamId();
        UserExam userExam = userExamDao.selectById(userExamId);
        ExamConfig examConfig =examConfigService.selectById(userExam.getExamConfigId());
        List<CheckExamForm> checkExamForms= userAnswerForm.getCheckExamForm();
        float countScore = userExamAnswerDao.getScoreExAudit(userExamId);
        float endScore = 0 ;
        for(CheckExamForm checkExamForm : checkExamForms){
            UserExamAnswer userExamAnswer = userExamAnswerDao.selectById(checkExamForm.getQueId());
            userExamAnswer.setAudCheckScore(checkExamForm.getScore());
            userExamAnswer.setAudCheckUserId(userAnswerForm.getCheckExamUserId());
            userExamAnswer.setUserScore(checkExamForm.getScore());
            userExamAnswer.setAudCheckBase(checkExamForm.getCheckBase());
            countScore +=checkExamForm.getScore();
            userExamAnswerDao.updateById(userExamAnswer);
        }
        if(!"10038".equals(examConfig.getReachRewardType())){
            endScore = userExamService.calcGetInt( examConfig, countScore , userExam.getUserId());
        }
        userExam.setIsFinMark("0");
        userExam.setScore(countScore);
        userExam.setRewards(endScore);
        userExamDao.updateById(userExam);

        return Result.ok();
    }
}
