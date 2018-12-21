package com.lawschool.service.impl;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.MedalEntity;
import com.lawschool.dao.MedalDao;
import com.lawschool.service.MedalService;
import org.springframework.stereotype.Service;

@Service
public class MedalServiceImpl extends AbstractServiceImpl<MedalDao, MedalEntity> implements MedalService {
}
