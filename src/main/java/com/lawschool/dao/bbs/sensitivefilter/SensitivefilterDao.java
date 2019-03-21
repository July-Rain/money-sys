package com.lawschool.dao.bbs.sensitivefilter;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.bbs.sensitivefilter.SensitivefilterEntity;
import com.lawschool.beans.competition.BattleRecord;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SensitivefilterDao extends BaseMapper<SensitivefilterEntity> {
    public Set<String> getAll();
}
