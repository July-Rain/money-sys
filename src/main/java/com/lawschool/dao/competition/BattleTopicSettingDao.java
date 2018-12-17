package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.competition.BattleTopicSetting;

import java.util.List;

public interface BattleTopicSettingDao extends BaseMapper<BattleTopicSetting> {

    List<BattleTopicSetting> selectListByBaBaId(String id);
}
