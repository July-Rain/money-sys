package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.Fraction;
import com.lawschool.enums.Source;
import com.lawschool.util.Result;

import java.util.List;

/**
 * InterfaceName: FractionService
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
public interface FractionService extends AbstractService<Fraction> {

    /**
     * 获取所有积分配置规则
     * @return
     */
    Result getFraction();

    /**
     * 保存积分配置规则
     * @param fractionList
     * @throws Exception
     */
    void saveFraction(List<Fraction> fractionList) throws Exception;

    /**
     * 更新
     * @param fractionList
     * @throws Exception
     */
    void updateFraction(List<Fraction> fractionList) throws Exception;

    /**
     *根据类型和数据源查询
     * @param fractionType
     * @param source
     * @return
     */
    Result getFractionByType(String fractionType, Source source);
}
