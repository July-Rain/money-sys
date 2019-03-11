package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.beans.learn.TasksUserEntity;
import com.lawschool.dao.StuMediaDao;
import com.lawschool.dao.learn.LearnTasksDao;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.UserService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.law.CaseAnalysisService;
import com.lawschool.service.law.ClassifyDesicService;
import com.lawschool.service.law.TaskDesicService;
import com.lawschool.service.learn.LearnTasksService;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.service.learn.TasksUserService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LearnTasksServiceImpl
 * Description: 学习任务serviceImpl
 * date: 2018-12-18 16:17
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class LearnTasksServiceImpl extends AbstractServiceImpl<LearnTasksDao,LearnTasksEntity> implements LearnTasksService {
    @Autowired
    private LearnTasksDao mapper;

    @Autowired
    private AuthRelationService authService;

    @Autowired
    private TaskDesicService desicService;

    @Autowired
    private StuRecordService recordService;

    @Autowired
    private ClassifyDesicService classifyDesicService;

    @Autowired
    private StuMediaService mediaService;

    @Autowired
    private CaseAnalysisService analysisService;

    @Autowired
    private TasksUserService tasksUserService;

    @Autowired
    private UserService userService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLearnTask(LearnTasksEntity learnTask, User user,String menuForm) {
        //存学习任务基本信息

        learnTask.setOptUser(user.getId());
        learnTask.setOptTime(new Date());
        learnTask.setCreateUser(user.getId());
        learnTask.setCreateTime(new Date());
        learnTask.setDeptCode(user.getOrgCode());

        int countUser=0;
        //存权限表
        if("person".equals(menuForm)){
            String[] userIdArr={user.getId()};
            authService.insertAuthRelation(null,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
            //保存对应的任务和人员信息
            countUser = tasksUserService.insertLearnUser(null,userIdArr,learnTask.getId());
        }else{
            //存权限表
            String[] deptIdArr=learnTask.getDeptArr();
            String[] userIdArr=learnTask.getUserArr();

            String[] deptTemp=new String[1];
            if(UtilValidate.isNotEmpty(deptIdArr)&&UtilValidate.isNotEmpty(userIdArr)&&deptIdArr.length==0&&userIdArr.length==0){
                deptTemp[0]=user.getOrgId();
                authService.insertAuthRelation(deptTemp,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
            }else{
                authService.insertAuthRelation(deptIdArr,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
            }
            //保存对应的任务和人员信息
            countUser = tasksUserService.insertLearnUser(deptIdArr,userIdArr,learnTask.getId());
        }
        learnTask.setCountUser(countUser);

        mapper.insert(learnTask);
        //保存任务节点
        insertBathTaskDesic(learnTask.getTaskContentList(),learnTask.getId());

    }
    public void insertBathTaskDesic(List<TaskDesicEntity> list,String taskId){
        if(UtilValidate.isNotEmpty(list)){
            list.stream().forEach(e->{
                e.setCreateTime(new Date());
                e.setId(GetUUID.getUUIDs("TD"));
                e.setTaskId(taskId);
            });
            desicService.insertBatch(list);
        }
    }

    @Override
    public LearnTasksEntity selectLearnTask(String id) {
        //获取学习实体
        LearnTasksEntity learnTasksEntity = mapper.selectById(id);
        if(UtilValidate.isNotEmpty(learnTasksEntity)){
            //获取适用人的id
            String [] userIdArr= authService.getUserIdArr(id,"LEARNTASK") ;
            learnTasksEntity.setUserArr(userIdArr);
            //获取适用部门的id
            String [] deptIdArr= authService.getDeptIdArr(id,"LEARNTASK") ;
            learnTasksEntity.setDeptArr(deptIdArr);

            //获取任务的节点
            List<TaskDesicEntity> list =desicService.selectList(
                    new EntityWrapper<TaskDesicEntity>()
                    .eq("task_id",learnTasksEntity.getId())
            );
            learnTasksEntity.setTaskContentList(list);
        }
        return learnTasksEntity;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params,User user) {
        String userId=(String)params.get("userId");
        String taskName = (String)params.get("taskName");
        String taskContent = (String)params.get("taskContent");
        String taskClass = (String)params.get("taskClass");
        String policeclass = (String)params.get("policeclass");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String createUser=(String)params.get("createUser");//创建人
        EntityWrapper<LearnTasksEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,TASK_NAME,START_TIME,END_TIME,TASK_CONTENT,CREATE_USER,DEPT_CODE,DICTCODE2VALE(POLICECLASS) as policeclassName,DICTCODE2VALE(TASK_CLASS) as taskClassName");
        ew.eq("is_use","1");
        if(UtilValidate.isNotEmpty(taskName)){
            ew.like("TASK_NAME",taskName);
        }
        if(UtilValidate.isNotEmpty(taskContent)){
            ew.like("TASK_CONTENT",taskContent);
        }
        if(UtilValidate.isNotEmpty(taskClass)){
            ew.eq("task_class",taskClass);
        }
        if(UtilValidate.isNotEmpty(policeclass)){
            ew.eq("policeclass",policeclass);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(UtilValidate.isNotEmpty(startTime)){
            Date startParse = null;
            try {
                startParse = sdf.parse(startTime);
                ew.ge("START_TIME", startParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(endTime)){
            Date endParse = null;
            try {
                endParse = sdf.parse(endTime);
                ew.le("END_TIME", endParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(createUser)){  //创建人
            ew.eq("CREATE_USER",createUser);
        }
        if(UtilValidate.isNotEmpty(params.get("isAuth"))){
            //需要权限过滤
            String[] authArr= authService.listAllIdByUser(user.getOrgId(),user.getId(),"LEARNTASK");
            if(authArr.length>0){
                ew.in("id",authArr);
            }else{
                String[] arr={"0"};
                ew.in("id",arr);
            }
        }
        ew.orderBy("CREATE_TIME",false);
        Page<LearnTasksEntity> page = this.selectPage(
                new Query<LearnTasksEntity>(params).getPage(),ew);
        //设置学习任务中课程数量以及当前登陆人学习数量
        List<LearnTasksEntity> tasksEntities=page.getRecords();
        tasksEntities.stream().forEach(e->{
            int allCount = desicService.selectCount(new EntityWrapper<TaskDesicEntity>().eq("task_id",e.getId()).like("info_type","_data"));
            int finishCount = recordService.selectCount(new EntityWrapper<StuRecordEntity>().eq("task_id",e.getId()).eq("user_id",userId));
            e.setAllCount(allCount);
            e.setFinishCount(finishCount);
        });
        page.setRecords(tasksEntities);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageICreate(Map<String, Object> params) {
        String userId=(String)params.get("userId");
        String taskName = (String)params.get("taskName");
        String taskClass = (String)params.get("taskClass");
        String policeclass = (String)params.get("policeclass");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String orgCode = (String)params.get("orgCode");
        String isUse = (String)params.get("isUse");
        String isSelf = (String)params.get("isSelf");
        EntityWrapper<LearnTasksEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,TASK_NAME,START_TIME,END_TIME,TASK_CONTENT,CREATE_USER,DEPT_CODE,DICTCODE2VALE(POLICECLASS) as policeclassName,DICTCODE2VALE(TASK_CLASS) as taskClassName,is_use,count_user");
        if(UtilValidate.isNotEmpty(taskName)){
            ew.like("TASK_NAME",taskName);
        }
        if(UtilValidate.isNotEmpty(taskClass)){
            ew.eq("task_class",taskClass);
        }
        if(UtilValidate.isNotEmpty(policeclass)){
            ew.eq("policeclass",policeclass);
        }
        if(UtilValidate.isNotEmpty(isSelf)){
            ew.eq("is_self",isSelf);
        }else{
            ew.eq("is_self","0");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(UtilValidate.isNotEmpty(startTime)){
            Date startParse = null;
            try {
                startParse = sdf.parse(startTime);
                ew.ge("START_TIME", startParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(endTime)){
            Date endParse = null;
            try {
                endParse = sdf.parse(endTime);
                ew.le("END_TIME", endParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(orgCode)){
            ew.like("dept_code",orgCode);
        }

        if(UtilValidate.isNotEmpty(userId)){  //创建人
            ew.eq("CREATE_USER",userId);
        }
        if(UtilValidate.isNotEmpty(isUse)){  //是否发布
            ew.eq("is_use",isUse);
        }
        ew.orderBy("CREATE_TIME",false);
        Page<LearnTasksEntity> page = this.selectPage(
                new Query<LearnTasksEntity>(params).getPage(),ew);
        //设置学习任务中课程数量以及当前登陆人学习数量
        List<LearnTasksEntity> tasksEntities=page.getRecords();
        tasksEntities.stream().forEach(e->{
            //完成的人数
            int finishCount = tasksUserService.selectCount(new EntityWrapper<TasksUserEntity>().eq("task_id",e.getId()).eq("is_finish","1"));
            //总完成人数
            int allCount = e.getCountUser();
            //超时完成的个数
            int overCount = tasksUserService.selectCount(new EntityWrapper<TasksUserEntity>().eq("task_id",e.getId()).eq("is_finish","1").gt("finish_time",e.getEndTime()));

            e.setAllCount(allCount);
            e.setFinishCount(finishCount);
            e.setOverCount(overCount);
        });
        page.setRecords(tasksEntities);
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLearnTask(LearnTasksEntity learnTask, User user,String menuForm) {
        learnTask.setCreateUser(user.getId());
        learnTask.setCreateTime(new Date());
        mapper.updateById(learnTask);
        //删除权限表
        authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag","LEARNTASK").eq("function_Id",learnTask.getId()));
        //删除任务表
        desicService.delete(new EntityWrapper<TaskDesicEntity>().eq("task_id",learnTask.getId()));
        //删除用户和任务关联表
        tasksUserService.delete(new EntityWrapper<TasksUserEntity>().eq("task_id",learnTask.getId()));
        int countUser=0;
        //存权限表
        if("person".equals(menuForm)){
            String[] userIdArr={user.getId()};
            authService.insertAuthRelation(null,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getOptUser());
            //保存对应的任务和人员信息
            countUser = tasksUserService.insertLearnUser(null,userIdArr,learnTask.getId());

        }else{
            //存权限表
            String[] deptIdArr=learnTask.getDeptArr();
            String[] userIdArr=learnTask.getUserArr();
            String[] deptTemp=new String[1];
            if(UtilValidate.isNotEmpty(deptIdArr)&&UtilValidate.isNotEmpty(userIdArr)&&deptIdArr.length==0&&userIdArr.length==0){
                deptTemp[0]=user.getOrgId();
                authService.insertAuthRelation(deptTemp,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
            }else{
                authService.insertAuthRelation(deptIdArr,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
            }
            //保存对应的任务和人员信息
            countUser = tasksUserService.insertLearnUser(deptIdArr,userIdArr,learnTask.getId());

        }
        learnTask.setCountUser(countUser);
        //保存任务节点
        insertBathTaskDesic(learnTask.getTaskContentList(),learnTask.getId());
    }

    @Override
    public PageUtils queryContentByTask(Map<String, Object> params) {
        PageUtils page= null;
        if(UtilValidate.isNotEmpty(params)) {
            String infoType = (String) params.get("infoType");
            //String infoId = (String) params.get("infoId");
            //String taskId = (String) params.get("taskId");
            //判断来源选择的是什么

            if("law".equals(infoType)){
                //获取的是法律法规数据
                params.put("infoType","law_data");
                page=classifyDesicService.queryListByTask(params);
            }
            else{
                params.put("infoType",infoType+"_data");
                page= mediaService.listStuByTask(params);
                //设置当前数据的视频或者音频地址信息
                List<TaskDesicEntity> tasksEntities=(List<TaskDesicEntity> )page.getList();
                tasksEntities.stream().forEach(e->{
                    if(e.getInfoType().contains("stu_")){
                        //学习管理内容
                        StuMedia stu =mediaService.selectById(e.getDataId());
                        if(UtilValidate.isNotEmpty(stu)){
                            e.setAccId(stu.getComContent());
                            e.setVideoPicId(stu.getVideoPicAcc());
                        }
                    }else{
                        //案例分析内容
                        CaseAnalysisEntity ana= analysisService.selectById(e.getDataId());
                        if(UtilValidate.isNotEmpty(ana)){
                            e.setAccId(ana.getCaseContent());
                            e.setVideoPicId(ana.getVideoPicAcc());
                        }

                    }
                });
                page.setList(tasksEntities);
            }
            /*if (("sham".equals(infoType) && "law_90001".equals(infoId))||"law".equals(infoType)||"law_data".equals(infoType)) {
                //点击的是虚拟节点数据 并且是法律法规数据 应该查询该节点下所有的法律法规文件
                //点击的是法律法规分类 并且是法律法规数据 应该查询该节点下所有的法律法规文件
                params.put("infoType","law_data");
                page=classifyDesicService.queryListByTask(params);
                page.setRemarks("law");

            }
            else if (("sham".equals(infoType) && "stu_90004".equals(infoId))||"stu_pic".equals(infoType)||"stu_pic_data".equals(infoType)) {
                //点击的是学习管理虚拟节点图文 或者点击图文下的分类
                params.put("infoType","stu_pic_data");
                page= mediaService.listStuByTask(params);
                page.setRemarks("stu_pic");
            }
            else if (("sham".equals(infoType) && "stu_90003".equals(infoId))||"stu_audio".equals(infoType)||"stu_audio_data".equals(infoType)) {
                //点击的是学习管理虚拟节点音频 或者点击音频下的分类
                params.put("infoType","stu_audio_data");
                page= mediaService.listStuByTask(params);
                page.setRemarks("stu_audio");
            }
            else if (("sham".equals(infoType) && "stu_90002".equals(infoId))||"stu_video".equals(infoType)||"stu_video_data".equals(infoType)) {
                //点击的是学习管理虚拟节点视频 或者点击视频下的分类
                params.put("infoType","stu_video_data");
                page= mediaService.listStuByTask(params);
                page.setRemarks("stu_video");
            }else if (("sham".equals(infoType) && "case_90008".equals(infoId))||"case_pic".equals(infoType)||"case_pic_data".equals(infoType)) {
                //点击的是案例分析虚拟节点图文 或者点击图文下的分类
                params.put("infoType","case_pic_data");
                page= analysisService.listCaseAnaByUser(params);
                page.setRemarks("case_pic");
            }
            else if (("sham".equals(infoType) && "case_90007".equals(infoId))||"case_audio".equals(infoType)||"case_audio_data".equals(infoType)) {
                //点击的是案例分析虚拟节点音频 或者点击音频下的分类
                params.put("infoType","case_audio_data");
                page= analysisService.listCaseAnaByUser(params);
                page.setRemarks("case_audio");
            }
            else if (("sham".equals(infoType) && "case_90006".equals(infoId))||"case_video".equals(infoType)||"case_video_data".equals(infoType)) {
                //点击的是案例分析虚拟节点视频 或者点击视频下的分类
                params.put("infoType","case_video_data");
                page= analysisService.listCaseAnaByUser(params);
                page.setRemarks("case_video");
            }
            else if (("sham".equals(infoType) && "case_90005".equals(infoId))) {
                //点击的是案例分析虚拟节点
                params.put("infoType","");
                page= analysisService.listCaseAnaByUser(params);
                page.setRemarks("case_sham");
            }*/

        }

        return page;
    }

    @Override
    public PageUtils queryContentByUser(Map<String, Object> params) {
        PageUtils page= null;
        if(UtilValidate.isNotEmpty(params)) {
            String infoType = (String) params.get("infoType");

            if ("law".equals(infoType)){
                //查询法律法规相关数据
                page=classifyDesicService.queryListByUser(params);
            }
            else  {
                //点击的是学习管理和案例分析模块
                page= mediaService.listStuByUser(params);
            }
        }
        return page;
    }

    @Override
    public int countTask(Map<String, Object> params) {
        //数据权限使用
        String userId=(String)params.get("userId");
        User user = userService.selectUserByUserId(userId);
        EntityWrapper<LearnTasksEntity> ew = new EntityWrapper<>();
        //需要权限过滤
        String[] authArr= authService.listAllIdByUser(user.getOrgId(),user.getId(),"LEARNTASK");
        if(authArr.length>0){
            ew.in("id",authArr);
        }else{
            String[] arr={"0"};
            ew.in("id",arr);
        }

        ew.eq("is_use","1");
        int count = mapper.selectCount(ew);
        return count;
    }
}
