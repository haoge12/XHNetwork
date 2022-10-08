package com.xuhao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhao.entity.User;
import com.xuhao.utils.ResponseResult;

public interface LoginService{

    ResponseResult login(User user);

    ResponseResult logout();
}
