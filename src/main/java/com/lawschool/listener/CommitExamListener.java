package com.lawschool.listener;

import com.lawschool.beans.exam.UserExam;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.service.impl.exam.UserExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ClassName: MyMessageListener
 * Description: 提交试卷监听器
 * date: 2019/1/159:43
 *
 * @author 王帅奇
 */
@Component
public class CommitExamListener implements MessageListener {

    @Autowired
    private UserExamService userExamService;
    @Override
    public void onMessage(Message msg) {
        if (msg instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) msg;
                UserAnswerForm userAnswerForm =(UserAnswerForm) objectMessage.getObject();
                //实际项目中拿到String类型的message(通常是JSON字符串)之后，
                //会进行反序列化成对象，做进一步的处理
                System.out.println("receive txt msg===" + userAnswerForm.getUserExamId());
                userExamService.commitExam(userAnswerForm);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }



}