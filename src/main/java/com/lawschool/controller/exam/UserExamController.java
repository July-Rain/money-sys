package com.lawschool.controller.exam;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.form.ThemeAnswerForm;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.form.UserExamForm;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserExamController
 * Description: TODO
 * date: 2018/12/2716:05
 *
 * @author 王帅奇
 */
@RestController
@RequestMapping("/user/exam")
public class UserExamController extends AbstractController {

    @Autowired
    private ExamConfigService examConfigService;

    @Autowired
    private UserExamService userExamService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination queueDestination;

//    @RequestMapping("/list")
//    public Result list(@RequestParam Map<String, Object> params) {
//        ExamConfig entity = new ExamConfig();
//        Page<ExamConfig> page = examConfigService.findPage(new Page<ExamConfig>(params), entity);
//        return Result.ok().put("page", page);
//    }

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        Result result = userExamService.getList(params,getUser());
        return result;
    }
    /**
     * 开始考试获取试卷
     * @param examConfigId
     * @return
     */
    @RequestMapping(value = "/startExam", method = RequestMethod.POST)
    public Result startExam( String examConfigId) {
        Result res = userExamService.getExam(examConfigId,getUser());
        return res;
    }

    /**
     * 继续考试
     * @param userExamId
     * @return
     */
    @RequestMapping(value = "/continueExam", method = RequestMethod.POST)
    public Result continueExam( String userExamId) {
        Result res = userExamService.continueExam(userExamId,getUser());
        return res;
    }

    /**
     * 提交考试
     * @param userAnswerForm
     * @return
     */
    @RequestMapping(value = "/commitExam", method = RequestMethod.POST)
    public Result commitExam(@RequestBody UserAnswerForm userAnswerForm){

        jmsTemplate.send(queueDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage((Serializable) userAnswerForm);
                return objectMessage;
            }
        });
        return Result.ok();
    }

    @RequestMapping(value = "/saveExam",method = RequestMethod.POST)
    public Result saveExam(@RequestBody UserAnswerForm userAnswerForm){

        userExamService.saveExam(userAnswerForm);

        return Result.ok();
    }

    @RequestMapping(value = "/viewExam",method = RequestMethod.POST)
    public Result viewExam( String userExamId ){

        Result res = userExamService.viewExam(userExamId,getUser());

        return res;
    }

}
