package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.UserQuestRecord;

/**
 * 
 * @Descriptin   做过的题目保存
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/28
 *
 */
public interface UserQuestRecordService extends IService<UserQuestRecord> {
    int CheckpointByUser(String uid);

    int OnlinByUser(String uid);

    int leitaiByUser(String uid);

    int chuangguanCorrectBydept(String deptcode);

    int OnlinCorrectBydept(String deptcode);

    int leitaiCorrectBydept(String deptcode);
}
