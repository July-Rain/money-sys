package com.lawschool.service.learn;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.enums.Source;

import java.math.BigDecimal;

/**
 * InterfaceName: StuRecordService
 * Description: 学习记录表service
 * date: 2018-12-29 9:30
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface StuRecordService extends IService<StuRecordEntity> {
    /**
     * @Author MengyuWu
     * @Description 添加学习时长 -- 视频音频数据
     * @Date 15:00 2019-1-10
     * @Param [user, stuId, stuType, stuFrom, taskId]
     * @return int
     **/
    
    int insertStuRecord(User user, String stuId, String stuType,String stuFrom,String taskId);

    /**
     * @Author MengyuWu
     * @Description 添加学习时长 -- 普通数据
     * @Date 15:00 2019-1-10
     * @Param [user, stuId, stuType, stuFrom, taskId]
     * @return int
     **/

    int insertStuRecordNormal(User user, String stuId, String stuType,String stuFrom,String taskId);


    /**
     * @Author MengyuWu
     * @Description 叠加时长
     * @Date 11:31 2019-3-12
     * @Param [stuId, stuFrom, playTime, finishFlag, user, taskId, type]
     * @return int
     **/

    

    int countTime(String stuId,String stuFrom, BigDecimal playTime,Boolean finishFlag,User user,String taskId,String type);

    /**
     * @Author MengyuWu
     * @Description 更新用户的学习状态
     * @Date 19:20 2019-1-10
     * @Param [user, taskId]
     * @return int
     **/
    
    int updateUseFinish(User user,String taskId);

    /**
     * @Author MengyuWu
     * @Description 根据学习记录添加积分来源分析
     * @Date 9:59 2019-3-12
     * @Param [stuFrom, taskId, playTime, user]
     * @return int
     **/
    
    int updateUserIntegral(String stuFrom,String taskId,BigDecimal playTime,User user, Source source);

}
