package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @Auther: Moon
 * @Date: 2018/12/21 14:09
 * @Description: 练习任务答题记录
 */
@TableName("LAW_PAPER_RECORD")
public class PaperAnswerRecordEntity extends AnswerRecordEntity<PaperAnswerRecordEntity> {

    private static final long serialVersionUID = 919650639023234839L;
}
