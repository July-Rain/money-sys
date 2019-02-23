package com.lawschool.service.impl.exam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.ExamDetail;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.beans.exam.ExamQuestions;
import com.lawschool.dao.exam.ExamConfigDao;
import com.lawschool.dao.exam.ExamDetailDao;
import com.lawschool.dao.exam.ExamQueConfigDao;
import com.lawschool.dao.exam.ExamQuestionsDao;
import com.lawschool.form.CommonForm;
import com.lawschool.form.ExamConfigForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.NewExamConfigService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: NewExamConfigServiceImpl
 * Description: TODO
 * date: 2019/2/1915:16
 *
 * @author 王帅奇
 */
@Service
public class NewExamConfigServiceImpl extends AbstractServiceImpl<ExamConfigDao, ExamConfig> implements NewExamConfigService {

    @Autowired
    private AuthRelationService authService;

    @Autowired
    private ExamDetailDao examDetailDao;

    @Autowired
    private ExamQuestionsDao examQuestionsDao;

    @Autowired
    private ExamQueConfigDao examQueConfigDao;

    @Autowired
    private TestQuestionService testQuestionService;

    @Override
    public Result saveOrUpdate(ExamConfig examConfig, User user) {
        Date date = new Date();
        if(UtilValidate.isNotEmpty(examConfig.getId())&&date.after(examConfig.getStartTime())){
            return	Result.error("考试开始后不允许修改");
        } else {
            String[] arr = examConfig.getSpecialKnowledgeArr();
            if (UtilValidate.isNotEmpty(arr)) {
                examConfig.setSpecialKnowledgeId(StringUtils.join(arr, ","));
            }
            examConfig.setOptTime(new Date());
            examConfig.setOptUser(user.getUserId());
            if (UtilValidate.isNotEmpty(examConfig.getId())) {
                //update 操作
                ExamConfig ec = dao.selectById(examConfig.getId());
                if (!ec.getQuestionWay().equals(examConfig.getQuestionWay())) {
                    examConfig.setStatus("0");
                }
                //删除权限表
                authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag", "ExamConfig").eq("function_Id", examConfig.getId()));
                dao.updateById(examConfig);
            } else {
                //insert 操作
                examConfig.setId(GetUUID.getUUIDs("EC"));
                examConfig.setStatus("0");

                examConfig.setCreateTime(new Date());
                examConfig.setCreateUser(user.getId());
                dao.insert(examConfig);
            }
            //存权限表
            String[] deptIdArr = examConfig.getDeptArr();
            String[] userIdArr = examConfig.getUserArr();
            authService.insertAuthRelation(deptIdArr, userIdArr, examConfig.getId(), "ExamConfig", examConfig.getGroupForm());
            return Result.ok();
        }
    }

    @Override
    public Result genAutoQue(ExamConfigForm examConfigForm) {
        String id = examConfigForm.getId();
        ExamConfig examConfig = dao.selectById(id);
        ExamDetail examDetail = saveExamDetail(examConfig.getId());
        examDetailDao.insert(examDetail);
        List<TestQuestions> sinMultipleSelection = examConfigForm.getSinMultipleSelection();
        if(sinMultipleSelection.size()>0){
            saveExamQueByComm(sinMultipleSelection,id,examDetail.getId(),examConfigForm.getSinMultScore());
        }
        List<TestQuestions> mulMultipleSelection = examConfigForm.getMulMultipleSelection();   //多选
        if(mulMultipleSelection.size()>0){
            saveExamQueByComm(mulMultipleSelection,id,examDetail.getId(),examConfigForm.getMulMultScore());
        }
        List<TestQuestions> judgeMultipleSelection = examConfigForm.getJudgeMultipleSelection();   //判断
        if(judgeMultipleSelection.size()>0){
            saveExamQueByComm(judgeMultipleSelection,id,examDetail.getId(),examConfigForm.getJudgeMultScore());
        }
        List<TestQuestions> subMultipleSelection = examConfigForm.getSubMultipleSelection();   //自主
        if(subMultipleSelection.size()>0){
            saveExamQueByComm(subMultipleSelection,id,examDetail.getId(),0);
        }
        examConfig.setStatus("1");
        dao.updateById(examConfig);
        return Result.ok();
    }

    @Override
    public Result genRandomQue(ExamConfigForm examConfigForm) {
        String id = examConfigForm.getId();
        ExamConfig examConfig = dao.selectById(id);
        List<ExamQueConfig> examQueList = examConfigForm.getExamQueConfigList();
        if("10027".equals(examConfig.getGroupForm())){
            //统一组卷，生成一套试卷
            ExamDetail examDetail = saveExamDetail(examConfig.getId());
            examDetailDao.insert(examDetail);
            for (ExamQueConfig examQueConfig : examQueList) {
                // 根据配置从题库中获取
                //保存考试题目配置表
                saveExamQueConfig(examConfig.getId(),examQueConfig);
                //根据考试配置获取题目ID
                List<String> idLists = getIdList(examQueConfig);
                saveExamQueByIdList(idLists,examConfig.getId(),examDetail.getId(),examQueConfig.getQuestionScore()/examQueList.size());
            }
        }else{
            //生成50套
            int num =50;
            generateNumExam(num,examConfig.getId(),examQueList);
        }
        examConfig.setStatus("1");
        dao.updateById(examConfig);
        return Result.ok();
    }

    /**
     * 保存考试详情信息
     * @param examConfigId
     */
    private ExamDetail saveExamDetail(String examConfigId){
        ExamDetail examDetail = new ExamDetail();
        examDetail.setId(GetUUID.getUUIDs("ED"));
        examDetail.setOptTime(new Date());
        examDetail.setCreateTime(new Date());
        examDetail.setExamConfigId(examConfigId);
        return examDetail;
    }

    /**
     * 根据试题编号保存试题信息,自主出题
     * @param list
     * @param examConfigId
     * @param examDetailId
     */
    private void saveExamQueByComm(List<TestQuestions> list, String examConfigId, String examDetailId,float score) {
        for (TestQuestions testQuestions : list) {
            ExamQuestions examQuestions = new ExamQuestions();
            examQuestions.setExamConfigId(examConfigId);
            examQuestions.setId(GetUUID.getUUIDs("EQ"));
            examQuestions.setExamDetailId(examDetailId);
            examQuestions.setExamLibraryId(testQuestions.getId());
            if (!"10007".equals(testQuestions.getQuestionType())) {
                examQuestions.setScore(score);
            }else{
                examQuestions.setScore(testQuestions.getPerScore());
            }
            examQuestionsDao.insert(examQuestions);
        }
    }

    private void generateNumExam(int num,String examConfigId,List<ExamQueConfig> examQueList){
        for(int i=0;i<num;i++){
            ExamDetail ed = saveExamDetail(examConfigId);
            examDetailDao.insert(ed);
            //随机出题
            for (ExamQueConfig examQueConfig : examQueList) {
                //保存考试题目配置信息
                saveExamQueConfig(examConfigId,examQueConfig);
                // 根据配置从题库中获取
                //根据考试配置获取题目ID
                List<String> idLists = getIdList(examQueConfig);
                saveExamQueByIdList(idLists,examConfigId,ed.getId(),examQueConfig.getQuestionScore()/examQueList.size());
            }
        }
    }

    private void saveExamQueConfig(String examConfigId,ExamQueConfig examQueConfig){
        examQueConfig.setId(GetUUID.getUUIDs("EQC"));
        examQueConfig.setLawExamConfigId(examConfigId);
        examQueConfigDao.insert(examQueConfig);
    }

    /**
     * 获取题目ID列表
     * @param examQueConfig
     * @return
     */
    private List<String> getIdList(ExamQueConfig examQueConfig){
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("specialKnowledgeId",examQueConfig.getSpecialKnowledgeId());
        paramsMap.put("questionType",examQueConfig.getQuestionType());
        paramsMap.put("num",examQueConfig.getQuestionNumber());
        List<String> idList = testQuestionService.findByNum(paramsMap);
        return idList;
    }
    /**
     * 根据试题编号保存试题信息,随机出题
     * @param idLists
     * @param examConfigId
     * @param examDetailId
     */
    private void saveExamQueByIdList(List<String> idLists,String examConfigId,String examDetailId,float score){
        for (String tqId : idLists){
            ExamQuestions examQuestions = new ExamQuestions();
            examQuestions.setExamConfigId(examConfigId);
            examQuestions.setId(GetUUID.getUUIDs("EQ"));
            examQuestions.setExamDetailId(examDetailId);
            examQuestions.setExamLibraryId(tqId);
            examQuestions.setScore(score);
            examQuestionsDao.insert(examQuestions);
        }
    }

}

