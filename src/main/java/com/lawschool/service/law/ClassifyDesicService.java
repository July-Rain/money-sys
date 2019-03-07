package com.lawschool.service.law;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.TaskDesicEntity;
import com.lawschool.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * InterfaceName: ClassifyDesicService
 * Description: 法律法规service
 * date: 2018-12-25 11:20
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface ClassifyDesicService extends IService<ClassifyDesicEntity> {
    /**
     * @Author MengyuWu
     * @Description 根据任务查询法律法规文件
     * @Date 19:02 2018-12-26
     * @Param [params]
     * @return com.lawschool.util.PageUtils
     **/
    
    PageUtils queryListByTask(Map<String,Object> params);

    /**
     * @Author MengyuWu
     * @Description 分页查询法律法规文件
     * @Date 19:02 2018-12-26
     * @Param [param]
     * @return com.lawschool.util.PageUtils
     **/
    
    PageUtils queryPage(Map<String,Object> param);

    /**
     * @Author MengyuWu
     * @Description 分页查询法律法规文件
     * @Date 19:02 2018-12-26
     * @Param [param]
     * @return com.lawschool.util.PageUtils
     **/

    PageUtils queryPageBySyn(Map<String,Object> param);

    /**
     * @Author MengyuWu
     * @Description 根据当前用户  查询对应的学习内容信息（管理员维护学习任务时）
     * @Date 10:14 2018-12-27
     * @Param [page, desicEntity]
     * @return java.util.List<com.lawschool.beans.law.ClassifyDesicEntity>
     **/

    PageUtils queryListByUser(Map<String, Object> params);

}
