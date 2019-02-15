package com.lawschool.config;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.User;
import com.lawschool.service.UserService;
import com.lawschool.util.UtilValidate;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjw
 * @Title: 下线拦截
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018/12/2510:59
 */
public class RequestInterceptor  extends AbstractController implements HandlerInterceptor {


    @Autowired
    private UserService userService;

    public RequestInterceptor(){
        System.out.println(1);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri=request.getRequestURI();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        String loginPage=basePath+"index.html";
        boolean isRedirect=false;


        User user = ShiroUtils.getUserEntity();

        //防止用户重复登陆
        if((path+"/index.html").equals(uri))
        {
            //User user = (User) request.getSession().getAttribute("user");

            if(UtilValidate.isNotEmpty(user)){
                response.sendRedirect(basePath+"main.html");
                return false;
            }
            return true;

        }
//        else{
            if(UtilValidate.isNotEmpty(user)){
                User realUser =userService.selectUserByUserId(user.getId());
                if("0".equals(realUser.getIsOnline())){
                    //强制下线，重新登陆
                    //request.getSession().removeAttribute("user");
                    try {
                        ShiroUtils.logout();
                    }catch (Exception e){
                        response.sendRedirect(basePath+"index.html");
                        return false;
                    }


                }
            }else{
                response.sendRedirect(basePath+"index.html");
                return false;
            }

//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("333");
    }
}
