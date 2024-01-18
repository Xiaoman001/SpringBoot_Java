package com.example.demo.service;

import com.example.demo.common.jwt.PasswordGenerateUtil;
import com.example.demo.entity.ChangePasswdIn;
import com.example.demo.entity.User;
import com.example.demo.entity.in.ChangeUserTime;
import com.example.demo.mapper.LoginMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public User selectUserByUserName(User user){
        List<User> res = loginMapper.selectUserByUser(user);
        if (res.size()>0){
            return res.get(0);
        }else return null;
    }

    public List<User> selectUsersByUserInfo(User user){
        return loginMapper.selectUserByUser(user);
    }

    public Boolean changePasswd(ChangePasswdIn changePasswdIn){
        String oldPasswd = PasswordGenerateUtil.getPassword(changePasswdIn.getUserName(), changePasswdIn.getOriginPasswd());
        User userSelect = new User();
        userSelect.setUserName(changePasswdIn.getUserName());
        List<User> resUser = loginMapper.selectUserByUser(userSelect);
        if (resUser.size()>0){
            User user = loginMapper.selectUserByUser(userSelect).get(0);
            if (user.getPassword().equals(oldPasswd)){
                changePasswdIn.setNewPasswd(PasswordGenerateUtil.getPassword(changePasswdIn.getUserName(), changePasswdIn.getNewPasswd()));
                Integer res = loginMapper.changePasswd(changePasswdIn);
                return res == 1;
            }
            else return false;
        }else return false;
    }

    public Boolean updateTime(ChangeUserTime changeUserTime){
        return loginMapper.updateTime(changeUserTime) == 1;
    }
}
