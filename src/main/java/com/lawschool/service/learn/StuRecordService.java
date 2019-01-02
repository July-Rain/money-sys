package com.lawschool.service.learn;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.StuRecordEntity;

/**
 * InterfaceName: StuRecordService
 * Description: 学习记录表service
 * date: 2018-12-29 9:30
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface StuRecordService extends IService<StuRecordEntity> {
    int insertStuRecord(User user, String stuId, String stuType,String stuFrom,String ... taskId);
}
