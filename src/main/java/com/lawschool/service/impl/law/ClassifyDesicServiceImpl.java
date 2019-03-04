package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.dao.law.ClassifyDesicDao;
import com.lawschool.dao.law.TaskDesicDao;
import com.lawschool.service.law.ClassifyDesicService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ClassifyDesicServiceImpl
 * Description: 法律法规Impl
 * date: 2018-12-25 11:21
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class ClassifyDesicServiceImpl extends ServiceImpl<ClassifyDesicDao,ClassifyDesicEntity> implements ClassifyDesicService {
    @Autowired
    private ClassifyDesicDao desicDao;
    @Override
    public PageUtils queryListByTask(Map<String, Object> params) {
        Page<ClassifyDesicEntity> page = new Page<ClassifyDesicEntity>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));
        String infoType=(String)params.get("infoType");
        String taskId=(String)params.get("taskId");
        String infoId=(String)params.get("infoId");
        String userId=(String)params.get("userId");
        TaskDesicEntity taskDesicEntity = new TaskDesicEntity();
        taskDesicEntity.setInfoType(infoType);
        taskDesicEntity.setTaskId(taskId);
        taskDesicEntity.setInfoId(infoId);
        taskDesicEntity.setUserId(userId);
        page.setRecords(desicDao.queryListByTask(page,taskDesicEntity));
        page.setTotal(desicDao.countListByTask(taskDesicEntity));
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        String classifyId = (String)param.get("classifyId");
        String libId = (String)param.get("libId");
        String status = (String)param.get("status");
        String lawTitle = (String)param.get("lawTitle");
        String issueOrg = (String)param.get("issueOrg");
        EntityWrapper<ClassifyDesicEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,LAW_CODE,LAW_TITLE,ISSUE_TIME,LIB_ID,CLASSIFY_ID,ISSUE_ORG,STATUS");
        if(UtilValidate.isNotEmpty(classifyId)){
            ew.like("classify_id",classifyId);
        }
        if(UtilValidate.isNotEmpty(libId)){
            ew.eq("lib_id",libId);
        }
        if(UtilValidate.isNotEmpty(status)){
            ew.eq("status",status);
        }if(UtilValidate.isNotEmpty(lawTitle)){
            ew.like("law_title",lawTitle);
        }
        if(UtilValidate.isNotEmpty(issueOrg)){
            ew.like("issue_org",issueOrg);
        }

        ew.orderBy("ISSUE_TIME",false);
        Page<ClassifyDesicEntity> page = this.selectPage(
                new Query<ClassifyDesicEntity>(param).getPage(),ew);
        return new PageUtils(page);
    }
}
