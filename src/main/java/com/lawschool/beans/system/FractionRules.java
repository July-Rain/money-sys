package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * ClassName: FractionRules
 * Description: 分数获取详细规则
 * date: 2019/3/710:31
 *
 * @author 王帅奇
 */
@TableName("LAW_FRACTION_RULES")
public class FractionRules extends DataEntity<FractionRules> {

    private String fractionId;  //分数规则id

    private Float rightRateMin;    //正确率最小要求 或者浏览最低要求

    private Float rightRateMax;    //正确率最大要求

    private Float intervalScore ;      //此区间获得分数

    public String getFractionId() {
        return fractionId;
    }

    public void setFractionId(String fractionId) {
        this.fractionId = fractionId;
    }

    public Float getRightRateMin() {
        return rightRateMin;
    }

    public void setRightRateMin(Float rightRateMin) {
        this.rightRateMin = rightRateMin;
    }

    public Float getRightRateMax() {
        return rightRateMax;
    }

    public void setRightRateMax(Float rightRateMax) {
        this.rightRateMax = rightRateMax;
    }

    public Float getIntervalScore() {
        return intervalScore;
    }

    public void setIntervalScore(Float intervalScore) {
        this.intervalScore = intervalScore;
    }
}
