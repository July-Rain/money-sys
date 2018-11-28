package com.lawschool.dao;

import com.lawschool.beans.Dict;

import java.util.List;

public interface DictDao {

    public Dict selectByDictId(Integer id);

    public List<Dict> selectAllDict();

    public void addDict(Dict dict);

    public void updateByDictId(Dict dict);

    public void deleteByDictIds(int[] ids);

    public void deleteByDictId(int id);
}
