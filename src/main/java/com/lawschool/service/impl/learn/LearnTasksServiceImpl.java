package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.learn.LearnTasksEntity;
import com.lawschool.dao.StuMediaDao;
import com.lawschool.dao.learn.LearnTasksDao;
import com.lawschool.service.auth.AuthRelationService;
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

    }

    @Override
    public LearnTasksEntity selectLearnTask(String id) {
        //获取学习实体
        LearnTasksEntity learnTasksEntity = mapper.selectById(id);
        if(UtilValidate.isNotEmpty(learnTasksEntity)){
            //获取适用人的id
            List<AuthRelationBean> userAuth = authService
                    .selectList(new EntityWrapper<AuthRelationBean>()
                            .setSqlSelect("auth_id")
                            .eq("function_flag","LEARNTASK")
                            .eq("function_id",id).eq("auth_type","user"));
            if(UtilValidate.isNotEmpty(userAuth)){
                //把适用人id放在实体中
                String [] userIdArr= new String[userAuth.size()] ;
                for(int i=0;i<userAuth.size();i++){
                    userIdArr[i]=userAuth.get(i).getAuthId();
                }
                learnTasksEntity.setUserArr(userIdArr);
            }
            //获取适用部门的id
            List<AuthRelationBean> deptAuth = authService
                    .selectList(new EntityWrapper<AuthRelationBean>()
                            .setSqlSelect("auth_id")
                            .eq("function_flag","LEARNTASK")
                            .eq("function_id",id).eq("auth_type","dept"));
            if(UtilValidate.isNotEmpty(deptAuth)){
                String [] deptIdArr= new String[userAuth.size()] ;
                for(int i=0;i<userAuth.size();i++){
                    deptIdArr[i]=userAuth.get(i).getAuthId();
                }
                learnTasksEntity.setDeptArr(deptIdArr);
            }

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
}
