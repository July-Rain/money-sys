package com.lawschool.service.impl.exam;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.dao.exam.CheckUserExamFormDao;
import com.lawschool.dao.exam.UserExamFormDao;
import com.lawschool.form.CheckUserExamForm;
import com.lawschool.form.UserExamForm;
import com.lawschool.service.exam.CheckUserExamFormService;
import com.lawschool.service.exam.UserExamFormService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: CheckUserExamFormServiceImpl
 * Description: TODO
 * date: 2019/2/1113:57
 *
 * @author 王帅奇
 */
@Service
public class CheckUserExamFormServiceImpl extends AbstractServiceImpl<CheckUserExamFormDao, CheckUserExamForm> implements CheckUserExamFormService {

    @Override
    public List<CheckUserExamForm> getAuditList(List<String> list) {
        return dao.getAuditList(list);
    }
}
