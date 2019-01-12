package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.Date;

/**
 * 随机练习答题记录
 * @author xuxiang
 * @date 2018/12/4 9:51
 */
@TableName("LAW_EXERCISE_RECORD")
public class ExerciseAnswerRecordEntity extends AnswerRecordEntity<ExerciseAnswerRecordEntity> {

    private static final long serialVersionUID = 1885637237834092218L;
}
