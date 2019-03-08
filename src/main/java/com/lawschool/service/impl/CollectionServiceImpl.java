package com.lawschool.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.*;
import com.lawschool.beans.Collection;
import com.lawschool.dao.*;
import com.lawschool.service.CollectionService;
import com.lawschool.service.ExerciseConfigureService;
import com.lawschool.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.lawschool.util.Constant.*;
import static java.lang.Integer.parseInt;

@Service
public class CollectionServiceImpl  extends ServiceImpl<CollectionDao,Collection> implements CollectionService{

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private TestQuestionsDao testQuestionsMapper;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private PracticePaperDao practicePaperDao;

    @Autowired
    private PracticeRelevanceDao practiceRelevanceDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserQuestRecordDao userQuestRecordDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ExerciseConfigureService exerciseConfigureService;

    /**
     * 收藏功能
     * @param type 收藏类型：10课件收藏、20重点试题收藏、30错题收藏
     * @param comStuCode 课件/试题ID
     * @param isCancel true取消收藏、false收藏
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doCollection(String type, String comStuCode, boolean isCancel){
        User user = (User)SecurityUtils.getSubject().getPrincipal();

        // 1.获取对应收藏数据
        Collection collection = collectionDao.findOne(type, comStuCode, user.getId());

        if(collection == null){
            // 未被收藏过
            if(isCancel){
                // 取消收藏，正常情况下不存在此情况，直接返回结果
                return true;

            } else {
                // 直接收藏数据

                collection = new Collection();
                collection.setComStucode(comStuCode);
                collection.setType(Integer.parseInt(type));
                collection.setId(IdWorker.getIdStr());
                collection.setDelStatus(0);// 删除状态，0未删除
                collection.setOpttime(new Date());// 收藏时间
                collection.setOptuser(user.getFullName());
                collection.setComUserid(user.getId());

                int result = collectionDao.insert(collection);

                return result == 1 ? true : false;
            }
        } else {
            // 有收藏记录
            // 当前收藏状态，true收藏、false取消收藏
            boolean flag = collection.getDelStatus() == 0 ? true : false;

            if(flag == isCancel){
                // 更新收藏状态
                return collectionDao.doAgain(collection.getId(), isCancel == true ? 1 : 0);
            } else {
                // 不用盘它，直接结束
                return true;
            }

        }
    }

    @Override
    //1 删  0 留  取消收藏
    public int delCollection(Collection collection, User user) {
        return this.changeStatus(collection,user,true)==1?SUCCESS:ERROR;
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
            collection.setDelStatus(0);
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
        ew.eq("COM_USERID",user.getId());
        if(UtilValidate.isNotEmpty(collection.getId())){
            ew.eq("ID",collection.getId());
        }
        if(UtilValidate.isNotEmpty(collection.getComStucode())){
            ew.eq("com_stucode",collection.getComStucode());
        }
        if(flag){//0-》1  取消收藏
            collection.setDelStatus(1);
            ew.eq("DEL_STATUS",0);
        }else {//1-》0 重新收藏
            collection.setDelStatus(0);
            ew.eq("DEL_STATUS",1);
        }
        return collectionDao.update(collection,ew)==1?1:0;//1 改变成功 0 改变失败
    }

    //我的收藏-重点试题（我收藏的题目）-zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {

        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        long pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());

        //总个数
        int count = testQuestionsMapper.cntMyCollection(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("endPage", pageNo * pageSize);

        List<TestQuestions> testQuestions = testQuestionsMapper.listMyCollection(param);

        PageUtils page=new PageUtils(testQuestions,count,pageSize, pageNo);

        return page;
    }


    //重点试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result randomQuestColl( Map<String, Object> params, User user) {
        //生成试卷名称
        String name = generateName(params.get("pname").toString());
        ExerciseConfigureEntity entity = new ExerciseConfigureEntity();
        entity.preInsert(user.getId());
        entity.setDelFlag(0);
        entity.setUserName(user.getUserName());
        entity.setUsers(user.getId());
        entity.setDepts(null);
        entity.setTotal(parseInt(params.get("num").toString()));
        entity.setName(name);
        entity.setPrefix(params.get("pname").toString());
        entity.setSourceFrom(params.get("sourceFrom").toString());
        exerciseConfigureService.insert(entity);
        return Result.ok().put("id", entity.getId()).put("name",entity.getName());
    }

    //我的收藏-我的错题（获取我的所有的错题）-zjw
    @Override
    public PageUtils listMyErrorQuestion(Map<String, Object> param) {
        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        long pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());
        //总个数
        int count = userQuestRecordDao.cntMyError(param);

        param.put("startPage", (pageNo - 1) * pageSize);
        param.put("endPage", pageNo * pageSize);
        List<TestQuestions> testQuestions = userQuestRecordDao.listMyError(param);

        List<TestQuestions> lst=new ArrayList<>();

        testQuestions.stream().forEach(e->{
            lst.add(testQuestionsMapper.getInfoById(e));
        });

        PageUtils page = new PageUtils(lst, count, pageSize, pageNo);

        return page;
    }

    //错题试题-组卷-zjw
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  Result randomErrorColl(Map<String, Object> param,User user) {
        int num=10;
        //1,生成题目
        Map<String,TestQuestions> map=new HashedMap();
        if(UtilValidate.isNotEmpty(param.get("num"))){
            try{
                num= parseInt(param.get("num").toString());
            }catch(Exception e){
                return Result.error("题目个数要为整型");
            }
        }
        param.put("num",num);//获取组成10题
        param.put("userId",user.getId());
        List<TestQuestions> testQuestions = userQuestRecordDao.randomErrorColl(param);//仅仅只有id,提高效率

        //2。生成练习
        String pid = IdWorker.get32UUID();
        PracticePaper practicePaper=new PracticePaper();
        practicePaper.setId(pid);

        practicePaper.setPracCreatUser(UtilValidate.isNotEmpty(user.getUserName())?user.getUserName():"");
        practicePaper.setPracCreatTime(new Date());

        practicePaper.setPracticeName(UtilValidate.isNotEmpty(param.get("pname"))? param.get("pname").toString():"错题练习");

        if(UtilValidate.isNotEmpty(user.getOrgCode())){
            List<Org> org_code = orgDao.selectList(new EntityWrapper<Org>().setSqlSelect("ORG_NAME").eq("ORG_CODE", user.getOrgCode()));
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

            question.setAnswerList(answers);
            map.put(question.getId(),question);
        });
        return Result.ok().put("pid",pid).put("data",map);
    }

    //详情-zjw
    @Override
    public TestQuestions getTestQuestions(TestQuestions testQuestions) {
        TestQuestions testQuestions1 = testQuestionsMapper.getInfoById(testQuestions);
        return testQuestions1;
    }

    public boolean cancle(String questionId, String userId){

        return collectionDao.cancle(questionId, userId);
    }

    private String generateName(String prefix){
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String time = sim.format(date);
        String name = prefix + "" + time;

        String key = "NUMBER" + time;
        String value = redisUtil.getNumber(key, 24*60*60, 4);

        name += value;
        return name;
    }
}
