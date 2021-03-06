package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.dao.law.CaseAnalysisDao;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.law.CaseAnalysisService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CaseAnalysisServiceImpl
 * Description: 案例分析的impl
 * date: 2018-12-22 14:54
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class CaseAnalysisServiceImpl extends ServiceImpl<CaseAnalysisDao,CaseAnalysisEntity> implements CaseAnalysisService {

    @Autowired
    private CaseAnalysisDao mapper;

    @Autowired
    private AuthRelationService authService;
    public PageUtils queryPage(Map<String,Object> params,User user){
        String caseTitle = (String)params.get("caseTitle");
        String caseProcess = (String)params.get("caseProcess");
        String lawLevel = (String)params.get("lawLevel");
        String caseType = (String)params.get("caseType");
        String contentType = (String)params.get("contentType");
        String caseLawid = (String)params.get("caseLawid");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String createUser=(String)params.get("createUser");
        EntityWrapper<CaseAnalysisEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,CASE_TITLE,CASE_CONTENT,CONTENT_TYPE,CASE_TIME,DICTCODE2VALE(CASE_PROCESS) as CASE_PROCESS,DICTCODE2VALE(CASE_TYPE) as CASE_TYPE,DICTCODE2VALE(LAW_LEVEL) as LAW_LEVEL,VIDEO_PIC_ACC,CREATE_TIME,CREATE_USER_NAME,IS_OPEN");
        if(UtilValidate.isNotEmpty(caseTitle)){
            ew.like("CASE_TITLE",caseTitle);
        }
        if(UtilValidate.isNotEmpty(caseProcess)){
            ew.eq("CASE_PROCESS",caseProcess);
        }
        if(UtilValidate.isNotEmpty(lawLevel)){
            ew.eq("LAW_LEVEL",lawLevel);
        }
        if(UtilValidate.isNotEmpty(contentType)){
            ew.eq("content_type",contentType);
        }
        if(UtilValidate.isNotEmpty(caseType)){
            ew.eq("CASE_TYPE",caseType);
        }
        if(UtilValidate.isNotEmpty(caseLawid)){
            String[] lawArr=caseLawid.split(",");
            ew.in("CASE_LAWID",lawArr);
        }
        if(UtilValidate.isNotEmpty(createUser)){  //创建人
            if(user.getIsAdmin()==1){
                //是超级管理员  可以看到所有人的学习管理信息
            }else{
                ew.eq("CREATE_USER",createUser);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(UtilValidate.isNotEmpty(startTime)){
            Date startParse = null;
            try {
                startParse = sdf.parse(startTime);
                ew.ge("case_time", startParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(endTime)){
            Date endParse = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Format f = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(format.parse(endTime));
                c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
                Date tomorrow = c.getTime();
                endParse = tomorrow;
                //以开始时间
                ew.le("case_time", endParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }
        if(UtilValidate.isNotEmpty(params.get("isAuth"))){
            //需要权限过滤
            String[] authArr= authService.listAllIdByUser(user.getOrgId(),user.getId(),"CASEANALYSIS");
            if(authArr.length>0){
                ew.andNew().eq("is_open","1").or().in("id",authArr);
                //ew.in("id",authArr);
            }else{
                String[] arr={"0"};
                ew.andNew().eq("is_open","1").or().in("id",arr);
            }
        }

        ew.orderBy("CREATE_TIME",false);
        Page<CaseAnalysisEntity> page = this.selectPage(
                new Query<CaseAnalysisEntity>(params).getPage(),ew);
        return new PageUtils(page);
    }

    @Override
    public void insertCaseAnaly(CaseAnalysisEntity analysisEntity, User user) {
        //存案例分析基本信息

        analysisEntity.setOptUser(user.getId());
        analysisEntity.setOptTime(new Date());
        analysisEntity.setCreateUser(user.getId());
        analysisEntity.setCreateUserName(user.getUserName());
        analysisEntity.setCreateTime(new Date());
        analysisEntity.setCaseCode(GetUUID.getUUIDs("CA"));
        analysisEntity.setIsOpen("0");
        mapper.insert(analysisEntity);
        //存权限表
        String[] deptIdArr=analysisEntity.getDeptArr();
        String[] userIdArr=analysisEntity.getUserArr();
        String[] deptTemp=new String[1];
        if(UtilValidate.isNotEmpty(deptIdArr)&&UtilValidate.isNotEmpty(userIdArr)&&deptIdArr.length==0&&userIdArr.length==0){
            deptTemp[0]=user.getOrgId();
            authService.insertAuthRelation(deptTemp,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getCreateUser());
        }else{
            authService.insertAuthRelation(deptIdArr,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getCreateUser());
        }
    }

    @Override
    public CaseAnalysisEntity selectCaseAnalyInfo(String id) {
        //获取学习实体
        CaseAnalysisEntity caseAnalysisEntity = mapper.selectById(id);
        if(UtilValidate.isNotEmpty(caseAnalysisEntity)){
            //获取适用人的id
            String [] userIdArr= authService.getUserIdArr(id,"CASEANALYSIS") ;
            caseAnalysisEntity.setUserArr(userIdArr);
            //获取适用部门的id
            String [] deptIdArr= authService.getDeptIdArr(id,"CASEANALYSIS") ;
            caseAnalysisEntity.setDeptArr(deptIdArr);


        }
        return caseAnalysisEntity;
    }

    @Override
    public void updateCaseAnaly(CaseAnalysisEntity analysisEntity, User user) {
        analysisEntity.setCreateUser(user.getId());
        analysisEntity.setCreateTime(new Date());
        mapper.updateById(analysisEntity);
        authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag","CASEANALYSIS").eq("function_Id",analysisEntity.getId()));
        //存权限表
        String[] deptIdArr=analysisEntity.getDeptArr();
        String[] userIdArr=analysisEntity.getUserArr();
        String[] deptTemp=new String[1];
        if(UtilValidate.isNotEmpty(deptIdArr)&&UtilValidate.isNotEmpty(userIdArr)&&deptIdArr.length==0&&userIdArr.length==0){
            deptTemp[0]=user.getOrgId();
            authService.insertAuthRelation(deptTemp,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getCreateUser());
        }else{
            authService.insertAuthRelation(deptIdArr,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getCreateUser());
        }
    }

    @Override
    public PageUtils listCaseAnaByTask(Map<String, Object> params) {
        Page<CaseAnalysisEntity> page = new Page<CaseAnalysisEntity>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));
        String infoType=(String)params.get("infoType");
        String taskId=(String)params.get("taskId");
        String infoId=(String)params.get("infoId");
        TaskDesicEntity taskDesicEntity = new TaskDesicEntity();
        taskDesicEntity.setInfoType(infoType);
        taskDesicEntity.setTaskId(taskId);
        taskDesicEntity.setInfoId(infoId);
        String userId=(String)params.get("userId");
        taskDesicEntity.setUserId(userId);
        page.setRecords(mapper.listCaseAnaByTask(page,taskDesicEntity));
        page.setTotal(mapper.countListCaseAnaByTask(taskDesicEntity));
        return new PageUtils(page);
    }

    @Override
    public PageUtils listCaseAnaByUser(Map<String, Object> params) {
        Page<TaskDesicEntity> page = new Page<TaskDesicEntity>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));
        String infoType=(String)params.get("infoType");
        String taskId=(String)params.get("taskId");
        String infoId=(String)params.get("infoId");
        String dataId=(String)params.get("dataId");
        String infoName=(String)params.get("infoName");
        TaskDesicEntity taskDesicEntity = new TaskDesicEntity();
        taskDesicEntity.setInfoType(infoType);
        taskDesicEntity.setTaskId(taskId);
        taskDesicEntity.setInfoId(infoId);
        taskDesicEntity.setDataId(dataId);
        taskDesicEntity.setInfoName(infoName);
        String userId=(String)params.get("userId");
        taskDesicEntity.setUserId(userId);
        page.setRecords(mapper.listCaseAnaByUser(page,taskDesicEntity));
        page.setTotal(mapper.countListCaseAnaByUser(taskDesicEntity));
        return new PageUtils(page);
    }

    @Override
    public int updateCount(String stuId) {
        CaseAnalysisEntity caseAna = new CaseAnalysisEntity();
        caseAna.setContentCount(1);
        caseAna.setId(stuId);
        return baseMapper.updateById(caseAna);
    }

    @Override
    public int updateOpen(String caseId, User user) {
        CaseAnalysisEntity caseAna = new CaseAnalysisEntity();
        caseAna.setId(caseId);
        caseAna.setIsOpen("1");
        caseAna.setOpenTime(new Date());
        caseAna.setOpenUser(user.getId());
        return mapper.updateById(caseAna);
    }
}
