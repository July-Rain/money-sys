package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.dao.StuMediaMapper;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.auth.AuthRelationService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StuMediaServiceImpl extends ServiceImpl<StuMediaMapper,StuMedia> implements StuMediaService {

    @Autowired
    private StuMediaMapper mapper;

    @Autowired
    private AuthRelationService authService;

    //我的收藏-重点课程 -zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        int count=mapper.cntMyCollection(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("endPage",pageNo*pageSize);

        List<StuMedia> stuMedias = mapper.listMyCollection(param);

        PageUtils page=new PageUtils(stuMedias,count,pageSize,pageNo);

        return page;
    }


    @Override
    public StuMedia getStuMedia(StuMedia stuMedia) {
        return mapper.selectById(stuMedia.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 插入学习管理  加权限
     * @Date 15:35 2018-12-5
     * @Param [stuMedia]
     * @return int
     **/
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertStuMedia(StuMedia stuMedia) {
        //存学习管理基本信息
        stuMedia.setId(GetUUID.getUUIDs("SM"));
        mapper.insert(stuMedia);
        //存权限表
        String[] deptIdArr=stuMedia.getDeptId();
        String[] userIdArr=stuMedia.getUserId();
        authService.insertAuthRelation(deptIdArr,userIdArr,stuMedia.getId(),"STUMEDIA",stuMedia.getOptuser());
    }
    
    /**
     * @Author MengyuWu
     * @Description 查询学习的详情数据包括适用人员和部门信息
     * @Date 16:37 2018-12-5
     * @Param [id]
     * @return com.lawschool.beans.StuMedia
     **/
    

    @Override
    public StuMedia selectStuMediaInfo(String id) {
        //获取学习实体
        StuMedia stuMedia = mapper.selectById(id);
        if(UtilValidate.isNotEmpty(stuMedia)){
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
                stuMedia.setUserId(userIdArr);
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
                stuMedia.setUserId(deptIdArr);
            }

        }
        return stuMedia;
    }

}
