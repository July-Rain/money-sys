package com.lawschool.dao.competition;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.competition.BattlePlatform;
import org.springframework.stereotype.Component;

@Component
public interface BattlePlatformDao extends BaseMapper<BattlePlatform> {

    public int PkCountByUser(String uid);

    public int leitaiCountByUser(String uid);
}
