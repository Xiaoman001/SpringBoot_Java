package com.example.demo.controller;

import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.ChangePasswdIn;
import com.example.demo.entity.User;
import com.example.demo.entity.in.ChangeUserTime;
import com.example.demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = {"登陆管理"})
public class loginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/load")
    @ApiOperation(value = "登入")
    public WebResponse ToLogin(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try{
            subject.login(token);
            User userSelect = new User();
            userSelect.setUserName(user.getUserName());
            User correctUser = loginService.selectUserByUserName(userSelect);
            return BaseRest.buildResponse(JwtUtil.createToken(correctUser));
        }catch (UnknownAccountException e) {
            return BaseRest.buildResponse(HttpStatus.UNAUTHORIZED, "用户名不存在");
        }catch (IncorrectCredentialsException e) {
            return BaseRest.buildResponse(HttpStatus.UNAUTHORIZED, "密码错误");
        }
    }

    @PostMapping("/loginout")
    @ApiOperation(value = "登出")
    public WebResponse ToLoginOut() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
            return BaseRest.buildSuccessResponse(true, "退出登录");
        }
        else {
            return BaseRest.buildResponse(HttpStatus.UNAUTHORIZED, "请先登录");
        }
    }

    @PostMapping("/queryUser")
    @ApiOperation(value = "查询用户信息")
    public WebResponse queryUser(@RequestBody User user){

        WebResponse webResponse;
        try {
            List<User> resUsers = loginService.selectUsersByUserInfo(user);
            webResponse = BaseRest.buildResponse(resUsers);
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR ,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/changePasswd")
    @ApiOperation(value = "改变用户密码")
    public WebResponse changePasswd(@RequestBody ChangePasswdIn changePasswdIn){

        WebResponse webResponse;
        try {
            Boolean flag = loginService.changePasswd(changePasswdIn);
            if (flag){
                webResponse = BaseRest.buildSuccessResponse(true, "POST");
            }else {
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED, "密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR ,e.getMessage());
        }
        return webResponse;
    }

    @RequestMapping(value = "updateTime", method = RequestMethod.POST)
    @ApiOperation(value = "改变用户当前差旅报销审批时间")
    public WebResponse updateTime(@RequestBody ChangeUserTime changeUserTime){
        loginService.updateTime(changeUserTime);
        return BaseRest.buildSuccessResponse(true, "POST");
    }

}
