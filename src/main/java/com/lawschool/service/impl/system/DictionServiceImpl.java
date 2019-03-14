package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.dao.system.DictionDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.system.DictionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.lawschool.util.UtilMisc;
import com.lawschool.util.UtilValidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/2/15 16:37
 * @Description:
 */
@Service
public class DictionServiceImpl extends AbstractServiceImpl<DictionDao, DictEntity> implements DictionService {

    /**
     * 删除
     * @param id 主键
     * @param isParent 是否为父类
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mydelete(String id, Integer isParent){

        // 删除，更新标志位
        int result = dao.remove(id);

        if(result == 1){
            if(isParent == 1){
                // 父类，则一并更新子类信息
                dao.updateDelByParentCode(id);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CommonForm> findCodeByType(String code){

        return dao.findCodeByType(code);
    }

    public List<DictEntity> selectAllDict(){

        return dao.selectAllDict();
    }

    /**
     * 校验名称和码值是否重复
     * @param id 主键，无值为新增、有值为编辑
     * @param str 判断的值
     * @param type 值的类型
     * @return true 不重名
     */
    @Override
    public boolean checkDuplicate(String id, String str, String type, String parentCode){
        Integer result = dao.checkDuplicate(id, str, type, parentCode);
        if(result > 0){
            return false;
        }
        return true;
    }

    /**
     * 批量根据父类CODE 查询字典
     * @param codes
     * @return
     */
    @Override
    public Map<String, List<CommonForm>> getDictByParentCode(List<String> codes){
        // 定义返回结果集
        Map<String, List<CommonForm>> result = new HashMap<String, List<CommonForm>>();

        List<CommonForm> list = dao.getDictByParentCode(codes);
        // 查询结果为空，直接返回空Map
        if(CollectionUtils.isEmpty(list)){ return result; }

        String key = "";
        List<CommonForm> tempList = new ArrayList<>();

        for(CommonForm form : list){
            // key为空，为第一次进循环
            if(StringUtils.isBlank(key)){
                key = form.getOpinion();
                tempList.add(form);

            } else {
                String parentCode = form.getOpinion();
                if(key.equals(parentCode)){
                    tempList.add(form);

                } else {
                    result.put(key, tempList);

                    // 初始化参数
                    key = parentCode;
                    tempList = new ArrayList<>();
                    tempList.add(form);
                }
            }

        }
        result.put(key, tempList);

        return result;
    }

    @Override
    public String selectOneByName(String name) {
        DictEntity dictEntity = baseMapper.selectOneByName(name);
        if(UtilValidate.isNotEmpty(dictEntity)){
            return dictEntity.getCode();
        }
        return "";
    }
}