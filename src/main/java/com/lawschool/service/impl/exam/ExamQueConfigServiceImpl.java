package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.exam.ExamQueConfig;
import com.lawschool.dao.exam.ExamQueConfigDao;
import com.lawschool.service.exam.ExamQueConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamQueConfigServiceImpl extends AbstractServiceImpl<ExamQueConfigDao, ExamQueConfig> implements ExamQueConfigService {

    @Override
    public List<ExamQueConfig> getByExamConfigId(String examConfigId) {
        return dao.getByExamConfigId(examConfigId);
    }
}
