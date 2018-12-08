package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Descriptin 字典service
 * @Author liuhuan
 * @version v1.0
 * @Time  2018/11/28
 *
 */
public interface DictService extends AbstractService {
    /**
     * 查询用户字典表
     * @param id
     * @return
     */
    public Dict selectByDictId(String id);

    /**
     * 获取全部字典集合
     * @return
     */
    public List<Dict> selectAllDict();

    /**
     * 新增数据字典
     * @param dict
     */
    public void addDict(Dict dict);

    /**
     * 修改数据字典
     * @param dict
     */
    public void updateByDict(Dict dict);

    /**
     * 批量删除数据字典
     * @param ids
     */
    public void deleteByDictIds(Map ids);

    /**
     * 删除数据字典表
     * @param id
     */
    public  void deleteByDictId(@Param(value="id") String id);
}