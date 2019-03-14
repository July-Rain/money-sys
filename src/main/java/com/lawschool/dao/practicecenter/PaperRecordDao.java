package com.lawschool.dao.practicecenter;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.practicecenter.PaperRecordEntity;
import com.lawschool.form.CommonForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/3/11 18:51
 * @Description:
 */
public interface PaperRecordDao extends AbstractDao<PaperRecordEntity> {

    boolean saveBatch(List<CommonForm> list);

    List<String> getQuestions(@Param("taskId") String taskId);
}
