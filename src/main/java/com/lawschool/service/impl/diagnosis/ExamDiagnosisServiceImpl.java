package com.lawschool.service.impl.diagnosis;

import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.diagnosis.DiagnosisEntity;
import com.lawschool.beans.diagnosis.OrgDiagnosisEntity;
import com.lawschool.beans.diagnosis.StuDiagnosisEntity;
import com.lawschool.dao.diagnosis.ExamDiagnosisDao;
import com.lawschool.dao.diagnosis.StuDiagnosisDao;
import com.lawschool.service.diagnosis.ExamDiagnosisService;
import com.lawschool.util.PageUtils;
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
    @Autowired
    private StuDiagnosisDao stuDiagnosisDao;
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

    @Override
    public PageUtils allExamStatByOrgId(Map<String, String> param) {
        Page<OrgDiagnosisEntity> page = new Page<OrgDiagnosisEntity>(Integer.parseInt(param.get("currPage")),Integer.parseInt(param.get("pageSize")));
        page.setRecords(diagnosisDao.allExamStatByOrgId(page,param));
        int userNum =stuDiagnosisDao.countUser(param.get("orgId"));
        page.setTotal(userNum);
        return new PageUtils(page);
    }
}
