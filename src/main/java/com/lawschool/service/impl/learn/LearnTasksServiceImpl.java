package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.dao.StuMediaDao;
import com.lawschool.dao.learn.LearnTasksDao;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.law.CaseAnalysisService;
import com.lawschool.service.law.ClassifyDesicService;
import com.lawschool.service.law.TaskDesicService;
import com.lawschool.service.learn.LearnTasksService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ClassifyDesicService classifyDesicService;

    @Autowired
    private StuMediaService mediaService;

    @Autowired
    private CaseAnalysisService analysisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLearnTask(LearnTasksEntity learnTask, User user) {
        //存学习任务基本信息

        learnTask.setOptUser(user.getId());
        learnTask.setOptTime(new Date());
        learnTask.setCreateUser(user.getId());
        learnTask.setCreateTime(new Date());
        mapper.insert(learnTask);
        //存权限表
        String[] deptIdArr=learnTask.getDeptArr();
        String[] userIdArr=learnTask.getUserArr();
        authService.insertAuthRelation(deptIdArr,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getCreateUser());
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
    public PageUtils queryPage(Map<String, Object> params) {
        String taskName = (String)params.get("taskName");
        String taskContent = (String)params.get("taskContent");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String createUser=(String)params.get("createUser");//创建人
        EntityWrapper<LearnTasksEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,TASK_NAME,START_TIME,END_TIME,TASK_CONTENT,CREATE_USER");
        if(UtilValidate.isNotEmpty(taskName)){
            ew.like("TASK_NAME",taskName);
        }
        if(UtilValidate.isNotEmpty(taskContent)){
            ew.like("TASK_CONTENT",taskContent);
        }
        if(UtilValidate.isNotEmpty(startTime)){
            ew.addFilter("(START_TIME >= TO_DATE('"+startTime+"', 'yyyy-mm-dd'))");
        }
        if(UtilValidate.isNotEmpty(endTime)){
            ew.addFilter("(END_TIME <= TO_DATE('"+endTime+"', 'yyyy-mm-dd'))");
        }

        if(UtilValidate.isNotEmpty(createUser)){  //创建人
            ew.eq("CREATE_USER",createUser);
        }

        ew.orderBy("CREATE_TIME");
        Page<LearnTasksEntity> page = this.selectPage(
                new Query<LearnTasksEntity>(params).getPage(),ew);
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLearnTask(LearnTasksEntity learnTask, User user) {
        learnTask.setCreateUser(user.getId());
        learnTask.setCreateTime(new Date());
        mapper.updateById(learnTask);
        authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag","LEARNTASK").eq("function_Id",learnTask.getId()));
        //存权限表
        String[] deptIdArr=learnTask.getDeptArr();
        String[] userIdArr=learnTask.getUserArr();
        authService.insertAuthRelation(deptIdArr,userIdArr,learnTask.getId(),"LEARNTASK",learnTask.getOptUser());

    }

    @Override
    public PageUtils queryContentByTask(Map<String, Object> params) {
        PageUtils page= null;
        if(UtilValidate.isNotEmpty(params)) {
            String infoType = (String) params.get("infoType");
            String infoId = (String) params.get("infoId");
            String taskId = (String) params.get("taskId");
            if (("sham".equals(infoType) && "law_90001".equals(infoId))||"law".equals(infoType)||"law_data".equals(infoType)) {
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
                page= analysisService.listCaseAnaByTask(params);
                page.setRemarks("case_pic");
            }
            else if (("sham".equals(infoType) && "case_90007".equals(infoId))||"case_audio".equals(infoType)||"case_audio_data".equals(infoType)) {
                //点击的是案例分析虚拟节点音频 或者点击音频下的分类
                params.put("infoType","case_audio_data");
                page= analysisService.listCaseAnaByTask(params);
                page.setRemarks("case_audio");
            }
            else if (("sham".equals(infoType) && "case_90006".equals(infoId))||"case_video".equals(infoType)||"case_video_data".equals(infoType)) {
                //点击的是案例分析虚拟节点视频 或者点击视频下的分类
                params.put("infoType","case_video_data");
                page= analysisService.listCaseAnaByTask(params);
                page.setRemarks("case_video");
            }
            else if (("sham".equals(infoType) && "case_90005".equals(infoId))) {
                //点击的是案例分析虚拟节点
                params.put("infoType","");
                page= analysisService.listCaseAnaByTask(params);
                page.setRemarks("case_sham");
            }

        }

        return page;
    }

   /* public String[] listAllLawIdbyparent(String lawid){
        String lawIds=lawid;
        String [] lawArr=null;

        if(UtilValidate.isNotEmpty(lawid)){

        }
        if(UtilValidate.isNotEmpty(lawIds)){
            lawArr=lawIds.split(",");
        }
        return lawArr;
    }*/
}
