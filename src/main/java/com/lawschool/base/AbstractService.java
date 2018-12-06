package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;

public interface AbstractService<T> extends IService<T> {


    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    Page<T> findPage(Page<T> page, EntityWrapper<T> entity);
}
