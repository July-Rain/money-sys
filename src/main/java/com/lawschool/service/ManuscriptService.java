package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.ManuscriptEntity;

/**
 * @ClassName ManuscriptService
 * @Author wangtongye
 * @Date 2018/12/27 17:27
 * @Versiom 1.0
 **/
public interface ManuscriptService extends AbstractService<ManuscriptEntity> {

    Boolean mySave(ManuscriptEntity entity);

    Boolean myDelete(String id);

    Boolean examine(String id, String type);
}
