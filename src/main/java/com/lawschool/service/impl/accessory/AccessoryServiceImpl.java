package com.lawschool.service.impl.accessory;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.accessory.AccessoryEntity;
import com.lawschool.dao.accessory.AccessoryDao;
import com.lawschool.service.accessory.AccessoryService;
import org.springframework.stereotype.Service;

/**
 * ClassName: AccessoryService
 * Description: 附件表的impl
 * date: 2018-12-10 13:49
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service(value = "accessoryService")
public class AccessoryServiceImpl extends ServiceImpl<AccessoryDao,AccessoryEntity> implements AccessoryService {
}
