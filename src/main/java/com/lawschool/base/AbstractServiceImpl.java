package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

public abstract class AbstractServiceImpl<D extends AbstractDao<T>, T extends DataEntity<T>> extends ServiceImpl<D, T> implements AbstractService<T> {

    /**
     * 持久层对象
     */
    protected D dao;


    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, EntityWrapper<T> entity){
        entity.getEntity().setPage(page);
        page.setList(dao.selectList(entity));
        return page;
    }
}
