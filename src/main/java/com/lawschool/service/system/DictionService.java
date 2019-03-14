package com.lawschool.service.system;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.form.CommonForm;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:30
 * @Description:
 */
public interface DictionService extends AbstractService<DictEntity> {

    /**
     * 删除
     * @param id 主键
     * @param isParent 是否为父类
     * @return
     */
    boolean mydelete(String id, Integer isParent);

    List<CommonForm> findCodeByType(String code);

    /**
     * 查询所有字典信息
     * @return
     */
    List<DictEntity> selectAllDict();

    /**
     * 判断名称或码值是否重复
     * @param id
     * @param str
     * @param type
     * @return
     */
    boolean checkDuplicate(String id, String str, String type, String parentCode);

    Map<String, List<CommonForm>> getDictByParentCode(List<String> codes);


    /**
     * @Author MengyuWu
     * @Description 根据名称查code  --系统默认密码使用
     * @Date 10:07 2019-3-14
     * @Param [name]
     * @return String
     **/

    String selectOneByName(String name);

}
