package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.ManuscriptEntity;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.TestQuestions;
import com.lawschool.beans.User;
import com.lawschool.dao.ManuscriptDao;
import com.lawschool.service.ManuscriptService;
import com.lawschool.service.StuMediaService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.util.GetUUID;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ManuscriptServiceImpl
 * @Author wangtongye
 * @Date 2018/12/27 17:29
 * @Versiom 1.0
 **/
@Service
public class ManuscriptServiceImpl extends AbstractServiceImpl<ManuscriptDao, ManuscriptEntity> implements ManuscriptService {

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private StuMediaService stuMediaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mySave(ManuscriptEntity entity){
        String sourceId = "";

        // 根据类型保存到对应的数据库
        if(ManuscriptEntity.FILE_TYPE_QUE == entity.getType()){// 试题
            // 试题
            TestQuestions test = entity.getTest();
            if(test == null){
                throw new RuntimeException("试题信息错误...");
            }

            testQuestionService.mySave(test);
            sourceId = test.getId();

        } else if(ManuscriptEntity.FILE_TYPE_STU == entity.getType()){// 学习
            // 学习任务
            StuMedia stu = entity.getStu();
            if(stu == null){
                throw new RuntimeException("学习任务信息错误...");
            }
            stu.setDelStatus(1);
            stu.setStruts("1");
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            stu.setId(GetUUID.getUUIDs("SM"));
            stuMediaService.insertStuMedia(stu, user);
            sourceId = stu.getId();

        } else {
            throw new RuntimeException("类型错误...");
        }

        entity.setSourceId(sourceId);
        // 待审核
        entity.setStatus(ManuscriptEntity.STATUS_WAIT);

        return this.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean myDelete(String id){
        ManuscriptEntity entity = dao.findOne(id);
        Integer type = entity.getType();
        String sourceId = entity.getSourceId();

        dao.deleteById(id);
        if(type == 0){// 习题
            testQuestionService.deleteById(sourceId);
        } else {
            stuMediaService.deleteById(sourceId);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean examine(String id, String type,String userid,String opinion){

        ManuscriptEntity entity = dao.findOne(id);
        Integer sourceType = entity.getType();
        String sourceId = entity.getSourceId();
//        type  0是 不通过   1是通过
        Integer status = 0;
        if("0".equals(type)){
            status = ManuscriptEntity.STATUS_FAIL;
            if(sourceType == 0){// 习题

            } else {
                // 学习
                stuMediaService.updateStatus2(sourceId, "2");
            }


        } else {
            status = ManuscriptEntity.STATUS_PASS;
            // 更新对应的习题或者学习


            if(sourceType == 0){// 习题
                testQuestionService.updateStatus(sourceId, "0");
            } else {
                // 学习
                stuMediaService.updateStatus2(sourceId, "3");
            }

        }

        return dao.updateExamine(id, status, userid,opinion);
    }
}
