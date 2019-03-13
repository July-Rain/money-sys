package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.Integral;
import com.lawschool.beans.vo.CompetitionStatisticsByDeptVo;
import com.lawschool.beans.vo.OrgCompetitionVO;

import java.util.List;
import java.util.Map;

public interface IntegralDao  extends BaseMapper<Integral> {

    int chuangguanCountByUser(String uid);
    int pkByUser(String uid);
    int leitaiByUser(String uid);

    List<CompetitionStatisticsByDeptVo> userByDeptList(Page page, Map<String, Object> params);
    int userByDeptListCount(Map<String, Object> params);

    List<OrgCompetitionVO> orgDiaStat(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 根据当前登陆人信息 统计当天某个类型下的积分或者学分的总和
     * @Date 10:13 2019-3-12
     * @Param [integral]
     * @return java.lang.Float
     **/

    Float sumPointByUser(Integral integral);
}