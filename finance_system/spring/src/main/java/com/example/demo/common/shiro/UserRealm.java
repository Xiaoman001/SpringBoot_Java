package com.example.demo.common.shiro;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    //授权
    @Autowired
    LoginService loginService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken Token) throws AuthenticationException {
        System.out.println("执行了认证");
        UsernamePasswordToken userToken = (UsernamePasswordToken) Token;
        User userSelect = new User();
        userSelect.setUserName(userToken.getUsername());
        User user= loginService.selectUserByUserName(userSelect);
        if(user==null)
        {
            return null;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName());
        return new SimpleAuthenticationInfo("",user.getPassword(),credentialsSalt,getName());
    }
}
