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

    List<T> findList(T entity);
}
