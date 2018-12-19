package com.lawschool.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Dict;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.dao.DictDao;
import com.lawschool.dao.competition.RecruitConfigurationDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements DictService {

    @Autowired
    DictDao dictDao;

    @Override
    public Dict selectByDictId(String id) {
        return dictDao.selectByDictId(id);
    }

    @Override
    public List<Dict> selectAllDict() {
        return dictDao.selectAllDict();
    }

    @Override
    public void addDict(Dict dict) {
        dictDao.addDict(dict);
    }

    @Override
    public void updateByDict(Dict dict) {
        dictDao.updateByDictId(dict);
    }

    @Override
    public void deleteByDictIds(Map ids) {

    }

    @Override
    public void deleteByDictId(String id) {
        dictDao.deleteByDictId(id);
    }

    @Override
    public List<Map<String, Object>> queryForZtree() {
        return dictDao.queryForZtree();
    }

    @Override
    public List<Dict> queryListParentCode(String code) {
        return dictDao.queryListParentCode(code);
    }

    @Override
    public void deleteByCode(String code) {
        dictDao.deleteByCode(code);
    }

    @Override
    public Dict selectByCode(String code) {
        return dictDao.selectByCode(code);
    }

    @Override
    public List<CommonForm> findByType(String type){
        List<CommonForm> list = dictDao.findByType(type);

        return list;
    }
}