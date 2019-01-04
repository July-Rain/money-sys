package com.lawschool.controller.exam;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExam;
import com.lawschool.service.exam.ExamConfigService;
import com.lawschool.service.exam.UserExamService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        ExamConfig entity = new ExamConfig();
        Page<ExamConfig> page = examConfigService.findPage(new Page<ExamConfig>(params), entity);
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/startExam", method = RequestMethod.POST)
    public Result startExam( String examConfigId) {
        Result res = userExamService.getExam(examConfigId);
        return res;
    }
}
