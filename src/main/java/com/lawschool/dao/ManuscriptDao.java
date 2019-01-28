package com.lawschool.dao;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.ManuscriptEntity;
import org.apache.ibatis.annotations.Param;

public interface ManuscriptDao extends AbstractDao<ManuscriptEntity> {
    Boolean updateExamine(@Param("id") String id, @Param("status") Integer status);
}
