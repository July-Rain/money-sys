package com.lawschool.listener;

import com.lawschool.service.exam.CheckExamUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.xml.ws.handler.MessageContext;

/**
 * ClassName: GenerateCheckExamListener
 * Description: 配置分配阅卷试卷监听器
 * date: 2019/1/2615:03
 *
 * @author 王帅奇
 */
public class GenerateCheckExamListener implements MessageListener {

    @Autowired
    private CheckExamUserService checkExamUserService;

    @Override
    public void onMessage(Message message) {

    }
}
