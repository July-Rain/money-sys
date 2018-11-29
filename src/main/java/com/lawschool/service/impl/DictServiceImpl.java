package com.lawschool.service.impl;

import com.lawschool.beans.Dict;
import com.lawschool.dao.DictDao;
import com.lawschool.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

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
    public void deleteByDictIds() {
        //去查找  所有 符合的 id    ids
        //用list来吧这些id 包装起来

        //见一个 map
        //吧list  放到 map里面
        Map map=new HashMap();
        map.put("list",list);
        dictDao.deleteByDictIds(map);
    }

    @Override
    public void deleteByDictId(String id) {
        dictDao.deleteByDictId(id);
    }
}