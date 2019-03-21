package com.lawschool.service.bbs.sensitivefilter;


import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.bbs.sensitivefilter.SensitivefilterEntity;
import com.lawschool.beans.competition.BattleRecord;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @Descriptin
 * @author      孙小康
 * @version     v1.0
 * @Time
 *
 */
public interface SensitivefilterService extends IService<SensitivefilterEntity> {


     Set<String> getAll();

}
