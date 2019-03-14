package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2019/3/11 17:51
 * @Description: 组卷练习试卷信息
 */
@TableName("LAW_PAPER_PAPER")
public class PaperRecordEntity extends DataEntity<PaperRecordEntity> {
    private String configureId;// 配置ID
    private String questId;// 题目ID

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }
}
