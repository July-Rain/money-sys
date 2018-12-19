package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

public interface AbstractDao<T> extends BaseMapper<T> {

    /**
     * 保存数据
     * @param entity
     * @return
     */
    Integer insert(T entity);

    /**
     * 更新数据
     * @param entity
     * @return
     */
    Integer update(T entity);

    /**
     * 查询多条数据
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T findOne(String id);

    /**
     * 根据属性查询
     * @param entity
     * @return
     */
    T findByEntity(T entity);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    void delete(List<String> ids);
}
