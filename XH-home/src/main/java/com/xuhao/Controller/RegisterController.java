package com.xuhao.Controller;

import com.xuhao.entity.User;
import com.xuhao.entity.dto.UserDtoRegister;
import com.xuhao.service.RegisterService;
import com.xuhao.utils.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RegisterController{
    @Autowired
    private RegisterService registerService;
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserDtoRegister userDtoRegister){
        return registerService.register(userDtoRegister);
    }
}
