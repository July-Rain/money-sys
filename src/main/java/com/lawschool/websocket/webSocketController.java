package com.lawschool.websocket;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.beans.competition.RecruitCheckpointConfiguration;
import com.lawschool.form.QuestForm;
import com.lawschool.service.TestQuestionService;
import com.lawschool.service.competition.RecruitCheckpointConfigurationService;
import com.lawschool.util.Result;
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


    //pk 独自一人 随机
    @RequestMapping("/pkAloneByRandom")
    public Result list(HttpServletRequest request){
        HttpSession session = request.getSession();
        User websocketUser= (User)  request.getSession().getAttribute("loginUser");//websocketUser用到的user
        User u= (User)  request.getSession().getAttribute("user");//系统登陆人user

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


}
