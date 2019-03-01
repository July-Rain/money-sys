package com.lawschool.dao.exam;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.base.AbstractDao;
import com.lawschool.beans.exam.ExamQueConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamQueConfigDao extends AbstractDao<ExamQueConfig> {
    List<ExamQueConfig> getByExamConfigId(@Param("examConfigId") String examConfigId);
}
