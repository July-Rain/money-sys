package com.lawschool.service.practicecenter;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.practicecenter.AnalysisEntity;

import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/1/10 19:15
 * @Description:
 */
public interface AnalysisService extends AbstractService<AnalysisEntity> {

    Map<String, Object> analysis(String month, String userId);
}
