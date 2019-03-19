package com.lawschool.filter;

import com.lawschool.beans.User;
import com.lawschool.config.ShiroUtils;
import com.lawschool.service.UserService;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ClassName: SystemLogoutFilter
 * Description: TODO
 * date: 2019/2/1514:15
 *
 * @author 王帅奇
 */
@Service
public class SystemLogoutFilter extends LogoutFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据
        Subject subject=getSubject(request,response);
        String redirectUrl=getRedirectUrl(request,response,subject);
        ServletContext context= request.getServletContext();
        User user = ShiroUtils.getUserEntity();
        if(!"checkexam".equals(user.getUserCode())) {
            int res = userService.updateUserOnlineStatus(user.getId(), "1", "0");
        }
        try {
            subject.logout();
            context.removeAttribute("error");
        }catch (SessionException e){
            e.printStackTrace();
        }
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}
