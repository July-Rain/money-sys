package com.lawschool.dao.system;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.Fraction;
import com.lawschool.enums.Source;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterfaceName: FractionDao
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
public interface FractionDao extends AbstractDao<Fraction> {

    /**
     *查全部
     * @return
     */
    List<Fraction> findAll();

    /**
     *根据类型和原查
     * @param fractionType
     * @param source
     * @return
     */
    Fraction findByTypeAndSource(@Param("fractionType") String fractionType,@Param("source") Source source);
}
