package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.law.LawClassifyLibEntity;
import com.lawschool.dao.law.LawClassifyLibDao;
import com.lawschool.service.law.LawClassifyLibService;
import org.springframework.stereotype.Service;

/**
 * ClassName: LawClassifyLibServiceImpl
 * Description: 库分类 impl
 * date: 2018-12-7 15:14
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class LawClassifyLibServiceImpl extends ServiceImpl<LawClassifyLibDao,LawClassifyLibEntity> implements LawClassifyLibService {
}
