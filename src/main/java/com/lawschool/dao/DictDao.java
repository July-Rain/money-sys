package com.lawschool.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Dict;

import java.util.List;
import java.util.Map;

public interface DictDao extends BaseMapper<Dict> {

     Dict selectByDictId(String id);

     List<Dict> selectAllDict();

     void addDict(Dict dict);

     void updateByDictId(Dict dict);

     void deleteByDictIds(Map ids);

    void deleteByDictId(String id);

    List<Map<String,Object>> queryForZtree();

    List<Dict> queryListParentCode(String code);

    void deleteByCode(String code);

    Dict selectByCode(String code);
}
