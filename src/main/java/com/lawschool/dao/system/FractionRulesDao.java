package com.lawschool.dao.system;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.FractionRules;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterfaceName: FractionRulesDao
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
public interface FractionRulesDao extends AbstractDao<FractionRules> {

    /**
     * 根据分值设置主表ID查
     * @param fractionId
     * @return
     */
    List<FractionRules> findListByFracId(@Param("fractionId") String fractionId);
}
