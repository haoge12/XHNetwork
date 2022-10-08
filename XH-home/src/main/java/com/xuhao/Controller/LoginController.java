package com.xuhao.Controller;

import com.xuhao.entity.User;
import com.xuhao.enums.AppHttpCodeEnum;
import com.xuhao.service.LoginService;
import com.xuhao.utils.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController

public class LoginController{

    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (Objects.isNull(user)){
            throw new RuntimeException("请输入用户名和密码");
        }
        return loginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
