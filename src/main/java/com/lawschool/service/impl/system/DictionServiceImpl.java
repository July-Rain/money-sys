package com.lawschool.service.impl.system;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.dao.system.DictionDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.system.DictionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
