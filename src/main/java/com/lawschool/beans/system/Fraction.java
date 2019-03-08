package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;
import com.lawschool.enums.Source;

import javax.persistence.Table;
import java.util.List;

/**
 * ClassName: Fraction
 * Description: 积分学分规则
 * date: 2019/3/79:57
 *
 * @author 王帅奇
 */
@TableName("LAW_FRACTION")
public class Fraction extends DataEntity<Fraction> {


    private String fractionType;    //分数类型  积分1  学分0

    private Source source;     //数据来源

    private Integer queNum;//题目数量要求  大于多少题  or   守擂成功次数

    private Double dailyLimit;  //每日上限

    private Double score ;  //分数  or  额外奖励分数

    private Double minScore;  //小关分数 or 失败分数

    private Double  maxScore;   //大关分数 or 胜利分数

    private Double minDemand; //最低要求  考试学分、练习中心时为正确率  学习中心收听收看时长

    @TableField(exist = false)
    private List<FractionRules> fractionRulesList;

    public String getFractionType() {
        return fractionType;
    }

    public void setFractionType(String fractionType) {
        this.fractionType = fractionType;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Integer getQueNum() {
        return queNum;
    }

    public void setQueNum(Integer queNum) {
        this.queNum = queNum;
    }

    public Double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Double getMinDemand() {
        return minDemand;
    }

    public void setMinDemand(Double minDemand) {
        this.minDemand = minDemand;
    }

    public List<FractionRules> getFractionRulesList() {
        return fractionRulesList;
    }

    public void setFractionRulesList(List<FractionRules> fractionRulesList) {
        this.fractionRulesList = fractionRulesList;
    }
}
