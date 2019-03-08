package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.FractionRules;

import java.util.List;
/**
 * InterfaceName: FractionRulesSerivce
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
public interface FractionRulesSerivce extends AbstractService<FractionRules> {

    /**
     * 根据分数规则主表ID查
     * @param fractionId
     * @return
     */
    List<FractionRules> findListByFracId(String fractionId);

    /**
     * 根据分数规则主表ID删除
     * @param fractionId
     */
    void deleteByFractionId(String fractionId);
}
