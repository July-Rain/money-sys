package com.lawschool.service.impl;
import com.lawschool.beans.SysMenu;
import com.lawschool.dao.RecruitConfigurationDao;
import com.lawschool.dao.SysMenuDao;
import com.lawschool.service.RecruitConfigurationService;
import com.lawschool.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecruitConfigurationServiceImpl implements RecruitConfigurationService {
	
	@Autowired
	private RecruitConfigurationDao recruitconfigurationDao;


}
