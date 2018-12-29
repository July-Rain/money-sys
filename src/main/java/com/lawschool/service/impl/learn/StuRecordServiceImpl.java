package com.lawschool.service.impl.learn;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.learn.StuRecordEntity;
import com.lawschool.dao.learn.StuRecordDao;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;

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
    @Override
    public int insertStuRecord(User user, String stuId, String stuType,String stuFrom,String ... taskId) {
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
            stuRecordEntity.setTaskId(taskId.toString());
        }
        return baseMapper.insert(stuRecordEntity);
    }
}
