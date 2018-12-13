package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.Dict;
import com.lawschool.beans.competition.RecruitConfiguration;
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
public interface DictService extends IService<Dict> {
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

    /**
     * 查询字典表结合父子节点
     */
    List<Map<String,Object>> queryForZtree();

    /**
     * 查询当前节点下是否还有节点
     */
    List<Dict> queryListParentCode(String code);

    void deleteByCode(String code);

    Dict selectByCode(String code);
}