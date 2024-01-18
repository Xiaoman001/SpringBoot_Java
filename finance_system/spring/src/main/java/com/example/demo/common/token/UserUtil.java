package com.example.demo.common.token;

import com.auth0.jwt.JWT;
import com.example.demo.common.exception.ApplicationException;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.entity.User;
import com.example.demo.mapper.LoginMapper;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 获取用户信息
 * @Author guanguan
 * @Date 2021/7/5 15:03
 * @Version 1.0
 */
@Component
public class UserUtil {

    @Autowired
    private LoginMapper loginMapper;


    /**
     * 获取用户信息
     * @param request request
     * @return
     */
    public User userByUserId(HttpServletRequest request){

        String token = request.getHeader("token");
        // 执行认证
        if (token == null) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED + "," + "无token请重新登录");
        }
        // 获取 token 中的 user id
        String userId = JWT.decode(token).getClaim("userId").asString();
        User user = loginMapper.selectUserByUserId(Integer.valueOf(userId));
        if (user == null) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED + "," + "用户不存在请重新登录");
        }
        Boolean verify = JwtUtil.parseJwtToken(token);
        if (!verify) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED + "," + "登录过期请重新登录");
        }
        return user;
    }


    /**
     * 校验操作数据库是否成功
     * @param result
     */
    public static void checkException(Integer result){

        if (result < 1){
            throw new ApplicationException(HttpStatus.ERROR + "," + "操作数据库失败");
        }
    }

}
