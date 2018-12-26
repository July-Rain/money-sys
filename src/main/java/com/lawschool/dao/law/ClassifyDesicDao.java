package com.lawschool.dao.law;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author MengyuWu
 * @Description 法律法规dao
 * @Date 11:22 2018-12-25
 * @Param 
 * @return 
 **/

public interface ClassifyDesicDao extends BaseMapper<ClassifyDesicEntity> {
    List<ClassifyDesicEntity> queryListByTask(Page page,@Param("desic") TaskDesicEntity desicEntity);
}