package com.lawschool.dao.exam;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.UserExamAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface UserExamAnswerDao extends AbstractDao<UserExamAnswer> {
    UserExamAnswer findByuEIdAndQueId(@Param("userExamId") String userExamId, @Param("qId") String qId);

    void updateByIds(@Param("userAnsId")String userAnsId,@Param("userScore")double userScore,@Param("id")String id);

    List<String> getQueType(@Param("userExamId")String userExamId);

    List<UserExamAnswer>  findByuEIdAndQueType(@Param("userExamId")String userExamId,@Param("queType") String queType);

    CopyOnWriteArrayList<String> getListByDiffScore(@Param("score") int score);

    void updateCheckScoreById(@Param("userAnsId")String userAnsId,@Param("userScore")double userScore,@Param("id")String id);

    float getScoreByUserExamId(@Param("userExamId") String userExamId);
    float getScoreExAudit(@Param("userExamId") String userExamId);
}
