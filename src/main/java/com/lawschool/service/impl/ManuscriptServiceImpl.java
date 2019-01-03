package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.ManuscriptEntity;
import com.lawschool.dao.ManuscriptDao;
import com.lawschool.service.ManuscriptService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ManuscriptServiceImpl
 * @Author wangtongye
 * @Date 2018/12/27 17:29
 * @Versiom 1.0
 **/
@Service
public class ManuscriptServiceImpl extends AbstractServiceImpl<ManuscriptDao, ManuscriptEntity> implements ManuscriptService {
}
