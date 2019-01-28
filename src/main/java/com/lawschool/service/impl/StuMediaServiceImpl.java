package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.auth.AuthRelationBean;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
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

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lawschool.util.Constant.ERROR;
import static com.lawschool.util.Constant.SUCCESS;
import static java.lang.Integer.parseInt;

@Service
public class StuMediaServiceImpl extends AbstractServiceImpl<StuMediaDao,StuMedia> implements StuMediaService {

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
        int pageNo= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        long pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());

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
        stuMedia = mapper.selectById(stuMedia.getId());

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

        stuMedia.setOptUser(user.getId());
        stuMedia.setOptTime(new Date());
        stuMedia.setCreateUser(user.getId());
        stuMedia.setCreateTime(new Date());
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
        authService.insertAuthRelation(deptIdArr,userIdArr,stuMedia.getId(),"STUMEDIA",stuMedia.getCreateUser());
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
            String [] userIdArr= authService.getUserIdArr(id,"STUMEDIA") ;
            stuMedia.setUserArr(userIdArr);
            //获取适用部门的id
            String [] deptIdArr= authService.getDeptIdArr(id,"STUMEDIA") ;
            stuMedia.setDeptArr(deptIdArr);

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
    public PageUtils queryPage(Map<String, Object> params,User user) {
        String stuTitle = (String)params.get("stuTitle");
        String comContent = (String)params.get("comContent");
        String stuPoliceclass = (String)params.get("stuPoliceclass");
        String stuLawid = (String)params.get("stuLawid");
        String stuType = (String)params.get("stuType");
        String stuIssuer = (String)params.get("stuIssuer");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String createUser=(String)params.get("createUser");//创建人
        String addsrc=(String)params.get("addsrc");//添加来源  0-其他  1-教官中心
        EntityWrapper<StuMedia> ew = new EntityWrapper<>();
        ew.setSqlSelect("ID,STU_CODE,STU_TITLE,COM_CONTENT,STU_TYPE,STU_COUNT,STU_ISSUER,STU_ISSTIME,STU_POLICECLASS,DICTCODE2VALE(STU_POLICECLASS) as stuPoliceclassName,VIDEO_PIC_ACC");
        if(UtilValidate.isNotEmpty(stuTitle)){
            ew.like("stu_title",stuTitle);
        }
        if(UtilValidate.isNotEmpty(comContent)){
            ew.like("com_content",comContent);
        }
        if(UtilValidate.isNotEmpty(stuPoliceclass)){
            ew.eq("stu_policeclass",stuPoliceclass);
        }
        if(UtilValidate.isNotEmpty(stuType)){
            ew.eq("stu_type",stuType);
        }
        if(UtilValidate.isNotEmpty(stuLawid)){
            String[] lawArr=stuLawid.split(",");
            ew.in("stu_lawid",lawArr);
        }
        if(UtilValidate.isNotEmpty(stuIssuer)){
            ew.like("stu_issuer",stuIssuer);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(UtilValidate.isNotEmpty(startTime)){
            Date startParse = null;
            try {
                startParse = sdf.parse(startTime);
                ew.ge("stu_isstime", startParse);
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
                //以开始时间搞
                ew.le("stu_isstime", endParse);
            } catch (ParseException e) {
                throw new RuntimeException();
            }
        }

        if(UtilValidate.isNotEmpty(createUser)){  //创建人
            ew.eq("CREATE_USER",createUser);
        }
        if(UtilValidate.isNotEmpty(addsrc)){  //添加来源
            ew.eq("ADDSRC",addsrc);
        }
        if(UtilValidate.isNotEmpty(params.get("isAuth"))){
            //需要权限过滤
           String[] authArr= authService.listAllIdByUser(user.getOrgId(),user.getId(),"STUMEDIA");
           if(authArr.length>0){
               ew.in("id",authArr);
           }else{
               String[] arr={"0"};
               ew.in("id",arr);
           }
        }

        ew.orderBy("STU_ISSTIME",false);
        //Page<StuMedia> page = new Page<StuMedia>();
       /* Page<StuMedia> page = new Page<StuMedia>(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));

        page.setRecords(mapper.selectListPage(
                page));
        page.setTotal(mapper.selectCount(new EntityWrapper<>()));*/
        Page<StuMedia> page = this.selectPage(
                new Query<StuMedia>(params).getPage(),ew);
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
        stuMedia.setCreateUser(user.getId());
        stuMedia.setCreateTime(new Date());
        mapper.updateById(stuMedia);
        authService.delete(new EntityWrapper<AuthRelationBean>().eq("function_flag","STUMEDIA").eq("function_Id",stuMedia.getId()));
        //存权限表
        String[] deptIdArr=stuMedia.getDeptArr();
        String[] userIdArr=stuMedia.getUserArr();
        authService.insertAuthRelation(deptIdArr,userIdArr,stuMedia.getId(),"STUMEDIA",stuMedia.getOptUser());

    }

    @Override
    public PageUtils listStuByTask(Map<String, Object> params) {
        Page page = new Page(Integer.parseInt(params.get("currPage").toString()),Integer.parseInt(params.get("pageSize").toString()));
        String infoType=(String)params.get("infoType");
        String taskId=(String)params.get("taskId");
        String infoId=(String)params.get("infoId");
        TaskDesicEntity taskDesicEntity = new TaskDesicEntity();
        taskDesicEntity.setInfoType(infoType);
        taskDesicEntity.setTaskId(taskId);
        taskDesicEntity.setInfoId(infoId);
        String userId=(String)params.get("userId");
        taskDesicEntity.setUserId(userId);
        page.setRecords(mapper.listStuByTask(page,taskDesicEntity));
        page.setTotal(mapper.countListStuByTask(taskDesicEntity));
        return new PageUtils(page);
    }

    @Override
    public int updateCount(String stuId) {
        StuMedia stuMedia = new StuMedia();
        stuMedia.setStuCount(1);
        stuMedia.setId(stuId);
        return baseMapper.updateById(stuMedia);
    }

    public boolean updateStatus(String id, String status){

        return dao.updateStatus(id, status);
    }
}
