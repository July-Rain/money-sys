package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.dao.law.LawClassifyDao;
import com.lawschool.service.law.LawClassifyService;
import org.springframework.stereotype.Service;

/**
 * ClassName: LawClassifyServiceImpl
 * Description: 法律主题分类 impl
 * date: 2018-12-7 15:14
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class LawClassifyServiceImpl extends ServiceImpl<LawClassifyDao,LawClassifyEntity> implements LawClassifyService {
}
