package com.xuhao.service;

import com.xuhao.entity.User;
import com.xuhao.entity.dto.UserDtoRegister;
import com.xuhao.utils.ResponseResult;

public interface RegisterService{

    ResponseResult register(UserDtoRegister userDtoRegister);
}
