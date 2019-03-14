package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.FractionRules;
import com.lawschool.dao.system.FractionRulesDao;
import com.lawschool.service.system.FractionRulesSerivce;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: FractionRulesSerivceImpl
 * Description: TODO
 * date: 2019/3/715:18
 *
 * @author 王帅奇
 */
@Service
public class FractionRulesSerivceImpl extends AbstractServiceImpl<FractionRulesDao, FractionRules> implements FractionRulesSerivce {

    @Override
    public List<FractionRules> findListByFracId(String fractionId) {
        return dao.findListByFracId(fractionId);
    }

    @Override
    public void deleteByFractionId(String fractionId) {
         dao.deleteByFractionId(fractionId);
    }
}
