package com.lawschool.dao.exam;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
