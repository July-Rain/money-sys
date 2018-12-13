package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

public interface AbstractService<T> extends IService<T> {

    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    Boolean save(T entity);

    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    Page<T> findPage(Page<T> page, T entity);

    /**
     * 保存之前执行方法
     * @param entity
     */
    void setDataEntity(DataEntity entity);

    void setBaseEntity(BaseEntity entity);
}
