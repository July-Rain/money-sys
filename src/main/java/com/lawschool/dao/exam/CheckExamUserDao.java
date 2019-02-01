package com.lawschool.dao.exam;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.CheckExamUser;
import org.apache.ibatis.annotations.Param;

public interface CheckExamUserDao  extends AbstractDao<CheckExamUser> {

    int getCount(@Param("checkPassword") String checkPassword,
                 @Param("checkUserType") String checkUserType);

    CheckExamUser findByUserCodeAndPassword(@Param("checkPassword") String checkPassword,
                                            @Param("userCode") String userCode,
                                            @Param("checkUserType") String checkUserType);

    int getCountByExamConId(@Param("examConfigId") String examConfigId);
}
