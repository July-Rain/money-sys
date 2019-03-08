package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.system.Fraction;
import com.lawschool.beans.system.FractionRules;
import com.lawschool.dao.system.FractionDao;
import com.lawschool.enums.Source;
import com.lawschool.service.system.FractionRulesSerivce;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.GetUUID;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: FractionServiceImpl
 * Description: TODO
 * date: 2019/3/711:52
 *
 * @author 王帅奇
 */
@Service
public class FractionServiceImpl extends AbstractServiceImpl<FractionDao, Fraction> implements FractionService {

    @Autowired
    private FractionRulesSerivce fractionRulesSerivce;

    @Override
    public Result getFraction() {
        List<Fraction> fractionList = dao.findAll();
        for (Fraction fraction :fractionList){
            if (Source.EXAM.equals(fraction.getSource())&&"1".equals(fraction.getFractionType())) {
                fraction.setFractionRulesList(fractionRulesSerivce.findListByFracId(fraction.getId()));
            }
        }
        return Result.ok().put("fractionList",fractionList);
    }

    @Override
    public void saveFraction(List<Fraction> fractionList) throws Exception {
        for ( Fraction fraction : fractionList){
            fraction.setId(GetUUID.getUUIDs("FT"));
           if (Source.EXAM.equals(fraction.getSource())&&"1".equals(fraction.getFractionType())){
                saveExamFraction(fraction);
           }
           setDataEntity(fraction);
           dao.insert(fraction);
        }
    }

    @Override
    public void updateFraction(List<Fraction> fractionList) throws Exception {
        for ( Fraction fraction : fractionList){
            if (Source.EXAM.equals(fraction.getSource())&&"1".equals(fraction.getFractionType())){
                fractionRulesSerivce.deleteByFractionId(fraction.getId());
                saveExamFraction(fraction);
            }
            setDataEntity(fraction);
            dao.updateById(fraction);
        }

    }

    @Override
    public Result getFractionByType(String fractionType, Source source) {
        if (UtilValidate.isEmpty(fractionType) ||UtilValidate.isEmpty(source) ){
            return Result.error("类型和来源都不能为空");
        }
        Fraction fraction = dao.findByTypeAndSource(fractionType,source);
        if(Source.EXAM.equals(fraction.getSource())&&"1".equals(fraction.getFractionType())){
            fraction.setFractionRulesList(fractionRulesSerivce.findListByFracId(fraction.getId()));
        }
        return Result.ok().put("fraction",fraction);
    }

    private void saveExamFraction(Fraction fraction) throws Exception{
        List<FractionRules> fractionRulesList = fraction.getFractionRulesList();
        double[] minArr = new double[fractionRulesList.size()];
        double[] maxArr = new double[fractionRulesList.size()];
        if (UtilValidate.isNotEmpty(fractionRulesList)){
            for ( int i=0;i<fractionRulesList.size();i++ ){
                minArr[i] = fractionRulesList.get(i).getRightRateMin();
                maxArr[i] = fractionRulesList.get(i).getRightRateMax();
                fractionRulesList.get(i).setId(GetUUID.getUUIDs("FTR"));
                fractionRulesList.get(i).setFractionId(fraction.getId());
            }
            Arrays.sort(minArr);
            Arrays.sort(maxArr);
            for (int i=0; i<minArr.length-1;i++ ){
                if (maxArr[i]>minArr[i+1]){
                    throw new Exception("错错错");
                }
            }
        }
        fractionRulesSerivce.insertBatch(fractionRulesList);
    }

}
