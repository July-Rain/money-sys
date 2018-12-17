package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.competition.MatchSetting;

import java.util.List;


public interface MatchSettingDao extends BaseMapper<MatchSetting> {

    public List<MatchSetting> list();
}
