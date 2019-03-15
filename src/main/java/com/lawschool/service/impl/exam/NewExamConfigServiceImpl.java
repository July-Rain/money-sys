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
import com.lawschool.form.AnswerForm;
import com.lawschool.form.CommonForm;
import com.lawschool.form.ExamConfigForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.NewExamConfigService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ExamConfigService examConfigService;

    @Override
    public Result saveOrUpdate(ExamConfig examConfig, User user) {

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
            examConfig.setOrganizedOrgCode(user.getOrgCode().substring(0,6)+"000000");
            examConfig.setCreateTime(new Date());
            examConfig.setCreateUser(user.getId());
            dao.insert(examConfig);
        }
        //存权限表
        String[] deptIdArr = examConfig.getDeptArr();
        String[] userIdArr = examConfig.getUserArr();
        String[] deptTemp=new String[1];
        if(deptIdArr.length==0&&userIdArr.length==0) {
            deptTemp[0]=user.getOrgId();
            authService.insertAuthRelation(deptTemp, userIdArr, examConfig.getId(), "ExamConfig", examConfig.getGroupForm());
        }else{
            authService.insertAuthRelation(deptIdArr, userIdArr, examConfig.getId(), "ExamConfig", examConfig.getGroupForm());
        }
        return Result.ok().put("examConfig", examConfig);

    }

    @Override
    public Result genAutoQue(ExamConfigForm examConfigForm) {
        String id = examConfigForm.getId();
        ExamConfig examConfig = dao.selectById(id);
        examConfigService.deleteRelated(examConfig.getId());
        ExamDetail examDetail = saveExamDetail(examConfig.getId());
        examDetailDao.insert(examDetail);
        List<TestQuestions> sinMultipleSelection = examConfigForm.getSinMultipleSelection();
        if (sinMultipleSelection.size() > 0) {
            saveExamQueByComm(sinMultipleSelection, id, examDetail.getId(), examConfigForm.getSinMultScore());
        }
        List<TestQuestions> mulMultipleSelection = examConfigForm.getMulMultipleSelection();   //多选
        if (mulMultipleSelection.size() > 0) {
            saveExamQueByComm(mulMultipleSelection, id, examDetail.getId(), examConfigForm.getMulMultScore());
        }
        List<TestQuestions> judgeMultipleSelection = examConfigForm.getJudgeMultipleSelection();   //判断
        if (judgeMultipleSelection.size() > 0) {
            saveExamQueByComm(judgeMultipleSelection, id, examDetail.getId(), examConfigForm.getJudgeMultScore());
        }
        //自主
        List<TestQuestions> subMultipleSelection = examConfigForm.getSubMultipleSelection();
        if (subMultipleSelection.size() > 0) {
            saveExamQueByComm(subMultipleSelection, id, examDetail.getId(), 0);
        }
        examConfig.setExamCount(sinMultipleSelection.size()+mulMultipleSelection.size()+judgeMultipleSelection.size()+subMultipleSelection.size());
        examConfig.setStatus("1");
        dao.updateById(examConfig);
        return Result.ok();
    }

    @Override
    public Result genRandomQue(ExamConfigForm examConfigForm) throws Exception {
        String id = examConfigForm.getId();
        ExamConfig examConfig = dao.selectById(id);
        int examNum = 0;
        List<ExamQueConfig> examQueList = examConfigForm.getExamQueConfigList();
        for (ExamQueConfig examQueConfig : examQueList){
            examNum += examQueConfig.getQuestionNumber();
        }
        examConfigService.deleteRelated(examConfig.getId());
        if ("10027".equals(examConfig.getGroupForm())) {
            //统一组卷，生成一套试卷
            ExamDetail examDetail = saveExamDetail(examConfig.getId());
            examDetailDao.insert(examDetail);
            for (ExamQueConfig examQueConfig : examQueList) {
                // 根据配置从题库中获取
                //保存考试题目配置表
                examNum += examQueConfig.getQuestionNumber();
                saveExamQueConfig(examConfig.getId(), examQueConfig);
                //根据考试配置获取题目ID
                List<String> idLists = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
                saveExamQueByIdList(idLists, examConfig.getId(), examDetail.getId(), examQueConfig.getQuestionScore() / examQueConfig.getQuestionNumber());
            }
        } else {
            //生成50套
            int num = 50;
            generateNumExam(num, examConfig.getId(), examQueList);
        }
        examConfig.setStatus("1");
        examConfig.setExamCount(examNum);
        dao.updateById(examConfig);
        return Result.ok();
    }

    /**
     * 保存考试详情信息
     *
     * @param examConfigId
     */
    private ExamDetail saveExamDetail(String examConfigId) {
        ExamDetail examDetail = new ExamDetail();
        examDetail.setId(GetUUID.getUUIDs("ED"));
        examDetail.setOptTime(new Date());
        examDetail.setCreateTime(new Date());
        examDetail.setExamConfigId(examConfigId);
        return examDetail;
    }

    /**
     * 根据试题编号保存试题信息,自主出题
     *
     * @param list
     * @param examConfigId
     * @param examDetailId
     */
    private void saveExamQueByComm(List<TestQuestions> list, String examConfigId, String examDetailId, float score) {
        for (TestQuestions testQuestions : list) {
            ExamQuestions examQuestions = new ExamQuestions();
            examQuestions.setExamConfigId(examConfigId);
            examQuestions.setId(GetUUID.getUUIDs("EQ"));
            examQuestions.setExamDetailId(examDetailId);
            examQuestions.setExamLibraryId(testQuestions.getId());
            if (!"10007".equals(testQuestions.getQuestionType())) {
                examQuestions.setScore(score);
            } else {
                examQuestions.setScore(testQuestions.getPerScore());
            }
            examQuestionsDao.insert(examQuestions);
        }
    }

    private void saveExamQueConfig(String examConfigId, ExamQueConfig examQueConfig) {
        examQueConfig.setId(GetUUID.getUUIDs("EQC"));
        examQueConfig.setExamConfigId(examConfigId);
        examQueConfig.setSpecialKnowledgeId(StringUtils.join(examQueConfig.getSpecialKnowledgeArr(),","));
        examQueConfigDao.insert(examQueConfig);
    }

    /**
     *
     * @param examQueConfig
     * @param num   生成题目数量
     * @return
     */
    private List<String> getIdList(ExamQueConfig examQueConfig,int num) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("specialKnowledgeArr", examQueConfig.getSpecialKnowledgeArr());
        paramsMap.put("questionType", examQueConfig.getQuestionType());
        paramsMap.put("num", num);
        List<String> idList = testQuestionService.findByNum(paramsMap);
        if(idList.size()<num){
            String typeName = getTypeName(examQueConfig.getQuestionType());
            String msg = "题型“"+typeName+"”在此条件下题库中题目数量不足，目前题库中共有"+idList.size()+"题。";
            throw new Exception(msg);
        }
        return idList;
    }

    /**
     * 根据试题编号保存试题信息,随机出题
     *
     * @param idLists
     * @param examConfigId
     * @param examDetailId
     */
    private void saveExamQueByIdList(List<String> idLists, String examConfigId, String examDetailId, float score) {
        for (String tqId : idLists) {
            ExamQuestions examQuestions = new ExamQuestions();
            examQuestions.setExamConfigId(examConfigId);
            examQuestions.setId(GetUUID.getUUIDs("EQ"));
            examQuestions.setExamDetailId(examDetailId);
            examQuestions.setExamLibraryId(tqId);
            examQuestions.setScore(score);
            examQuestionsDao.insert(examQuestions);
        }
    }

    /**
     * 预览
     * @param examConfigForm
     * @return
     */
    @Override
    public Result preview(ExamConfigForm examConfigForm) throws Exception {
        List<TestQuestions> sinList = new ArrayList<>();  //单选列表
        List<TestQuestions> mulList = new ArrayList<>(); //多选列表
        List<TestQuestions> judList = new ArrayList<>();    //判断
        List<TestQuestions> subList = new ArrayList<>();    //主观
        List<String> sinIdList = new ArrayList<>();  //单选列表
        List<String> mulIdList = new ArrayList<>(); //多选列表
        List<String> judIdList = new ArrayList<>();    //判断
        List<String> subIdList = new ArrayList<>();    //主观
        for (ExamQueConfig examQueConfig : examConfigForm.getExamQueConfigList()) {
            if("10004".equals(examQueConfig.getQuestionType())){
                //单选
                sinIdList = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
            }else if ("10005".equals(examQueConfig.getQuestionType())){
                //多选
                mulIdList = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
            }else if ("10006".equals(examQueConfig.getQuestionType())){
                //判断
                judIdList = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
            }else{
                //主观
                subIdList = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
            }
        }
        sinList = installList(sinIdList,sinList);
        mulList = installList(mulIdList,mulList);
        judList = installList(judIdList,judList);
        subList = installList(subIdList,subList);
        return Result.ok().put("sinList",sinList).put("mulList",mulList)
                .put("judList",judList).put("subList",subList);
    }

    /**
     * 预览后生成
     * @param examConfigForm
     * @return
     */
    @Override
    public Result genRanQueAfterPreview(ExamConfigForm examConfigForm) throws Exception {
        //考试配置ID
        String id = examConfigForm.getId();
        //获取考试配置详情
        ExamConfig examConfig = dao.selectById(id);
        examConfigService.deleteRelated(examConfig.getId());
        //获取必考题目列表
        List<String> mustQueIdList = examConfigForm.getMustQueList();
        //获取所有必考题目详情
        List<TestQuestions> mustQueList = new ArrayList<>();
        if (mustQueIdList.size()>0){
            mustQueList = getList(mustQueIdList);
        }
        //单选列表
        List<String> sinIdList = new ArrayList<>();
        //多选列表
        List<String> mulIdList = new ArrayList<>();
        //判断
        List<String> judIdList = new ArrayList<>();
        //主观
        List<String> subIdList = new ArrayList<>();
        int examNum = 0 ;
        //获取考试题目设置
        List<ExamQueConfig> examQueList = examConfigForm.getExamQueConfigList();
        for (ExamQueConfig examQueConfig : examQueList){
            examNum += examQueConfig.getQuestionNumber();
        }
        for(TestQuestions tes : mustQueList){
            if("10004".equals(tes.getQuestionType())){
                sinIdList.add(tes.getId());
            }else if ("10005".equals(tes.getQuestionType())){
                mulIdList.add(tes.getId());
            }else if ("10006".equals(tes.getQuestionType())){
                judIdList.add(tes.getId());
            }else{
                subIdList.add(tes.getId());
            }
        }
        if ("10027".equals(examConfig.getGroupForm())) {
            //统一组卷，生成一套试卷
            generateNumExamAfterPreview(1,examConfig.getId(),examQueList,sinIdList,mulIdList,judIdList,subIdList);
        } else {
            //生成50套
            int num = 50;
            generateNumExamAfterPreview(50,examConfig.getId(),examQueList,sinIdList,mulIdList,judIdList,subIdList);
        }
        examConfig.setStatus("1");
        examConfig.setExamCount(examNum);
        dao.updateById(examConfig);
        return Result.ok();
    }

    private void generateNumExamAfterPreview(int num, String examConfigId,
                                             List<ExamQueConfig> examQueList,List<String> sinIdList,
                                             List<String> mulIdList,List<String> judIdList , List<String> subIdList) throws Exception{
        for (ExamQueConfig examQueConfig : examQueList) {
            //保存考试题目配置表
            saveExamQueConfig(examConfigId, examQueConfig);
        }
        for (int i = 0; i < num; i++) {
            ExamDetail ed = saveExamDetail(examConfigId);
            examDetailDao.insert(ed);
            //随机出题
            for (ExamQueConfig examQueConfig : examQueList) {
                // 根据配置从题库中获取

                //根据考试配置获取题目ID
                List<String> idLists = new ArrayList<>();
                if("10004".equals(examQueConfig.getQuestionType())){
                    //单选
                    idLists = getIdList(examQueConfig,examQueConfig.getQuestionNumber()-sinIdList.size());
                    idLists.addAll(sinIdList);
                }else if ("10005".equals(examQueConfig.getQuestionType())){
                    //多选
                    idLists = getIdList(examQueConfig,examQueConfig.getQuestionNumber()-mulIdList.size());
                    idLists.addAll(mulIdList);
                }else if ("10006".equals(examQueConfig.getQuestionType())){
                    //判断
                    idLists = getIdList(examQueConfig, examQueConfig.getQuestionNumber()-judIdList.size());
                    idLists.addAll(judIdList);
                }else{
                    //主观
                    idLists = getIdList(examQueConfig, examQueConfig.getQuestionNumber()-subIdList.size());
                    idLists.addAll(subIdList);
                }
                saveExamQueByIdList(idLists, examConfigId, ed.getId(), examQueConfig.getQuestionScore() / examQueList.size());
            }
        }
    }

    private void generateNumExam(int num, String examConfigId, List<ExamQueConfig> examQueList) throws Exception {
        for (ExamQueConfig examQueConfig : examQueList) {
            //保存考试题目配置表
            saveExamQueConfig(examConfigId, examQueConfig);
        }
        for (int i = 0; i < num; i++) {
            ExamDetail ed = saveExamDetail(examConfigId);
            examDetailDao.insert(ed);
            //随机出题
            for (ExamQueConfig examQueConfig : examQueList) {
                //保存考试题目配置信息
               /* saveExamQueConfig(examConfigId, examQueConfig);*/
                // 根据配置从题库中获取
                //根据考试配置获取题目ID
                List<String> idLists = getIdList(examQueConfig, examQueConfig.getQuestionNumber());
                if (idLists.size()<examQueConfig.getQuestionNumber()){
                    String typeName = getTypeName(examQueConfig.getQuestionType());
                    String msg = "题型“"+typeName+"”在此条件下题库中题目数量不足，目前题库中共有"+idLists.size()+"题。";
                    throw new Exception(msg);
                }
                saveExamQueByIdList(idLists, examConfigId, ed.getId(), examQueConfig.getQuestionScore() / examQueConfig.getQuestionNumber());
            }
        }
    }

    private List<TestQuestions> installList (List<String> idList, List<TestQuestions> testQuestionsList){
        if(UtilValidate.isNotEmpty(idList)) {
            testQuestionsList = getList(idList);
        }
        return  testQuestionsList;
    }
    public List<TestQuestions> getList(List<String> idList){
        List<TestQuestions> eqList = testQuestionService.listByIds(idList);
        // 遍历处理选项信息
        for(TestQuestions tes : eqList){
            tes.setAnswerList(answerService.getAnswerByQid(tes.getId()));
        }
        return  eqList;
    }

    private String getTypeName(String type){
        if( "10004".equals(type) ){
            return "单选题";
        }else if( "10005".equals(type) ){
            return "多选题";
        }else if("10006".equals(type)){
            return "判断题";
        }else{
            return "主观题";
        }
    }

}

