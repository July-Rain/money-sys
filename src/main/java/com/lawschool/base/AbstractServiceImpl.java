package com.lawschool.base;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

public abstract class AbstractServiceImpl<D extends AbstractDao<T>, T extends DataEntity<T>> extends ServiceImpl<D, T> implements AbstractService<T> {

    /**
     * 持久层对象
     */
    protected D dao;


    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    public Boolean save(T entity){
        int success;
        if(StringUtils.isEmpty(entity.getId())){
            setDataEntity(entity);
            success = dao.insert(entity);
        } else {
            setDataEntity(entity);
            success = dao.update(entity);
        }
        return (success == 1);
    }

    /**
     * 查询分页数据
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, Wrapper<T> entity){
        entity.getEntity().setPage(page);
        page.setList(dao.selectList(entity));
        return page;
    }

    public void setDataEntity(DataEntity entity){

        String userId = "none";

        User user = (User)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("user");
        if(user != null){
            userId = user.getId();
        }

        if(StringUtils.isNotEmpty(entity.getId())){
            entity.setCreateBy(userId);
            entity.setCreateDate(new Date());
        }
        entity.setOptBy(userId);
        entity.setOptDate(new Date());

        setBaseEntity(entity);
    }

    public void setBaseEntity(BaseEntity entity){
        if(StringUtils.isEmpty(entity.getId())){
            entity.setId(IdWorker.getIdStr());
        }
    }
}
