package com.lawschool.service.impl.diagnosis;

import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.dao.diagnosis.ExamDiagnosisDao;
import com.lawschool.service.diagnosis.ExamDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ExamDiagnosisServiceImpl
 * Description: TODO
 * date: 2019-1-18 10:05
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class ExamDiagnosisServiceImpl implements ExamDiagnosisService {
    @Autowired
    private ExamDiagnosisDao diagnosisDao;
    @Override
    public List<DiagnosisEntity> getDiaInfo(Map<String, String> param) {
        return diagnosisDao.getDiaInfo(param);
    }

    @Override
    public List<DiagnosisEntity> DiaStat(Map<String, String> param) {
        return diagnosisDao.DiaStat(param);
    }

    @Override
    public List<OrgDiagnosisEntity> orgDiaStat(Map<String, String> param) {
        return diagnosisDao.orgDiaStat(param);
    }
}
