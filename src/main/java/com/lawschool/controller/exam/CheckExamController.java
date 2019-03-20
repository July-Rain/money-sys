package com.lawschool.controller.exam;

import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.exam.CheckExamUser;
import com.lawschool.form.UserAnswerForm;
import com.lawschool.service.exam.CheckExamService;
import com.lawschool.service.exam.CheckExamUserService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName: CheckExamController
 * Description: 阅卷
 * date: 2019/1/2315:36
 *
 * @author 王帅奇
 */
@RestController
@RequestMapping("/check/exam")
public class CheckExamController extends AbstractController {

    @Autowired
    private CheckExamUserService checkExamUserService;

    @Autowired
    private CheckExamService checkExamService;

    /**
     * 阅卷人登陸
     * @param checkUser
     * @return
     */
    @SysLog("阅卷人登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody CheckExamUser checkUser) {
        Result res = checkExamUserService.login(checkUser);
        return res;
    }

    /**
     * 保存阅卷人信息
     * @param checkExamUser
     * @return
     */
    @SysLog("保存阅卷人信息")
    @RequestMapping(value = "/saveCheckExamUser" , method = RequestMethod.POST)
    public Result saveCheckExamUser(@RequestBody CheckExamUser checkExamUser){
        Result res = checkExamUserService.saveCheckExamUser(checkExamUser);
        return  res;
    }

    /**
     * 查询阅卷列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list" , method = RequestMethod.POST)
    public Result list(@RequestParam Map<String, Object> params){
        Result result = checkExamUserService.list(params);
        return result;
    }

    /**
     * 开始阅卷
     * @param userExamId
     * @return
     */
    @SysLog("开始阅卷")
    @RequestMapping( value = "/startCheckExam" , method = RequestMethod.POST)
    public Result startCheckExam(String userExamId){

        Result res = checkExamService.startCheckExam(userExamId);

        return res;
    }

    /**
     * 继续阅卷
     * @param userExamId
     * @return
     */
    @SysLog("继续阅卷")
    @RequestMapping( value = "/continueCheckExam" , method = RequestMethod.POST)
    public Result continueCheckExam(String userExamId,String checkExamUserId){

        Result res = checkExamService.continueCheckExam(userExamId,checkExamUserId);

        return res;
    }

    /**
     * 保存阅卷信息
     * @param userAnswerForm
     * @return
     */
    @SysLog("保存阅卷信息")
    @RequestMapping( value = "/saveCheckExam" , method = RequestMethod.POST)
    public Result saveCheckExam(@RequestBody UserAnswerForm userAnswerForm){

        Result res = checkExamService.saveCheckExam(userAnswerForm);// checkExamService.startCheckExam(userExamId);

        return res;
    }

    /**
     * 提交阅卷信息
      * @param userAnswerForm
     * @return
     */
    @SysLog("提交阅卷信息")
    @RequestMapping( value = "/commitCheckExam" , method = RequestMethod.POST)
    public Result commitCheckExam(@RequestBody UserAnswerForm userAnswerForm){

        Result res =checkExamService.commitCheckExam(userAnswerForm);// checkExamService.startCheckExam(userExamId);

        return res;
    }

    /**
     * 审核阅卷
     * @param userExamId
     * @return
     */
    @RequestMapping( value = "/auditCheckExam" , method = RequestMethod.POST)
    public Result auditCheckExam(String userExamId){

        Result res = checkExamService.auditCheckExam(userExamId);

        return res;
    }

    /**
     * 提交审核阅卷信息
     * @param userAnswerForm
     * @return
     */
    @RequestMapping( value = "/commitAuditExam" , method = RequestMethod.POST)
    public Result commitAuditExam(@RequestBody UserAnswerForm userAnswerForm){

        Result res =checkExamService.commitAuditExam(userAnswerForm);// checkExamService.startCheckExam(userExamId);

        return res;
    }
}
