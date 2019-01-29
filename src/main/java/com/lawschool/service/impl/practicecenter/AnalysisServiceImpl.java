package com.lawschool.service.impl.practicecenter;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.practicecenter.AnalysisEntity;
import com.lawschool.dao.practicecenter.AnalysisDao;
import com.lawschool.service.practicecenter.AnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: Moon
 * @Date: 2019/1/10 19:17
 * @Description:
 */
@Service
public class AnalysisServiceImpl extends AbstractServiceImpl<AnalysisDao, AnalysisEntity>
        implements AnalysisService {

    public Map<String, Object> analysis(String month, String userId){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 查询的结果以练习类型排序
        List<Map<String, Object>> mapList = dao.analysis(month, userId);

        /******** 定义返回的参数 ********/
        // 总分析，key为练习类型、value为次数
        Map<String, Integer> overall = new HashMap<String, Integer>();
        // 主题回答数
        Map<String, Integer> themeAnswerNum = new HashMap<String, Integer>();
        // 主题正确数
        Map<String, Integer> themeRightNum = new HashMap<String, Integer>();
        // 总回答数
        int total = 0;
        // 总正确数
        int right = 0;
        // 总练习次数
        int sum = 0;

        // 处理返回格式
        String key = "";
        // 用于统计练习次数
        Set<String> idSet = new HashSet<String>();

        for(Map<String, Object> temp : mapList){
            String typeName = (String)temp.get("typeName");
            if(StringUtils.isNotBlank(key)){
                if(!key.equals(typeName)){
                    // key与typeName不同，说明某一类型练习已处理完毕
                    overall.put(key, idSet.size());
                    sum += idSet.size();

                    // 初始化容器
                    key = typeName;
                    idSet = new HashSet<String>();
                }
            } else {
                key = typeName;
            }


            String taskId = (String)temp.get("id");// 任务ID

            BigDecimal answer = (BigDecimal)temp.get("answerNum");
            BigDecimal rig = (BigDecimal)temp.get("rightNum");

            Integer answerNum = answer.intValue();// 回答数
            Integer rightNum = rig.intValue();// 正确数
            String themeName = (String)temp.get("themeName");// 主题类型

            idSet.add(taskId);

            total += answerNum;
            right += rightNum;

            // 已有该主题的数据，累加
            if(themeAnswerNum.get(themeName) != null){
                themeAnswerNum.put(themeName, themeAnswerNum.get(themeName) + answerNum);
                themeRightNum.put(themeName, themeRightNum.get(themeName) + rightNum);
            } else {
                themeAnswerNum.put(themeName, answerNum);
                themeRightNum.put(themeName, rightNum);
            }

        }

        // 最后的练习类型信息
        overall.put(key, idSet.size());
        sum += idSet.size();

        resultMap.put("overall", overall);
        resultMap.put("themeAnswerNum", themeAnswerNum);
        resultMap.put("themeRightNum", themeRightNum);
        resultMap.put("total", total);
        resultMap.put("right", right);
        resultMap.put("sum", sum);

        return resultMap;
    }
}
