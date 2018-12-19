package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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

    /**
     * 保存之前执行方法
     * @param entity
     */
    void setDataEntity(DataEntity entity);

    void setBaseEntity(BaseEntity entity);
}
