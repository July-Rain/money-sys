package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.SysConfig;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.dao.StuMediaDao;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.auth.AuthRelationService;
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

import static com.lawschool.util.Constant.ERROR;
import static com.lawschool.util.Constant.SUCCESS;
import static java.lang.Integer.parseInt;

@Service
public class StuMediaServiceImpl extends ServiceImpl<StuMediaDao,StuMedia> implements StuMediaService {

    @Autowired
    private StuMediaDao mapper;

    @Autowired
    private AuthRelationService authService;

    @Autowired
    private UserMapper userMapper;

    /**
     * @Author zjw
     * @Description 我的收藏-重点课程(已经下架的也展示出来了，有需要再xml里改)
     * @Date 9:18 2018-12-6
     * @Param [param]
     * @return com.lawschool.util.PageUtils
    **/
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


    /**
     * @Author zjw
     * @Description 课程详情（基础）
     * @Date 9:44 2018-12-6
     * @Param [stuMedia]
     * @return com.lawschool.beans.StuMedia
    **/
    @Override
    public StuMedia getStuMedia(StuMedia stuMedia) {
        StuMedia stuMedia1 = mapper.selectById(stuMedia.getId());

        return stuMedia;
    }

    /**
     * @Author MengyuWu
     * @Description 插入学习管理  加权限  //教官添加课程复用
     * @Date 15:35 2018-12-5
     * @Param [stuMedia]
     * @return int
     **/
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertStuMedia(StuMedia stuMedia,User user) {
        //存学习管理基本信息

        stuMedia.setOptuser(user.getId());
        stuMedia.setOpttime(new Date());
        stuMedia.setStuCode(GetUUID.getUUIDs("SC"));
        stuMedia.setStuCount(0);
        stuMedia.setStuCreat(new Date());
        stuMedia.setStuIssuer(user.getUserName());
        stuMedia.setStuIssdepartment(user.getOrgName());
        stuMedia.setStuIsstime(new Date());
        mapper.insert(stuMedia);
        //存权限表
        String[] deptIdArr=stuMedia.getDeptArr();
        String[] userIdArr=stuMedia.getUserArr();
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
                stuMedia.setUserArr(userIdArr);
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
                stuMedia.setDeptArr(deptIdArr);
            }

        }
        return stuMedia;
    }

    /**
     * @Author zjw
     * @Description 获取指定教官的课程
     * @Date 9:17 2018-12-6
     * @Param [id]
     * @return java.util.List<com.lawschool.beans.StuMedia>
     **/
    @Override
    public List<StuMedia> selectTchMedia(Map<String,Object> param,User user) {
        int pageNo = 1;
        int pageSize = 10;
        if (UtilValidate.isNotEmpty(param.get("pageNo"))) {
            pageNo = parseInt((String) param.get("pageNo"));
        }
        if (UtilValidate.isNotEmpty(param.get("pageSize"))) {
            pageSize =parseInt((String) param.get("pageSize"));
        }
        Page<StuMedia> page=new Page<StuMedia>(pageNo,pageSize);
        if(user.getIdentify().equals("1")){//是否是教官的身份
            return mapper.selectPage(page,new EntityWrapper<StuMedia>().eq("OPTUSER",user.getId()).eq("ADDSRC",1).eq("DEL_STATUS",0));
        }
        return  null;
    }

    /**
     * @Author zjw
     * @Description 教官删除自己的课程
     * @Date 15:37 2018-12-6
     * @Param [stuMedia, user]
     * @return java.util.List<com.lawschool.beans.StuMedia>
     **/
    @Override
    public int delTchMedia(StuMedia stuMedia, User user) {

        Wrapper<StuMedia> ew = new EntityWrapper<StuMedia>().eq("OPTUSER", user.getId()).eq("DEL_STATUS", 0).eq("ID",stuMedia.getId());
        StuMedia temp =new StuMedia();
        int update = mapper.update(temp, ew);
        return update==1?SUCCESS:ERROR;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String stuTitle = (String)params.get("stuTitle");
        String comContent = (String)params.get("comContent");
        String stuPoliceclass = (String)params.get("stuPoliceclass");
        String stuType = (String)params.get("stuType");
        EntityWrapper<StuMedia> ew = new EntityWrapper<>();

        if(UtilValidate.isNotEmpty(stuTitle)){
            ew.like("stu_title",stuTitle);
        }
        if(UtilValidate.isNotEmpty(comContent)){
            ew.like("com_content",comContent);
        }
        if(UtilValidate.isNotEmpty(stuPoliceclass)){
            ew.like("stu_policeclass",stuPoliceclass);
        }
        if(UtilValidate.isNotEmpty(stuType)){
            ew.eq("stu_type",stuType);
        }
        Page<StuMedia> page = this.selectPage(
                new Query<StuMedia>(params).getPage(),ew);
       /* List<StuMedia> stuList = page.getRecords();
        for(StuMedia stu : stuList){
            if("3".equals(stu.getStuType())){

            }
        }*/
        return new PageUtils(page);
    }

    /**
     * @Author MengyuWu
     * @Description 学习管理修改
     * @Date 17:08 2018-12-13
     * @Param [stuMedia, user]
     * @return void
     **/
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStuMedia(StuMedia stuMedia, User user) {
        mapper.updateById(stuMedia);
        authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag","STUMEDIA").eq("function_Id",stuMedia.getId()));
        //存权限表
        String[] deptIdArr=stuMedia.getDeptArr();
        String[] userIdArr=stuMedia.getUserArr();
        authService.insertAuthRelation(deptIdArr,userIdArr,stuMedia.getId(),"STUMEDIA",stuMedia.getOptuser());

    }
}
