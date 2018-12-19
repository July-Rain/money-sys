package com.lawschool.serviceimpl.competition.bak;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.beans.competition.bak.RecruitCheckpointConfigurationBak;
import com.lawschool.dao.competition.RecruitCheckpointConfigurationDao;
import com.lawschool.dao.competition.bak.RecruitCheckpointConfigurationBakDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.service.competition.bak.RecruitCheckpointConfigurationBakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitCheckpointConfigurationServiceBakImpl extends ServiceImpl<RecruitCheckpointConfigurationBakDao, RecruitCheckpointConfigurationBak> implements RecruitCheckpointConfigurationBakService {

	@Autowired
	private RecruitCheckpointConfigurationBakDao recruitcheckpointconfigurationbakDao;


}
