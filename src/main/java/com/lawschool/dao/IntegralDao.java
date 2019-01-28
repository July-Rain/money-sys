package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.Integral;
import com.lawschool.beans.vo.CompetitionStatisticsByDeptVo;
import com.lawschool.beans.vo.OrgCompetitionVO;

import java.util.List;
import java.util.Map;

public interface IntegralDao  extends AbstractDao<Integral> {

    int chuangguanCountByUser(String uid);
    int pkByUser(String uid);
    int leitaiByUser(String uid);

    List<CompetitionStatisticsByDeptVo> userByDeptList(Page page, Map<String, Object> params);
    int userByDeptListCount(Map<String, Object> params);

    List<OrgCompetitionVO> orgDiaStat(Map<String,String> param);
}