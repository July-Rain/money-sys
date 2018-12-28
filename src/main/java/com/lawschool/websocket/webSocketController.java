package com.lawschool.websocket;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.MatchSetting;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.form.QuestForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.competition.MatchSettingService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.AbstractCollection;
import java.util.List;
import java.util.Map;

/**
 *
 * @Descriptin  闯关关卡
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/websocket")
public class webSocketController extends AbstractController {

    @Autowired
    public MatchSettingService matchSettingService;

    //pk 独自一人 随机
    @RequestMapping("/pkAloneByRandom")
    public Result pkAloneByRandom(HttpServletRequest request){
        HttpSession session = request.getSession();
        User websocketUser= (User)  request.getSession().getAttribute("loginUser");//websocketUser用到的user
       // User u= (User)  request.getSession().getAttribute("user");//系统登陆人user
        User u = (User) SecurityUtils.getSubject().getPrincipal();

        if (null != websocketUser) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
        }

        // 新进来pk，需要构建一个用户//把系统的user放进来
        session.setAttribute("loginUser", u);
        // 将登录信息放入数据库，便于协查跟踪聊天者
        System.out.println("新用户诞生了：" + u);
        return Result.ok().put("user",u);
    }


    //pk 独自一人  邀请码
    @RequestMapping("/pkAloneByCode")
    public Result pkAloneByCode(String type,String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        User websocketUser= (User)  request.getSession().getAttribute("loginUser");//websocketUser用到的user
        //User u= (User)  request.getSession().getAttribute("user");//系统登陆人user
        User u = (User) SecurityUtils.getSubject().getPrincipal();

        if (null != websocketUser) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
            session.removeAttribute("joinType");
            session.removeAttribute("joinCode");
        }

        // 新进来pk，需要构建一个用户//把系统的user放进来
        session.setAttribute("loginUser", u);
        session.setAttribute("joinType", type);//加入房间的类型
        session.setAttribute("joinCode", code);//加入房间的邀请码
        // 将登录信息放入数据库，便于协查跟踪聊天者
        System.out.println("新用户诞生了：" + u);
        System.out.println("加入房间的类型：" + type);
        System.out.println("加入房间的邀请码：" + code);
        return Result.ok().put("user",u);
    }






    //pk 擂台赛
    @RequestMapping("/leitaiGame")
    public Result leitaiGame(HttpServletRequest request){
        HttpSession session = request.getSession();
        User websocketUser= (User)  request.getSession().getAttribute("loginUser");//websocketUser用到的user
        //User u= (User)  request.getSession().getAttribute("user");//系统登陆人user
        User u = (User) SecurityUtils.getSubject().getPrincipal();

        if (null != websocketUser) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
        }

        // 新进来pk，需要构建一个用户//把系统的user放进来
        session.setAttribute("loginUser", u);
        // 将登录信息放入数据库，便于协查跟踪聊天者
        System.out.println("新用户诞生了：" + u);

        //去找现在的擂主是谁
        //可能会找个null出来，说明当前没有擂主  去页面判断把
        MatchSetting matchSetting=matchSettingService.list().get(0);//只会有一条数据的  直接 干 取第一条
//        if(matchSetting.getWinId()==null ||  matchSetting.getWinId()=="")
//        {
//
//        }
        //擂台配置在这里了
        session.setAttribute("matchSetting", matchSetting);

        return Result.ok().put("user",u).put("matchSetting",matchSetting);
    }
}
