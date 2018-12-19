package com.lawschool.serviceimpl.competition.bak;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.beans.competition.bak.RecruitConfigurationBak;
import com.lawschool.dao.competition.RecruitConfigurationDao;
import com.lawschool.dao.competition.bak.RecruitConfigurationBakDao;
import com.lawschool.form.CommonForm;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.service.competition.RecruitConfigurationService;
import com.lawschool.service.competition.bak.RecruitConfigurationBakService;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecruitConfigurationServiceBakImpl extends ServiceImpl<RecruitConfigurationBakDao, RecruitConfigurationBak> implements RecruitConfigurationBakService {
	
	@Autowired
	private RecruitConfigurationBakDao recruitconfigurationbakDao;

}
