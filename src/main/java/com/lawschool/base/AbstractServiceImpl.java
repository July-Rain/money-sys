package com.lawschool.base;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public abstract class AbstractServiceImpl<D extends AbstractDao<T>, T extends DataEntity<T>> extends ServiceImpl<D, T> implements AbstractService<T> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;


    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
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
    public Page<T> findPage(Page<T> page, T entity){
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public T findOne(String id){
        return dao.findOne(id);
    }

    /**
     * 根据属性查询
     * @param entity
     * @return
     */
    public T findByEntity(T entity){
        return dao.findByEntity(entity);
    }

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(List<String> ids){
        dao.delete(ids);
    }

    public void setDataEntity(DataEntity entity){

        String userId = "none";

        User user = (User)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("user");
        if(user != null){
            userId = user.getId();
        }

        if(StringUtils.isNotEmpty(entity.getId())){
            entity.setCreateUser(userId);
            entity.setCreateTime(new Date());
        }
        entity.setOptUser(userId);
        entity.setOptTime(new Date());

        setBaseEntity(entity);
    }

    public void setBaseEntity(BaseEntity entity){
        if(StringUtils.isEmpty(entity.getId())){
            entity.setId(IdWorker.getIdStr());
        }
    }
}
