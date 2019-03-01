package com.lawschool.service.exam;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.exam.ExamQueConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamQueConfigService extends AbstractService<ExamQueConfig> {

    List<ExamQueConfig> getByExamConfigId( String examConfigId);
}
