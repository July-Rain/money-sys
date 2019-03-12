package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.Integral;
import com.lawschool.beans.User;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.beans.learn.TasksUserEntity;
import com.lawschool.beans.system.Fraction;
import com.lawschool.dao.learn.StuRecordDao;
import com.lawschool.dao.system.FractionDao;
import com.lawschool.enums.Source;
import com.lawschool.service.IntegralService;
import com.lawschool.service.law.TaskDesicService;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.service.learn.TasksUserService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: StuRecordServiceImpl
 * Description: 学习记录impl
 * date: 2018-12-29 9:30
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class StuRecordServiceImpl extends ServiceImpl<StuRecordDao,StuRecordEntity> implements StuRecordService {
    @Autowired
    private TaskDesicService desicService;
    @Autowired
    private TasksUserService tasksUserService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private FractionDao dao;

    @Override
    public int insertStuRecord(User user, String stuId, String stuType,String stuFrom,String taskId) {
        //判断是否有学习记录   没有的话新增
        EntityWrapper<StuRecordEntity> ew =new EntityWrapper<StuRecordEntity>();
        if(UtilValidate.isNotEmpty(stuId)){
            ew.eq("stu_id",stuId);
        }
        if(UtilValidate.isNotEmpty(stuType)){
            ew.eq("stu_type",stuType);
        }
        if(UtilValidate.isNotEmpty(stuFrom)){
            ew.eq("stu_from",stuFrom);
        }
        if(UtilValidate.isNotEmpty(taskId)){
            ew.eq("task_id",taskId);
            //根据任务id 更新用户学习状态
            updateUseFinish(user, taskId);
        }
        StuRecordEntity recordEntity= this.selectOne(ew);
        if(UtilValidate.isEmpty(recordEntity)){
            StuRecordEntity stuRecordEntity = new StuRecordEntity();
            stuRecordEntity.setCreateTime(new Date());
            stuRecordEntity.setStuTime(new Date());
            stuRecordEntity.setUserId(user.getId());
            stuRecordEntity.setStuId(stuId);
            stuRecordEntity.setStuType(stuType);
            stuRecordEntity.setId(GetUUID.getUUIDs("SR"));
            stuRecordEntity.setCreateUser(user.getId());
            stuRecordEntity.setUserName(user.getUserName());
            stuRecordEntity.setStuFrom(stuFrom);
            if(UtilValidate.isNotEmpty(taskId)){
                stuRecordEntity.setTaskId(taskId);
            }
            return baseMapper.insert(stuRecordEntity);
        }
        return 0;

    }
    @Override
    public int insertStuRecordNormal(User user, String stuId, String stuType,String stuFrom,String taskId) {
        //判断是否有学习记录   没有的话新增
        EntityWrapper<StuRecordEntity> ew =new EntityWrapper<StuRecordEntity>();
        if(UtilValidate.isNotEmpty(stuId)){
            ew.eq("stu_id",stuId);
        }
        if(UtilValidate.isNotEmpty(stuType)){
            ew.eq("stu_type",stuType);
        }
        if(UtilValidate.isNotEmpty(stuFrom)){
            ew.eq("stu_from",stuFrom);
        }
        if(UtilValidate.isNotEmpty(taskId)){
            ew.eq("task_id",taskId);
            //根据任务id 更新用户学习状态
            updateUseFinish(user, taskId);
        }
        StuRecordEntity recordEntity= this.selectOne(ew);
        if(UtilValidate.isEmpty(recordEntity)){
            StuRecordEntity stuRecordEntity = new StuRecordEntity();
            stuRecordEntity.setCreateTime(new Date());
            stuRecordEntity.setStuTime(new Date());
            stuRecordEntity.setUserId(user.getId());
            stuRecordEntity.setStuId(stuId);
            stuRecordEntity.setStuType(stuType);
            stuRecordEntity.setId(GetUUID.getUUIDs("SR"));
            stuRecordEntity.setCreateUser(user.getId());
            stuRecordEntity.setUserName(user.getUserName());
            stuRecordEntity.setStuFrom(stuFrom);
            if(UtilValidate.isNotEmpty(taskId)){
                stuRecordEntity.setTaskId(taskId);
            }
            return baseMapper.insert(stuRecordEntity);
        }else{
            recordEntity.setStuCount(1);
            return baseMapper.updateById(recordEntity);
        }
    }

    @Override
    public int countTime(String stuId, String stuFrom, BigDecimal playTime,Boolean finishFlag,User user,String taskId,String type) {
        StuRecordEntity recordEntity= this.selectOne(new EntityWrapper<StuRecordEntity>().eq("stu_id",stuId).eq("stu_from",stuFrom));
        if(UtilValidate.isNotEmpty(recordEntity)){
            if(finishFlag){
                //看完一个视频统计量
                recordEntity.setStuCount(1);
            }
            BigDecimal time = recordEntity.getStuCountTime();
            time = time.add(playTime);
            recordEntity.setStuCountTime(time);
            baseMapper.updateById(recordEntity);
            Source source = null;
            if("audio".equals(type)){
                //音频课堂
                source=Source.AUDIOSTUDY;
            }else if("video".equals(type)){
                //音频课堂
                source=Source.VIDEOSTUDY;
            }else if("pic".equals(type)){
                //音频课堂
                source=Source.PICSTUDY;
            }
            updateUserIntegral(stuFrom,taskId,playTime.divide(new BigDecimal(60)),user,source);
        }

        return 0;
    }

    @Override
    public int updateUseFinish(User user, String taskId) {
        TasksUserEntity userEntity = tasksUserService.selectOne(new EntityWrapper<TasksUserEntity>().eq("user_id",user.getId()).eq("task_id",taskId));
        if(UtilValidate.isNotEmpty(userEntity)&&"0".equals(userEntity.getIsFinish())){
            //获取当前登陆人的学习记录条数
            int stuRecordCount = baseMapper.selectCount(new EntityWrapper<StuRecordEntity>().eq("user_id",user.getId()).eq("task_id",taskId));
            int taskDataCount = desicService.selectCount(new EntityWrapper<TaskDesicEntity>().eq("task_id",taskId).like("info_type","_data"));
            if(stuRecordCount+1>=taskDataCount){
                //说明用户已完成 更新状态和时间
                userEntity.setIsFinish("1");
                userEntity.setFinishTime(new Date());
                tasksUserService.updateById(userEntity);
                //完成后添加学分积分 -- 2019 03 12  学完所有任务内容后+4分，其中视频、音频需至少完整播放一遍，图文类型任务浏览相关界面不少于4分钟。每个任务不重复加分。
                Fraction fraction = dao.findByTypeAndSource("1", Source.STUTASK);
                Integral integral = new Integral();

                integral.setSrc("learntask");
                integral.setType("1");
                integral.setPoint(fraction.getScore());
                integralService.addIntegralRecord(integral,user );

            }
        }
        return 0;
    }

    @Override
    public int updateUserIntegral(String stuFrom, String taskId, BigDecimal playTime, User user, Source source) {
        if(UtilValidate.isNotEmpty(taskId)){
            //如果任务id不为空，则说明是学习任务中的学习记录  则不计入积分
           return  0;
        }
        //查询各个模块的积分情况
        Fraction fraction = dao.findByTypeAndSource("1", source);
        Float minDemand=fraction.getMinDemand();//最小时长
        if(playTime.compareTo(new BigDecimal(minDemand))>1){
            //如果播放时间大于最小时长则可以计入积分
            Float point = fraction.getScore();//获取分数
            Float dailyLimit = fraction.getDailyLimit();//每日上线
            Integral integral = new Integral();
            integral.setUserId(user.getId());
            integral.setSrc("stu");
            integral.setType("1");
            Float nowPoint = integralService.sumPointByUser(integral);
            Integral newIntegral = new Integral();
            //如果当天已经添加了积分大于上限则取上限减当前积分
            if(point+nowPoint>dailyLimit){
                newIntegral.setPoint(dailyLimit-nowPoint>=0?dailyLimit-nowPoint:0);
            }else{
                newIntegral.setPoint(point);
            }
            if(newIntegral.getPoint()>0){
                newIntegral.setType("1");
                newIntegral.setSrc("src");
                integralService.addIntegralRecord(newIntegral,user);
            }

        }
        return 0;
    }
}
