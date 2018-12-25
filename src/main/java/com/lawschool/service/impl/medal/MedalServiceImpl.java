package com.lawschool.service.impl.medal;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.medal.MedalEntity;
import com.lawschool.dao.medal.MedalDao;
import com.lawschool.service.medal.MedalService;
import org.springframework.stereotype.Service;

@Service
public class MedalServiceImpl extends AbstractServiceImpl<MedalDao, MedalEntity> implements MedalService {
}
