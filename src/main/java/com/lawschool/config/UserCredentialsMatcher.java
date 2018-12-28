package com.lawschool.config;

import com.lawschool.beans.User;
import com.lawschool.util.MD5Util;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author zjw
 * @Title: 自定义密码验证
 * @ProjectName law_school
 * @Description: TODO
 * @date 2018-12-2713:39
 */
public class UserCredentialsMatcher  extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken,
                                      AuthenticationInfo info) {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        PrincipalCollection principals = info.getPrincipals();
        User user = (User) principals.asList().get(0);

        //进行加密
        Object tokenCredentials = MD5Util.Md5Hex(String.valueOf(token.getPassword())+user.getSalt());
        //获取账号的密码
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredentials, accountCredentials);
    }


}
