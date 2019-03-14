package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.dao.system.TopicTypeDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.system.TopicTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description: 主题类型service实现类
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 11:16
 */
@Service
public class TopicTypeServiceImpl extends AbstractServiceImpl<TopicTypeDao, TopicTypeEntity> implements TopicTypeService {

    public boolean mysave(TopicTypeEntity entity){

        return dao.mysave(entity);
    }

    public boolean updateDelFlag(String id){

        return dao.updateDelFlag(id);
    }

    @Override
    public List<CommonForm> findAll(List<String> list){
        List<CommonForm> resultList = new ArrayList<>();

        resultList = dao.findAll(list);

        return resultList;
    }
}
