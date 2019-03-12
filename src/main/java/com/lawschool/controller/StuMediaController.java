package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.service.IntegralService;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stumedia")
public class StuMediaController extends AbstractController {

    @Autowired
    private StuMediaService stuMediaService;

    @Autowired
    private StuRecordService recordService;



    /**
     * @Author zjw
     * @Description 当前登录人收藏的课程
     * @Date 14:38 2018-12-6
     * @Param [params]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/mycollection")
    public Result listMyCollection(@RequestParam Map<String,Object> params){
        params.put("userId",getUser().getId());
        PageUtils pageUtils = stuMediaService.listMyCollection(params);
        return Result.ok().put("result",pageUtils);
    }

    /**
     * @Author zjw
     * @Description 获取课件的基本详情
     * @Date 14:38 2018-12-6
     * @Param [stuMedia]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/getStuMedia")
    public Result getStuMedia(@RequestBody StuMedia stuMedia){
        StuMedia stuMedia1 = stuMediaService.getStuMedia(stuMedia);
        return Result.ok().put("info",stuMedia1);
    }
    /**
     * @Author MengyuWu
     * @Description 新增课程信息
     * @Date 11:58 2018-12-6
     * @Param [stuMedia]
     * @return com.lawschool.util.Result
     **/
    @SysLog("添加课程")
    @RequestMapping("/insertStuMedia")
    public Result insertStuMedia(@RequestBody StuMedia stuMedia){
//        User user=getUser();
//        if(user.getIdentify().equals("1")){//教官
//            stuMedia.setId(GetUUID.getUUIDs("TM"));
//        }else{
//            stuMedia.setId(GetUUID.getUUIDs("SM"));
//        }
        stuMedia.setId(GetUUID.getUUIDs("SM"));
        stuMediaService.insertStuMedia(stuMedia,getUser());
        return Result.ok().put("id",stuMedia.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 修改课程
     * @Date 15:53 2018-12-19
     * @Param [stuMedia]
     * @return com.lawschool.util.Result
     **/
    
    @SysLog("修改课程")
    @RequestMapping("/updateStuMedia")
    public Result updateStuMedia(@RequestBody StuMedia stuMedia){
        //User user=getUser();
        stuMediaService.updateStuMedia(stuMedia,getUser());
        return Result.ok().put("id",stuMedia.getId());
    }


    /**
     * @Author zjw
     * @Description 获取某教官的课程
     *      * @Date 14:39 2018-12-6
     * @Param []
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("/listTM")
    public Result listTM(@RequestParam Map<String,Object> params){

        List<StuMedia> stuMedias = stuMediaService.selectTchMedia(params,getUser());
        if(UtilValidate.isEmpty(stuMedias)){
            return Result.error("身份错误或数据为空");
        }
        return Result.ok().put("list",stuMedias);
    }

    /**
     * @Author MengyuWu
     * @Description 课程列表查询
     * @Date 14:01 2018-12-8
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params){
        params.put("isAuth",true);
        PageUtils page = stuMediaService.queryPage(params,getUser());
        return Result.ok().put("page", page);
    }
    /**
     * @Author MengyuWu
     * @Description 获取我创建的数据
     * @Date 10:20 2019-1-16
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/listICreate")
    public Result listICreate(@RequestParam Map<String,Object> params){
        User user=getUser();
        params.put("createUser",user.getId());
        PageUtils page = stuMediaService.queryPage(params,getUser());
        return Result.ok().put("page", page);
    }

    /**
     * @Author MengyuWu
     * @Description 获取详情信息
     * @Date 15:52 2018-12-19
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/info")
    public Result info(String id){
        StuMedia stuMedia = stuMediaService.selectStuMediaInfo(id);
        return Result.ok().put("data",stuMedia);
    }


    /**
     * @Author MengyuWu
     * @Description 删除课程
     * @Date 15:53 2018-12-19
     * @Param [ids]
     * @return com.lawschool.util.Result
     **/
    
    @SysLog("删除课程")
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] ids){
        stuMediaService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * @Author MengyuWu
     * @Description 根据附件id更新播放量
     * * @Date 10:14 2018-12-28
     * @Param [accId]
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("/updateCount")
    public Result updateCount(String stuId, String stuType, String stuFrom, String taskId, BigDecimal playTime){
        //获取当前登陆人
        User user=  getUser();
        //更新
        stuMediaService.updateCount(stuId );
        //插入学习记录
        recordService.insertStuRecord(user,stuId,stuType,stuFrom,taskId);


        return Result.ok();
    }
    /**
     * @Author MengyuWu
     * @Description 根据学习id更新播放时长
     * * @Date 10:14 2018-12-28
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    @RequestMapping("/countTime")
    public Result countTime(String stuId,String stuFrom,BigDecimal playTime,Boolean finishFlag,String taskId,String type){
        recordService.countTime(stuId,stuFrom,playTime,finishFlag,getUser(),taskId,type);
        return Result.ok();
    }
}
