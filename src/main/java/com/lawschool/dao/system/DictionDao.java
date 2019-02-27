package com.lawschool.dao.system;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.form.CommonForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/16 19:29
 * @Description:
 */
public interface DictionDao extends AbstractDao<DictEntity> {

    /**
     * 更新删除标志位
     * @param id
     * @return
     */
    Integer remove(@Param("id") String id);

    /**
     * 批量更新子类信息
     * @param parentCode
     * @return
     */
    Integer updateDelByParentCode(@Param("parentCode") String parentCode);

    List<CommonForm> findCodeByType(@Param("code") String code);

    List<DictEntity> selectAllDict();

    /**
     * 查询是否同名
     * @param id
     * @param str
     * @param type
     * @return
     */
    Integer checkDuplicate(@Param("id") String id,
                           @Param("str") String str,
                           @Param("type") String type);
}
