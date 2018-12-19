package com.lawschool.serviceimpl.competition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.annotation.SysLog;
import com.lawschool.beans.Answer;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.dao.competition.RecruitCheckpointConfigurationDao;
import com.lawschool.form.AnswerForm;
import com.lawschool.form.QuestForm;
import com.lawschool.service.AnswerService;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitCheckpointConfigurationServiceImpl extends ServiceImpl<RecruitCheckpointConfigurationDao, RecruitCheckpointConfiguration> implements RecruitCheckpointConfigurationService {

	@Autowired
	private RecruitCheckpointConfigurationDao recruitcheckpointconfigurationDao;

	@Autowired
	private TestQuestionService testQuestionService;
	@Autowired
	private AnswerService answerService;

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<RecruitCheckpointConfiguration> findAll() {
		return this.selectList(new EntityWrapper<RecruitCheckpointConfiguration>());
	}




	@Override
	@Transactional(rollbackFor = Exception.class)
	public RecruitCheckpointConfiguration info(String id) {
		RecruitCheckpointConfiguration recruitCheckpointConfiguration=  this.selectOne(new EntityWrapper<RecruitCheckpointConfiguration>().eq("ID",id));
		return recruitCheckpointConfiguration;
	}

	@Override
	public void save(RecruitCheckpointConfiguration recruitCheckpointConfiguration) {

	}

	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<RecruitCheckpointConfiguration> selectListByBaBaId(String id) {
		List<RecruitCheckpointConfiguration> list= recruitcheckpointconfigurationDao.selectListByBaBaId(id);
		return list;
	}


	@Override
	@SysLog("查询")
	@Transactional(rollbackFor = Exception.class)
	public List<QuestForm> getQuestByids() {
		List<String> list =new ArrayList<String>();
		list.add("1");
		list.add("2");
		List<QuestForm> Questlist=testQuestionService.findByIds(list);

		List<AnswerForm> answerForms = answerService.findByQuestionIds(list);
		// 遍历处理选项信息
		for(QuestForm quest : Questlist){//遍历题目   我要得到答案啊
			String questid = quest.getId();
			List<AnswerForm> tempList = new ArrayList<>();
			for(AnswerForm af : answerForms){
				String aqid = af.getQuestionId();
				if(questid.equals(aqid)){
					tempList.add(af);
				}
			}
			quest.setAnswer(tempList);
			answerForms.removeAll(tempList);
		}


		return Questlist;
	}
}
