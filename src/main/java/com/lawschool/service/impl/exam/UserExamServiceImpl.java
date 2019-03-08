package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.Answer;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.beans.system.Fraction;
import com.lawschool.beans.system.FractionRules;
import com.lawschool.dao.TestQuestionsDao;
import com.lawschool.dao.exam.UserExamAnswerDao;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.enums.Source;
import com.lawschool.form.*;
import com.lawschool.service.AnswerService;
import com.lawschool.service.CollectionService;
import com.lawschool.service.IntegralService;
import com.lawschool.service.UserService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamAnswerService;
import com.lawschool.service.exam.UserExamFormService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private UserExamAnswerService userExamAnswerService;

    @Autowired
    private UserExamAnswerDao userExamAnswerDao;

    @Autowired
    private UserExamDao userExamDao;

    @Autowired
    private AuthRelationService authService;

    @Autowired
    private TestQuestionsDao testQuestionsDao;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserExamFormService userExamFormService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private FractionService fractionService;

    /**
     * 获取试卷
     * @param examConfigId
     * @param user
     * @return
     */
    @Override
    public Result getExam(String examConfigId, User user) {
        //获取考试配置信息
        ExamConfig examConfig = examConfigService.selectById(examConfigId);
        Date currDate = new Date();
        if (currDate.before(examConfig.getStartTime()) || currDate.after(examConfig.getEndTime())) {
            return Result.error("不在考试时间范围内");
        }
        //根据考试配置ID找到考试详情ID
        String examDetailId = dao.getExamDetailId(examConfigId);
        //根据考试详情ID获取所有题目ID
        List<String> idList = dao.getQueIdList(examDetailId);
        //获取所有题目详情
        List<QuestForm> queList = examConfigService.getQueList(idList, examDetailId);
        Map<String, List<QuestForm>> queMap = new HashMap<>();
        //根据不同题型定义不同list返回，打乱顺序使用
        //单选
        List<QuestForm> sinChoicList = new ArrayList<>();
        //多选
        List<QuestForm> mulChoicList = new ArrayList<>();
        //判断
        List<QuestForm> judgeList = new ArrayList<>();
        //主观
        List<QuestForm> subjectList = new ArrayList<>();
        //其他题型
        List<QuestForm> otherList = new ArrayList<>();
        for (QuestForm que : queList) {
            if ("10004".equals(que.getQuestionType())) {
                //单选
                sinChoicList.add(que);
            } else if ("10005".equals(que.getQuestionType())) {
                //多选
                mulChoicList.add(que);
            } else if ("10006".equals(que.getQuestionType())) {
                //判断
                judgeList.add(que);
            } else if ("10007".equals(que.getQuestionType())) {
                //主观
                subjectList.add(que);
            } else {
                //其他
                otherList.add(que);
            }
        }

        //根据考试配置判断是否乱序
        if ("10031".equals(examConfig.getTopicOrderType())) {
            //题目乱序
            Collections.shuffle(sinChoicList);
            Collections.shuffle(mulChoicList);
            Collections.shuffle(judgeList);
            Collections.shuffle(subjectList);
        }
        if ("10031".equals(examConfig.getOptionOrderType())) {
            //选项乱序
            for (QuestForm q : sinChoicList) {
                Collections.shuffle(q.getAnswer());
            }
            for (QuestForm q : mulChoicList) {
                Collections.shuffle(q.getAnswer());
            }
        }
        //保存用户考试表
        UserExam userExam = new UserExam();
        userExam.setId(GetUUID.getUUIDs("UE"));
        userExam.setExamConfigId(examConfigId);
        userExam.setExamDetailId(examDetailId);
        userExam.setQueNum(Long.valueOf(examConfig.getExamCount()));
        userExam.setExamStatus("1");
        userExam.setUserId(user.getId());
        userExam.setOptTime(new Date());
        userExam.setRemainingExamTime(examConfig.getExamTime().doubleValue());
        dao.insert(userExam);

        //单选
        saveUserExamAns(examDetailId, userExam.getId(), sinChoicList);
        //多选
        saveUserExamAns(examDetailId, userExam.getId(), mulChoicList);
        //判断
        saveUserExamAns(examDetailId, userExam.getId(), judgeList);
        //主观
        saveUserExamAns(examDetailId, userExam.getId(), subjectList);

        return Result.ok().put("sinChoicList", sinChoicList).put("mulChoicList", mulChoicList)
                .put("judgeList", judgeList).put("subjectList", subjectList).put("otherList", otherList).put("examConfig", examConfig).put("userExam", userExam).put("user", user);
    }

    private void saveUserExamAns(String examDetailId, String userExamId, List<QuestForm> list) {
        //循环保存答题表
        for (int i = 0; i < list.size(); i++) {
            QuestForm que = list.get(i);
            UserExamAnswer userExamAnswer = new UserExamAnswer();
            userExamAnswer.setId(GetUUID.getUUIDs("UEA"));
            userExamAnswer.setExamDetailId(examDetailId);
            userExamAnswer.setTestQueId(que.getId());
            userExamAnswer.setUserExamId(userExamId);
            userExamAnswer.setOrderNum(i);
            userExamAnswer.setTestQueType(que.getQuestionType());
            userExamAnswer.setScore(que.getScore());
            userExamAnswer.setRightAnsId(que.getAnswerId());
            List<AnswerForm> answerlist = que.getAnswer();
            StringBuffer aio = new StringBuffer();
            for (AnswerForm answerForm : answerlist) {
                aio.append(answerForm.getId()).append(",");
            }
            if (aio.length() > 0) {
                String answerIdOrder = aio.deleteCharAt(aio.length() - 1).toString();
                userExamAnswer.setAnswerIdOrder(answerIdOrder);
            } else {
                userExamAnswer.setAnswerIdOrder("");
            }
            userExamAnswerService.insert(userExamAnswer);
        }
    }

    /**
     * 提交考试试卷
     *
     * @param userAnswerForm
     */
    @Override
    public void commitExam(UserAnswerForm userAnswerForm) {

        String userExamId = userAnswerForm.getUserExamId();
        UserExam userExam = userExamDao.selectById(userExamId);
        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());
        List<ThemeAnswerForm> themeAnswerFormsList = userAnswerForm.getAnswerForm();
        //考试剩余时间
        Double remainingExamTime = userAnswerForm.getRemainingExamTime();
        Boolean isHaveSubject = false;
        double totalScore = 0;
        float endScore = 0F;
        for (ThemeAnswerForm themeAnswerForm : themeAnswerFormsList) {
            //根据用户考试ID+问题ID查询考题详情
            UserExamAnswer userExamAnswer = userExamAnswerDao.findByuEIdAndQueId(userExamId, themeAnswerForm.getqId());
            userExamAnswer.setUserAnsId(themeAnswerForm.getAnswer());
            //根据题型阅卷，选择判断
            if ("10007".equals(userExamAnswer.getTestQueType())) {
                //主观题不判断对错只保存答案
                isHaveSubject = true;
            } else {
                //单选多选判断  判断对错并且保存答案
                String[] userAns = themeAnswerForm.getAnswer() == null ? "".split(",") : themeAnswerForm.getAnswer().split(",");
                String[] rightAns = userExamAnswer.getRightAnsId().trim() == null ? "".split(",") : userExamAnswer.getRightAnsId().trim().split(",");
                if (Arrays.equals(userAns, rightAns)) {
                    userExamAnswer.setUserScore(userExamAnswer.getScore());
                    totalScore += userExamAnswer.getScore();
                } else {
                    userExamAnswer.setUserScore(0);
                }
            }
            userExamAnswerDao.updateByIds(userExamAnswer.getUserAnsId(), userExamAnswer.getUserScore(), userExamAnswer.getId());
        }
        String isFinMark;
        if (isHaveSubject) {
            //有主观题，阅卷未完成
            isFinMark = "1";
        }else {
            //无主观题  完成阅卷
            isFinMark = "0";
            if(!"10038".equals(examConfig.getReachRewardType())&&totalScore >= examConfig.getPassPnt()) {
                endScore = calcGetInt(examConfig,totalScore,userExam.getUserId());
            }
        }
        //提交试卷设置考试状态为完成
        String examStatus = "2";
        userExamDao.updateFinMarkAndScoreById(isFinMark, totalScore, examStatus,endScore, remainingExamTime, userExamId);
    }

    /**
     * 获取考试列表
     * @param params
     * @param user
     * @return
     * @throws ParseException
     */
    @Override
    public Result getList(Map<String, Object> params, User user) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] authArr = authService.listAllIdByUser(user.getOrgId(), user.getId(), "ExamConfig");
        UserExamForm userExamForm = new UserExamForm();
        if(authArr.length>0){
            userExamForm.setAuthArr(authArr);
        }
        userExamForm.setUserId(user.getUserId());
        if(UtilValidate.isNotEmpty(params.get("status"))){
            userExamForm.setExamStatus(params.get("status").toString());
        }
        if(UtilValidate.isNotEmpty(params.get("examName"))){
            userExamForm.setExamName(params.get("examName").toString());
        }
        if(UtilValidate.isNotEmpty(params.get("startTime"))){
            userExamForm.setStartTime( sdf.parse(params.get("startTime").toString()));
        }
        if(UtilValidate.isNotEmpty(params.get("endTime"))){
            userExamForm.setEndTime(sdf.parse(params.get("endTime").toString()));
        }
        if(UtilValidate.isNotEmpty(params.get("source"))){
            userExamForm.setSource(params.get("source").toString());
        }
        if (UtilValidate.isEmpty(params.get("limit"))){
            params.put("limit","10");
        }
        String orderBy = " exam_status,IS_MUST_TEST, start_time ";
        params.put("orderBy",orderBy);
        Page<UserExamForm> userExamFormPage = userExamFormService.findPage(new Page<UserExamForm>(params),userExamForm);

        return Result.ok().put("page",userExamFormPage);
    }

    /**
     * 保存考试
     * @param userAnswerForm
     */
    @Override
    public void saveExam(UserAnswerForm userAnswerForm) {
        String userExamId = userAnswerForm.getUserExamId();
        //考试剩余时间
        Double remainingExamTime = userAnswerForm.getRemainingExamTime();
        List<ThemeAnswerForm> themeAnswerFormsList = userAnswerForm.getAnswerForm();
        for (ThemeAnswerForm themeAnswerForm : themeAnswerFormsList) {
            //根据用户考试ID+问题ID查询考题详情
            UserExamAnswer userExamAnswer = userExamAnswerDao.findByuEIdAndQueId(userExamId, themeAnswerForm.getqId());
            userExamAnswer.setUserAnsId(themeAnswerForm.getAnswer());
            userExamAnswerDao.updateByIds(userExamAnswer.getUserAnsId(), userExamAnswer.getUserScore(), userExamAnswer.getId());
        }
        //更新考试剩余时间
        userExamDao.updateRemainTimeById(remainingExamTime, userExamId);

    }

    /**
     * 继续考试
     * @param userExamId
     * @param user
     * @return
     */
    @Override
    public Result continueExam(String userExamId,User user) {

        UserExam userExam = userExamDao.selectById(userExamId);
        //获取本次考试所有题型
        List<String> queTypeList = userExamAnswerDao.getQueType(userExamId);
        //单选
        List<UserExamAnswer> userSinChoicList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10004");
        //多选
        List<UserExamAnswer> userMulChoicList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10005");
        //判断
        List<UserExamAnswer> userJudgeList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10006");
        //主观
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        //组装题目试卷
        //单选
        List<QuestForm> sinChoicList = buildExam(userSinChoicList)==null?new ArrayList<>():buildExam(userSinChoicList);
        //多选
        List<QuestForm> mulChoicList = buildExam(userMulChoicList)==null?new ArrayList<>():buildExam(userMulChoicList);
        //判断
        List<QuestForm> judgeList = buildExam(userJudgeList)==null?new ArrayList<>():buildExam(userJudgeList);;
        //主观
        List<QuestForm> subjectList = buildExam(userSubjectList)==null?new ArrayList<>():buildExam(userSubjectList);

        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());


        return Result.ok().put("sinChoicList", sinChoicList).put("mulChoicList", mulChoicList)
                .put("judgeList", judgeList).put("subjectList", subjectList).put("examConfig", examConfig).put("userExam", userExam).put("user", user);
    }

    /**
     * 组装试卷
     * @param list
     * @return
     */
    @Override
    public List<QuestForm> buildExam(List<UserExamAnswer> list) {

        if (UtilValidate.isNotEmpty(list)) {
            List<QuestForm> questFormList = new ArrayList<>(list.size());
            for (UserExamAnswer userExamAnswer : list) {
                QuestForm questForm = testQuestionsDao.findTestQuestionById(userExamAnswer.getTestQueId());
                //将答案排序字符串转为list
                if (!"10007".equals(questForm.getQuestionType())) {
                    List<String> ansIdList = new ArrayList<>();
                    if(StringUtils.isNotEmpty(userExamAnswer.getAnswerIdOrder())) {
                        ansIdList = Arrays.asList(userExamAnswer.getAnswerIdOrder().split(","));
                    }//根据答案ID查询answer详情
                    List<AnswerForm> answerFormList = answerService.findAnsById(ansIdList);
                    questForm.setAnswer(answerFormList);
                }
                if(UtilValidate.isNotEmpty(questForm.getAnswerId())){
                    List<AnswerForm> rightAnswer = answerService.findAnsById(Arrays.asList(questForm.getAnswerId().split(",")));
                    questForm.setRightAnswer(rightAnswer);
                }
                Answer answer = new Answer();
                if(userExamAnswer.getRightAnsId()!=null&&!"".equals(userExamAnswer.getRightAnsId())){
                    answer = answerService.selectById(userExamAnswer.getRightAnsId());
                }
                if (UtilValidate.isNotEmpty(answer)){
                    questForm.setRightAnsCon(answer.getQuestionContent());
                }

                questForm.setScore(userExamAnswer.getScore());
                questForm.setFirScore(userExamAnswer.getFirCheckScore());
                questForm.setSecScore(userExamAnswer.getSecCheckScore());
                questForm.setUserAnswer(userExamAnswer.getUserAnsId());
                questForm.setUserScore(userExamAnswer.getUserScore());
                questForm.setQuestionId(userExamAnswer.getId());
                questForm.setFirCheckBase(userExamAnswer.getFirCheckBase());
                questForm.setSecCheckBase(userExamAnswer.getSecCheckBase());
                questForm.setAudCheckBase(userExamAnswer.getAudCheckBase());
                questForm.setIsCollect(userExamAnswer.getIsCollect());
                questFormList.add(questForm);
            }
            return questFormList;
        } else {
            return null;
        }

    }

    /**
     *
     * @param ids
     * @return
     */
    @Override
    public List<UserExamForm> getListByIds(List<String> ids) {
        return dao.getListByIds(ids);
    }

    @Override
    public Result viewExam(String userExamId, User user) {
        UserExam userExam = userExamDao.selectById(userExamId);
        //获取本次考试所有题型
        List<String> queTypeList = userExamAnswerDao.getQueType(userExamId);
        //单选
        List<UserExamAnswer> userSinChoicList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10004");
        //多选
        List<UserExamAnswer> userMulChoicList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10005");
        //判断
        List<UserExamAnswer> userJudgeList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10006");
        //主观
        List<UserExamAnswer> userSubjectList = userExamAnswerDao.findByuEIdAndQueType(userExamId, "10007");
        //组装题目试卷
        //单选
        List<QuestForm> sinChoicList = buildExam(userSinChoicList)==null?new ArrayList<>():buildExam(userSinChoicList);
        //多选
        List<QuestForm> mulChoicList = buildExam(userMulChoicList)==null?new ArrayList<>():buildExam(userMulChoicList);
        //判断
        List<QuestForm> judgeList = buildExam(userJudgeList)==null?new ArrayList<>():buildExam(userJudgeList);;
        //主观
        List<QuestForm> subjectList = buildExam(userSubjectList)==null?new ArrayList<>():buildExam(userSubjectList);

        ExamConfig examConfig = examConfigService.selectById(userExam.getExamConfigId());


        return Result.ok().put("sinChoicList", sinChoicList).put("mulChoicList", mulChoicList)
                .put("judgeList", judgeList).put("subjectList", subjectList).put("examConfig", examConfig).put("userExam", userExam).put("user", user);

    }

    @Override
    public boolean doCollect(String id, String recordId, Integer type) {
        boolean result = collectionService.doCollection("20", id, type==1 ? false : true);

        if(result){
            dao.updateCollect(recordId, type);
        }

        return true;
    }

    @Override
    public float calcGetInt(ExamConfig examConfig,double totalScore,String userId){
        //计算获得奖励
        //获取当前考生当前时间范围内积分并比较是否超出范围
        //未超出 奖励分数
        Integral integral = new Integral();
        Fraction fraction = new Fraction();
        Result result ;
        float endScore=0F;
        User user = userService.selectUserByUserId(userId);
        //判断奖励类型
        //获取奖励规则
        if ("10039".equals(examConfig.getReachRewardType())){
            //学分
            result = fractionService.getFractionByType("0", Source.EXAM);
            fraction = (Fraction) result.get("fraction");
            if(fraction.getQueNum()<=examConfig.getExamCount()&& totalScore>=(examConfig.getPassPnt()*fraction.getMinDemand())){
                //本次奖励分数
                endScore =Float.parseFloat( examConfig.getReachReward());
                endScore = svaeIntegral(DateTimeUtils.getCurrentQuarterStartTime(), DateTimeUtils.getCurrentQuarterEndTime(),
                        examConfig.getReachRewardType(),user,fraction.getDailyLimit(),endScore,"0");
            }
        }else{
            result = fractionService.getFractionByType("1", Source.EXAM);
            fraction = (Fraction) result.get("fraction");
            List<FractionRules> fractionRulesList = fraction.getFractionRulesList();
            if (fraction.getQueNum()<=examConfig.getExamCount()) {
                for (FractionRules fractionRules : fractionRulesList) {
                    if (totalScore >= examConfig.getExamScore() * fractionRules.getRightRateMin() && totalScore < examConfig.getExamScore() * fractionRules.getRightRateMax()) {
                        endScore = fractionRules.getIntervalScore();
                        endScore = svaeIntegral(DateTimeUtils.getCurrentMonthStartTime(), DateTimeUtils.getCurrentMonthEndTime(),
                                examConfig.getReachRewardType(),user,fraction.getDailyLimit(),endScore,"1");
                    }
                }
            }
        }
        return endScore;
    }

    private float svaeIntegral(Date startTime,Date endTime,String reachRewardType,
                              User user,float limit,float endScore, String type ){
        //获取本季度已经获得分数
        Integral integral = new Integral();
        float getScoreCount = dao.getSubScoreByTimes(startTime, endTime,reachRewardType ,user.getId());
        if (getScoreCount< limit) {
            //获得的总分数小于限制分数
            if ( (getScoreCount + endScore )> limit) {
                endScore = limit - getScoreCount;
            }
            integral.setType(type);
            integral.setSrc("exam");
            integralService.addIntegralRecord(integral,user);
        }else {
            endScore = 0f;
        }
        return endScore;
    }

}
