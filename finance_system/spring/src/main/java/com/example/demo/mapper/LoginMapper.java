package com.example.demo.mapper;

import com.example.demo.entity.ChangePasswdIn;
import com.example.demo.entity.User;
import com.example.demo.entity.in.ChangeUserTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface LoginMapper {
    /**
     * 根据用户名查询用户信息
     */
    List<User> selectUserByUser(User user);

    User selectUserByUserId(Integer userId);

    Integer changePasswd(ChangePasswdIn changePasswdIn);

    Integer updateTime(ChangeUserTime changeUserTime);
}
