package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Answer;
import com.lawschool.beans.UserQuestRecord;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamAnswerService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public Result getExam(String examConfigId) {
        //根据考试配置ID找到考试详情ID
        String examDetailId = dao.getExamDetailId(examConfigId);
        //根据考试详情ID获取所有题目ID
        List<String> idList = dao.getQueIdList(examDetailId);
        //获取所有题目详情
        List<QuestForm> queList = examConfigService.getList(idList);
        Map<String,List<QuestForm>> queMap = new HashMap<>();
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
        for (QuestForm que : queList){
            if(que.getQuestionType().equals("10004")){
                 //单选
                sinChoicList.add(que);
            }else if(que.getQuestionType().equals("10005")){
                //多选
                mulChoicList.add(que);
            }else if(que.getQuestionType().equals("10006")){
                //判断
                judgeList.add(que);
            }else if(que.getQuestionType().equals("10007")){
                //主观
                subjectList.add(que);
            }else{
                //其他
                otherList.add(que);
            }
        }
        /*String key = "";
        List<QuestForm> diffQueTypeList = new ArrayList<QuestForm>();
        for(QuestForm que : queList){
            if(!que.getQuestionType().equals(key)) {
                if(UtilValidate.isNotEmpty(diffQueTypeList)) {
                    Collections.shuffle(diffQueTypeList);
                    queMap.put(key, diffQueTypeList);
                }
                key = que.getQuestionType();
                diffQueTypeList = new ArrayList<QuestForm>();
                diffQueTypeList.add(que);
            }else {
                diffQueTypeList.add(que);
            }
        }
        queMap.put(key, diffQueTypeList);*/
        //获取考试配置信息
        ExamConfig examConfig = examConfigService.selectById(examConfigId);
        //根据考试配置判断是否乱序
        if("10031".equals(examConfig.getTopicOrderType())){
            //题目乱序
            Collections.shuffle(sinChoicList);
            Collections.shuffle(mulChoicList);
            Collections.shuffle(judgeList);
            Collections.shuffle(subjectList);
        }
        if("10031".equals(examConfig.getOptionOrderType())){
            //选项乱序
            for(QuestForm q : sinChoicList){
                Collections.shuffle(q.getAnswer());
            }
            for(QuestForm q : mulChoicList){
                Collections.shuffle(q.getAnswer());
            }
        }
        //保存用户考试表
        UserExam userExam = new UserExam();
        userExam.setId(GetUUID.getUUIDs("UE"));
        userExam.setExamConfigId(examConfigId);
        userExam.setExamDetailId(examDetailId);
        userExam.setExamStatus("0");
        userExam.setOptTime(new Date());
        dao.insert(userExam);

        //单选
        saveUserExamAns(examDetailId,userExam.getId(),sinChoicList);
        //多选
        saveUserExamAns(examDetailId,userExam.getId(),mulChoicList);
        //判断
        saveUserExamAns(examDetailId,userExam.getId(),judgeList);
        //主观
        saveUserExamAns(examDetailId,userExam.getId(),subjectList);

        return Result.ok().put("sinChoicList", sinChoicList).put("mulChoicList",mulChoicList)
                .put("judgeList",judgeList).put("subjectList",subjectList).put("otherList",otherList).put("examConfig", examConfig);
    }

    private void saveUserExamAns(String examDetailId,String userExamId,List<QuestForm> list){
        //循环保存答题表
        for (int i=0;i<list.size();i++){
            QuestForm que= list.get(i);
            UserExamAnswer userExamAnswer = new UserExamAnswer();
            userExamAnswer.setId(GetUUID.getUUIDs("UEA"));
            userExamAnswer.setExamDetailId(examDetailId);
            userExamAnswer.setUserExamId(userExamId);
            userExamAnswer.setOrderNum(i);
            userExamAnswer.setRightAnsId(que.getAnswerId());
            List<AnswerForm> answerlist = que.getAnswer();
            StringBuffer aio = new StringBuffer();
            for(AnswerForm answerForm : answerlist){
                aio.append(answerForm.getId()).append(",");
            }
            if(aio.length()>0) {
                String answerIdOrder = aio.deleteCharAt(aio.length() - 1).toString();
                userExamAnswer.setAnswerIdOrder(answerIdOrder);
            }else{
                userExamAnswer.setAnswerIdOrder("");
            }
            userExamAnswerService.insert(userExamAnswer);
        }
    }
}
