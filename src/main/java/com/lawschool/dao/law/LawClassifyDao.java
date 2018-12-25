package com.lawschool.dao.law;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.TaskDesicEntity;

import java.util.List;

/**
 * ClassName: LawClassifyDao
 * Description: 库分类 dao
 * date: 2018-12-7 15:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface LawClassifyDao extends BaseMapper<LawClassifyEntity> {
    List<TaskDesicEntity> queryClassTree();
}