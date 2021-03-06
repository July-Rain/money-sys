package com.lawschool.dao.exam;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.beans.vo.CompetitionStatisticsByDeptVo;
import com.lawschool.form.UserExamForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName: UserExamDao
 * Description: TODO
 * date: 2018/12/2910:21
 *
 * @author 王帅奇
 */
public interface UserExamDao extends AbstractDao<UserExam> {

    List<String> getQueIdList(@Param("examDetailId") String examDetailId);

    String getExamDetailId(@Param("examConfigId") String examConfigId);

    void updateFinMarkAndScoreById(@Param("isFinMark")String isFinMark,@Param("totalScore") double totalScore,
                                   @Param("examStatus") String examStatus,@Param("endScore") float endScore,
                                   @Param("remainingExamTime") Double remainingExamTime,
                                   @Param("commitTime") Date commitTime,@Param("id")String id);

    List<UserExamForm> getList(Page page, Map<String, Object> params);

    int getListCount(Map<String, Object> params);

    void updateRemainTimeById(@Param("remainingExamTime") Double remainingExamTime,
                              @Param("examStatus") String examStatus,@Param("id")  String id);

    CopyOnWriteArrayList<String> getListByExamConId(String examConfigId);

    List<UserExamForm> getListByIds(@Param("ids") List<String> ids);

    List<String> getAuditList(@Param("examConfigId")String examConfigId ,@Param("status") String status);

    boolean updateCollect(@Param("id") String id, @Param("type") Integer type);

    /**
     * 获取一段时间内所获得的奖励值（积分or学分）
     * @param startTime  开始时间
     * @param endTime   结束时间
     * @param reachRewardType   奖励类型
     * @param userId 用户ID
     * @return
     */
    float getSubScoreByTimes(@Param("startTime") Date startTime,@Param("endTime") Date endTime,
                             @Param("reachRewardType") String reachRewardType,@Param("userId") String userId);

    void updateRemainTime(Double remainTime,String userExamId);

}
