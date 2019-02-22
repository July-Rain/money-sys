package com.lawschool.service.impl.exam;


import com.alibaba.fastjson.JSONObject;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.exam.CheckExam;
import com.lawschool.beans.exam.CheckExamUser;
import com.lawschool.beans.exam.ExamConfig;
import com.lawschool.beans.exam.UserExamAnswer;
import com.lawschool.config.ShiroUtils;
import com.lawschool.dao.exam.CheckExamDao;
import com.lawschool.dao.exam.CheckExamUserDao;
import com.lawschool.dao.exam.ExamConfigDao;
import com.lawschool.dao.exam.UserExamDao;
import com.lawschool.form.CheckUserExamForm;
import com.lawschool.form.UserExamForm;
import com.lawschool.service.exam.*;
import com.lawschool.util.GetUUID;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * ClassName: CheckUserExamServiceImpl
 * Description: TODO
 * date: 2019/1/2316:09
 *
 * @author 王帅奇
 */
@Service
public class CheckExamUserServiceImpl extends AbstractServiceImpl<CheckExamUserDao, CheckExamUser> implements CheckExamUserService {

    @Autowired
    private ExamConfigDao examConfigDao;

    @Autowired
    private UserExamDao userExamDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserExamService userExamService;

    @Autowired
    private CheckUserExamFormService checkUserExamFormService;

    @Autowired
    private CheckExamDao checkExamDao;

    @Autowired
    private UserExamAnswerService userExamAnswerService;

    @Autowired
    private UserExamFormService userExamFormService;

    @Autowired
    private ExamConfigService examConfigService;

    @Override
    public Result login(CheckExamUser checkUser) {

        //查看考试配置信息  如查不到抛出异常  阅卷口令错误
        ExamConfig examConfig = examConfigDao.findByCheckPassword(checkUser.getCheckPassword(), checkUser.getCheckUserType());
        if (examConfig == null) {
            return Result.error("阅卷口令错误，请核对！");
        }
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("123456", "123456");
        subject.login(token);

        CheckExamUser checkExamUser = dao.findByUserCodeAndPassword(checkUser.getCheckPassword(), checkUser.getExamUserCode(), checkUser.getCheckUserType());

        if ("0".equals(checkUser.getCheckUserType())) {
            //普通阅卷人员
            //先查已经注册人员人数  根据
            if (checkExamUser == null) {
                int count = dao.getCount(checkUser.getCheckPassword(), checkUser.getCheckUserType());
                if (count >= examConfig.getCheckNum().intValue()) {
                    return Result.error("阅卷人数已达到设定值，请核对！");
                } else {
                    checkExamUser = checkUser;
                    //未注册
                    return Result.ok().put("userStatus", "0").put("checkExamUser", checkExamUser).put("examConfig", examConfig);
                }
            } else {
                //已注册
                return Result.ok().put("userStatus", "1").put("checkExamUser", checkExamUser).put("examConfig", examConfig);
            }
        }else {
            //审核人员
            if (checkExamUser == null) {
                checkExamUser = checkUser;
                //未注册
                return Result.ok().put("userStatus", "0").put("checkExamUser", checkExamUser).put("examConfig", examConfig);
            } else {
                //已注册
                return Result.ok().put("userStatus", "1").put("checkExamUser", checkExamUser).put("examConfig", examConfig);
            }
        }

    }

    @Override
    public Result saveCheckExamUser(CheckExamUser checkExamUser) {
        ExamConfig examConfig = examConfigDao.findByCheckPassword(checkExamUser.getCheckPassword(), checkExamUser.getCheckUserType());
        CopyOnWriteArrayList<String> userCheckList = new CopyOnWriteArrayList<>();
        List<UserExamForm> userExamFormList = new ArrayList<>();
        if("0".equals(checkExamUser.getCheckUserType())){
            //普通阅卷人员
            //放入redis中
            if (!redisUtil.hasKey(examConfig.getId())) {
                saveOnRedis(examConfig.getId(),examConfig.getPaperPerSetNum(),examConfig.getCheckNum().intValue());
            }
            //从redis中取出列表并分割
            Map<String,Object> checkExamMap = JSONObject.parseObject(redisUtil.get(examConfig.getId()));
            CopyOnWriteArrayList<String> idList = new CopyOnWriteArrayList<>();
            idList.addAll((List<String>) checkExamMap.get("allList"));
            CopyOnWriteArrayList<String> yuList = new CopyOnWriteArrayList<>();
            yuList.addAll((List<String>) checkExamMap.get("yuList"));
            int perNum = (Integer) checkExamMap.get("perNum");
            //试卷名称  考试时间
            if(idList.size()>0) {
                userCheckList.addAll(idList.subList(0, perNum));
                for (int i = 0; i < perNum; i++) {
                    idList.remove(0);
                }
            }
            if(yuList.size()>0) {
                userCheckList.add(yuList.get(0));
                yuList.remove(0);
            }
            checkExamMap.put("allList",idList);
            checkExamMap.put("yuList",yuList);
            redisUtil.set(examConfig.getId(),checkExamMap);
            //保存审核用户
            checkExamUser.setExamConfigId(examConfig.getId());
            checkExamUser.setId(GetUUID.getUUIDs("CEU"));
            dao.insert(checkExamUser);
            //保存到审核用户阅卷列表
            for (String ids : userCheckList){
                CheckExam checkExam = new CheckExam();
                checkExam.setId(GetUUID.getUUIDs("CE"));
                checkExam.setExamConfigId(examConfig.getId());
                checkExam.setCheckPassword(examConfig.getCheckPassword());
                checkExam.setUserExamId(ids);
                checkExam.setCheckUserCode(checkExamUser.getExamUserCode());
                checkExam.setCheckExamUserId(checkExamUser.getId());
                checkExam.setCheckStatus("0");
                checkExamDao.insert(checkExam);
            }
        }else {
            //审核阅卷人员  审核阅卷人员不保存直接查 分差值大于配置分差值的试卷
            checkExamUser.setExamConfigId(examConfig.getId());
            checkExamUser.setId(GetUUID.getUUIDs("CEU"));
            dao.insert(checkExamUser);
        }
        //根据ID查找试卷
        if(UtilValidate.isNotEmpty(userCheckList)) {
            userExamFormList = userExamService.getListByIds(userCheckList);
        }
        return Result.ok().put("checkExamUser",checkExamUser);
    }

    private void saveOnRedis(String examConfigId,int paperPerSetNum,int checkNum){
        Map<String,Object> examMap = new HashMap<>();
        CopyOnWriteArrayList<String> allList = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> yuList = new CopyOnWriteArrayList<>();
        //第一个注册的人  进行列表分割保存
        CopyOnWriteArrayList<String> list = userExamDao.getListByExamConId(examConfigId);
        if (paperPerSetNum == 1) {
            //只需要一个人阅卷
            //判断能否整除
            int yushu = list.size() % checkNum;
            int perNum = allList.size() / checkNum;
            if (yushu != 0) {
                yuList =(CopyOnWriteArrayList) list.subList(list.size() - yushu, list.size());
                if(list.size()>0) {
                    for (int i = 0; i < yushu; i++) {
                        list.remove(list.size() - 1);
                    }
                }
            }
            allList = list;
            examMap.put("allList",allList);
            examMap.put("yuList",yuList);
            examMap.put("perNum",perNum);
            redisUtil.set(examConfigId,examMap);
        }else {
            list.addAll(list);
            allList.addAll(list);
            int yushu = allList.size() % checkNum;
            int perNum = allList.size() / checkNum;
            if (yushu != 0) {
                yuList.addAll(allList.subList(allList.size() - yushu, allList.size()));
                if(allList.size()>0) {
                    for (int i = 0; i < yushu; i++) {
                        allList.remove(allList.size() - 1);
                    }
                }
            }
            allList = list;
            examMap.put("allList",allList);
            examMap.put("yuList",yuList);
            examMap.put("perNum",perNum);
            redisUtil.set( examConfigId,examMap);
        }
    }

    @Override
    public Result list(Map<String, Object> params) {

        CheckUserExamForm checkUserExamForm = new CheckUserExamForm();
        //获取
        CheckExamUser checkExamUser = dao.selectById(params.get("checkExamUserId").toString());

        ExamConfig examConfig = examConfigService.selectById(checkExamUser.getExamConfigId());

        List<String> list = new ArrayList<>();
        if("1".equals(checkExamUser.getCheckUserType())){
            //审核人员
            list = userExamDao.getAuditList(examConfig.getId(),"2");
            checkUserExamForm.setList(list);
        }else {
            //普通阅卷人员
            checkUserExamForm.setList(checkExamDao.getUserExamIdByCheckUserId(params.get("checkExamUserId").toString()));

        }

        checkUserExamForm.setUserExamId(checkExamUser.getId());
        if(params.get("checkStatus")!=null){
            checkUserExamForm.setCheckStatus(params.get("checkStatus").toString());
        }
        Page<CheckUserExamForm> page = checkUserExamFormService.findPage(new Page<CheckUserExamForm>(params),checkUserExamForm);
        if("1".equals(checkExamUser.getCheckUserType())){
            List<CheckUserExamForm> checkUserExamForms = checkUserExamFormService.getAuditList(list);
            page.setList(checkUserExamForms);
            page.setCount(checkUserExamForms.size());
        }

        return Result.ok().put("page",page);
    }


}
