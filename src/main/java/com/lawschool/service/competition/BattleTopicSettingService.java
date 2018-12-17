package com.lawschool.service.competition;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.competition.BattleTopicSetting;

import java.util.List;

/**
 * 
 * @Descriptin  对战题目配置service接口
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
public interface BattleTopicSettingService extends IService<BattleTopicSetting> {

    public List<BattleTopicSetting> selectListByBaBaId(String id);
}
