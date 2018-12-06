package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.*;
import com.lawschool.dao.*;
import com.lawschool.service.CollectionService;
import com.lawschool.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lawschool.util.Constant.*;
import static java.lang.Integer.parseInt;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionDao collectionDao;

    @Autowired
    TestQuestionsDao testQuestionsMapper;

    @Autowired
    OrgDao orgDao;

    @Autowired
    PracticePaperDao practicePaperDao;

    @Autowired
    PracticeRelevanceDao practiceRelevanceDao;

    @Autowired
    AnswerDao answerDao;

    @Override
    //1 删  0 留  取消收藏
    public int delCollection(Collection collection, User user) {
        return this.changeStatus(collection,user,true);
    }

    @Override
    //添加收藏
    public int addCollection(Collection collection,User user) {
        String[] existStatus = this.isExist(collection,user);//收藏状态
        if(existStatus[0].equals(IS_NOT_EXIST+"")){//新增
            collection.setId(GetUUID.getUUIDs("CO"));
            collection.setComUserid(user.getId());
            collection.setOptuser(user.getFullName());
            collection.setOpttime(new Date());
            collection.setDelStatus((short)0);
            return collectionDao.insert(collection)==1?SUCCESS:ERROR;//1 添加成功  0 添加失败
        }
        if(existStatus[0].equals(ONCE_EXIST+"")){//重新收藏
            collection.setOpttime(new Date());
            collection.setId(existStatus[1]);
            return this.changeStatus(collection,user,false)==1?SUCCESS:ERROR;//1 成功/ 0 失败
        }
        if(existStatus[0].equals(IS_EXITS+"")){
            return IS_EXITS;
        }
        return ERROR;
    }

    //判断是否被收藏过：
    private  String[]  isExist(Collection collection,User user){
        EntityWrapper<Collection> ew=new EntityWrapper();
        ew.eq("COM_USERID",user.getId()).eq("COM_STUCODE",collection.getComStucode()).eq("TYPE",collection.getType());
        List<Collection> collections = collectionDao.selectList(ew);
        if(UtilValidate.isNotEmpty(collections) && collections.size()==1){
            if(collections.get(0).getDelStatus()==0) return new String[]{IS_EXITS+""};//2 目前被收藏中
            return  new String[]{ONCE_EXIST+"",collections.get(0).getId()};//1 之前被收藏过然后取消收藏了
        }
       return  new String[]{IS_NOT_EXIST+""};
    }

    //改变状态 true 取消收藏  false 重新收藏
    private int changeStatus(Collection collection,User user,boolean flag){
        collection.setOpttime(new Date());
        EntityWrapper<Collection> ew=new EntityWrapper();
        ew.eq("COM_USERID",user.getId()).eq("ID",collection.getId());
        if(flag){//0-》1  取消收藏
            collection.setDelStatus((short)1);
            ew.eq("DEL_STATUS",0);
        }else {//1-》0 重新收藏
            collection.setDelStatus((short)0);
            ew.eq("DEL_STATUS",1);
        }
        return collectionDao.update(collection,ew)==1?SUCCESS:ERROR;//1 改变成功 0 改变失败
    }

    //我的收藏-重点试题（我收藏的题目）-zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo = 1;
        long pageSize = 10l;
        if (UtilValidate.isNotEmpty(param.get("pageNo"))) {
            pageNo = parseInt((String) param.get("pageNo"));
        }
        if (UtilValidate.isNotEmpty(param.get("pageSize"))) {
            pageSize = Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count = testQuestionsMapper.cntMyCollection(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("pageSize", pageNo * pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }


    //重点试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result randomQuestColl(Map<String, Object> param, User user) {
        int num=10;
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isNotEmpty(param.get("num"))){
            try{
                num= parseInt(param.get("num").toString());
            }catch(Exception e){
                return Result.error("题目个数要为整型");
            }
        }
        param.put("num",num);//获取组成10题
        param.put("userId",user.getId());
        param.put("userId",1);
        List<TestQuestions> testQuestions = testQuestionsMapper.randomQuestColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String pid = GetUUID.getUUIDs("PP");
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(pid);

        practicePaper.setPracCreatUser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setPracCreatTime(new Date());

        practicePaper.setPracticeName(UtilValidate.isNotEmpty(param.get("pname"))? param.get("pname").toString():"收藏练习");

        if(UtilValidate.isNotEmpty(user.getOrgCode())){
            List<Org> org_code = orgDao.selectList(new EntityWrapper<Org>().setSqlSelect("DICTIONARY_NAME").eq("ORG_CODE", user.getOrgCode()));
            practicePaper.setPracCreatDepartment(UtilValidate.isNotEmpty(org_code)?org_code.get(0).getDictionaryName():"");
        }
        practicePaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setOpttime(new Date());

        if(UtilValidate.isNotEmpty(param.get("knowledge"))){
            practicePaper.setStuKnowledge(param.get("knowledge").toString());
        }

        practicePaper.setCount(num);
        practicePaper.setPracPaperType("自定义");

        practicePaperDao.insert(practicePaper);

        //3.生成练习-试题关联表，以及选项的获取
        testQuestions.stream().forEach(e->{
            String qid=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(qid);//获取题目
            List<Answer> answers = answerDao.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", qid));//选项

            //记录表
            String rid = GetUUID.getUUIDs("PR");
            PracticeRelevance relevance=new PracticeRelevance();
            relevance.setId(rid);
            relevance.setPracticeId(pid);
            relevance.setQuestionId(qid);

            practiceRelevanceDao.insert(relevance);

            map.put(question,answers);
        });


        return Result.ok().put("pid",pid).put("data",map);
    }

    //我的收藏-我的错题（获取我的所有的错题）-zjw
    @Override
    public PageUtils listMyErrorQuestion(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo= parseInt((String) param.get("pageNo"));
        }
        if (UtilValidate.isNotEmpty(param.get("pageSize"))) {
            pageSize = Long.parseLong((String) param.get("pageSize"));
        }

        //总个数
        int count = testQuestionsMapper.cntMyError(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("pageSize", pageNo * pageSize);
        List<TestQuestions> testQuestions = testQuestionsMapper.listMyError(param);

        PageUtils page = new PageUtils(testQuestions, count, pageSize, pageNo);

        return page;
    }

    //重点试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  Result randomErrorColl(Map<String, Object> param,User user) {
        int num=10;
        //1,生成题目
        Map<TestQuestions,List<Answer>> map=new HashedMap();
        if(UtilValidate.isNotEmpty(param.get("num"))){
            try{
                num= parseInt(param.get("num").toString());
            }catch(Exception e){
                return Result.error("题目个数要为整型");
            }
        }
        param.put("num",num);//获取组成10题
        param.put("userId",user.getId());
        param.put("userId",1);

        List<TestQuestions> testQuestions = testQuestionsMapper.randomErrorColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String pid = IdWorker.get32UUID();
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(pid);

        practicePaper.setPracCreatUser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setPracCreatTime(new Date());

        practicePaper.setPracticeName(UtilValidate.isNotEmpty(param.get("pname"))? param.get("pname").toString():"错题练习");

        if(UtilValidate.isNotEmpty(user.getOrgCode())){
            List<Org> org_code = orgDao.selectList(new EntityWrapper<Org>().setSqlSelect("DICTIONARY_NAME").eq("ORG_CODE", user.getOrgCode()));
            practicePaper.setPracCreatDepartment(UtilValidate.isNotEmpty(org_code)?org_code.get(0).getDictionaryName():"");
        }
        practicePaper.setOptuser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setOpttime(new Date());

        if(UtilValidate.isNotEmpty(param.get("knowledge"))){
            practicePaper.setStuKnowledge(param.get("knowledge").toString());
        }

        practicePaper.setCount(num);
        practicePaper.setPracPaperType("自定义");

        practicePaperDao.insert(practicePaper);
        //3.生成练习-试题关联表，以及选项的获取
        testQuestions.stream().forEach(e->{
            String qid=e.getId();
            TestQuestions question = testQuestionsMapper.selectById(qid);//获取题目
            List<Answer> answers = answerDao.selectList(new EntityWrapper<Answer>().eq("QUESTION_ID", qid));//答案
            //记录表
            String rid = IdWorker.get32UUID();
            PracticeRelevance relevance=new PracticeRelevance();
            relevance.setId(rid);
            relevance.setPracticeId(pid);
            relevance.setQuestionId(qid);

            practiceRelevanceDao.insert(relevance);
            map.put(question,answers);
        });
        return Result.ok().put("pid",pid).put("data",map);
    }

    //详情-zjw
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        TestQuestions testQuestions1 = testQuestionsMapper.selectById(testQuestions.getId());
        return testQuestions1;
    }

}
