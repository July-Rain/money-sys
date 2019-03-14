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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Result result = Result.ok();
        List<Fraction> fractionList = dao.findAll();
        Map<String, Object> fractionMap = new HashMap<>();
        for (Fraction fraction : fractionList) {
            if (Source.EXAM.equals(fraction.getSource()) && "1".equals(fraction.getFractionType())) {
                fraction.setFractionRulesList(fractionRulesSerivce.findListByFracId(fraction.getId()));
            }
            switch (fraction.getSource()) {
                case AUDIOSTUDY:
                    fractionMap.put("audioStudy", fraction);
                    break;
                case VIDEOSTUDY:
                    fractionMap.put("videoStudy", fraction);
                    break;
                case PICSTUDY:
                    fractionMap.put("picStudy", fraction);
                    break;
                case STUTASK:
                    fractionMap.put("stuTask", fraction);
                    break;
                case DAILYQUE:
                    fractionMap.put("dailyQue", fraction);
                    break;
                case GROUPPRAC:
                    fractionMap.put("groupPrac", fraction);
                    break;
                case OTHERPRAC:
                    fractionMap.put("otherPrac", fraction);
                    break;
                case EXAM:
                    if (Fraction.EXAM_INTEGRAL.equals(fraction.getFractionType())) {
                        fractionMap.put("examIntegral", fraction);
                    } else {
                        fractionMap.put("examCredits", fraction);
                    }
                    break;
                case RECRUIT:
                    fractionMap.put("recruit", fraction);
                    break;
                case COMPEITIONONLINE:
                    fractionMap.put("competitionOnline", fraction);
                    break;
                case MATCH:
                    fractionMap.put("match", fraction);
                    break;
                default:
                    break;
            }
        }
        return result.put("fractionMap", fractionMap);
    }

    @Override
    public void saveFraction(List<Fraction> fractionList) {
        for (Fraction fraction : fractionList) {
            if (StringUtils.isNotEmpty(fraction.getId())) {
                if (Source.EXAM.equals(fraction.getSource()) && "1".equals(fraction.getFractionType())) {
                    fractionRulesSerivce.deleteByFractionId(fraction.getId());
                    saveExamFraction(fraction);
                }
                setDataEntity(fraction);
                dao.updateById(fraction);
            } else {
                fraction.setId(GetUUID.getUUIDs("FT"));
                if (Source.EXAM.equals(fraction.getSource()) && "1".equals(fraction.getFractionType())) {
                    saveExamFraction(fraction);
                }
                setDataEntity(fraction);
                dao.insert(fraction);
            }
        }
    }

    @Override
    public Result getFractionByType(String fractionType, Source source) {
        if (UtilValidate.isEmpty(fractionType) || UtilValidate.isEmpty(source)) {
            return Result.error("类型和来源都不能为空");
        }
        Fraction fraction = dao.findByTypeAndSource(fractionType, source);
        if (Source.EXAM.equals(fraction.getSource()) && "1".equals(fraction.getFractionType())) {
            fraction.setFractionRulesList(fractionRulesSerivce.findListByFracId(fraction.getId()));
        }
        return Result.ok().put("fraction", fraction);
    }

    private void saveExamFraction(Fraction fraction) {
        List<FractionRules> fractionRulesList = fraction.getFractionRulesList();

        if (UtilValidate.isNotEmpty(fractionRulesList)) {
            for (FractionRules fractionRules : fractionRulesList) {
                fractionRules.setId(GetUUID.getUUIDs("FTR"));
                fractionRules.setFractionId(fraction.getId());
            }
            fractionRulesSerivce.insertBatch(fractionRulesList);
        }

    }

}
