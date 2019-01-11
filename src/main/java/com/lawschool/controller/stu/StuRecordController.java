package com.lawschool.controller.stu;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.service.learn.StuRecordService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: StuRecordController
 * Description: 学习记录controller
 * date: 2019-1-10 15:05
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("sturecord")
public class StuRecordController extends AbstractController {

    @Autowired
    private StuRecordService recordService;

    @RequestMapping("/insertRecord")
    public Result insertRecord(String stuId, String stuType, String stuFrom, String taskId){
        //获取当前登陆人
        User user=  getUser();
        //插入学习记录
        recordService.insertStuRecordNormal(user,stuId,stuType,stuFrom,taskId);
        return Result.ok();
    }
}
