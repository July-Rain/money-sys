package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.law.CaseAnalysisEntity;
import com.lawschool.dao.law.CaseAnalysisDao;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.service.law.CaseAnalysisService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageUtils queryPage(Map<String,Object> params){
        String caseTitle = (String)params.get("caseTitle");
        String caseProcess = (String)params.get("caseProcess");
        String lawLevel = (String)params.get("lawLevel");
        String caseType = (String)params.get("caseType");
        String contentType = (String)params.get("contentType");
        String caseLawid = (String)params.get("caseLawid");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        EntityWrapper<CaseAnalysisEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,CASE_TITLE,CASE_CONTENT,CONTENT_TYPE,CASE_TIME,CASE_PROCESS,CASE_TYPE,LAW_LEVEL,VIDEO_PIC_ACC");
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
        if(UtilValidate.isNotEmpty(startTime)){
            ew.addFilter("(create_time >= TO_DATE('"+startTime+"', 'yyyy-mm-dd'))");
        }
        if(UtilValidate.isNotEmpty(endTime)){
            ew.addFilter("(create_time <= TO_DATE('"+endTime+"', 'yyyy-mm-dd'))");
        }

        ew.orderBy("CREATE_TIME");
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
        analysisEntity.setCreateTime(new Date());
        analysisEntity.setCaseCode(GetUUID.getUUIDs("CA"));
        mapper.insert(analysisEntity);
        //存权限表
        String[] deptIdArr=analysisEntity.getDeptArr();
        String[] userIdArr=analysisEntity.getUserArr();
        authService.insertAuthRelation(deptIdArr,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getCreateUser());

    }

    @Override
    public CaseAnalysisEntity selectCaseAnalyInfo(String id) {
        //获取学习实体
        CaseAnalysisEntity caseAnalysisEntity = mapper.selectById(id);
        if(UtilValidate.isNotEmpty(caseAnalysisEntity)){
            //获取适用人的id
            List<AuthRelationBean> userAuth = authService
                    .selectList(new EntityWrapper<AuthRelationBean>()
                            .setSqlSelect("auth_id")
                            .eq("function_flag","STUMEDIA")
                            .eq("function_id",id).eq("auth_type","user"));
            if(UtilValidate.isNotEmpty(userAuth)){
                //把适用人id放在实体中
                String [] userIdArr= new String[userAuth.size()] ;
                for(int i=0;i<userAuth.size();i++){
                    userIdArr[i]=userAuth.get(i).getAuthId();
                }
                caseAnalysisEntity.setUserArr(userIdArr);
            }
            //获取适用部门的id
            List<AuthRelationBean> deptAuth = authService
                    .selectList(new EntityWrapper<AuthRelationBean>()
                            .setSqlSelect("auth_id")
                            .eq("function_flag","STUMEDIA")
                            .eq("function_id",id).eq("auth_type","dept"));
            if(UtilValidate.isNotEmpty(deptAuth)){
                String [] deptIdArr= new String[userAuth.size()] ;
                for(int i=0;i<userAuth.size();i++){
                    deptIdArr[i]=userAuth.get(i).getAuthId();
                }
                caseAnalysisEntity.setDeptArr(deptIdArr);
            }

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
        authService.insertAuthRelation(deptIdArr,userIdArr,analysisEntity.getId(),"CASEANALYSIS",analysisEntity.getOptUser());

    }
}
