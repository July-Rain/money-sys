package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.Dict;
import com.lawschool.dao.DictDao;
import com.lawschool.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl extends AbstractServiceImpl implements DictService {

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
}