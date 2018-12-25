package com.lawschool.service.law;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.TaskDesicEntity;

import java.util.List;

/**
 * ClassName: LawClassifyService
 * Description: 法律主体分类service
 * date: 2018-12-7 15:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface LawClassifyService extends IService<LawClassifyEntity> {

    List<TaskDesicEntity> queryClassTree();
}
