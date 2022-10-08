package com.xuhao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuhao.entity.User;
import com.xuhao.entity.dto.UserDtoRegister;
import com.xuhao.enums.AppHttpCodeEnum;
import com.xuhao.exception.SystemException;
import com.xuhao.mapper.UserMapper;
import com.xuhao.service.RegisterService;
import com.xuhao.utils.BeanCopyUtils;
import com.xuhao.utils.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult register(UserDtoRegister userDtoRegister){
        if(!StringUtils.hasText(userDtoRegister.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!StringUtils.hasText(userDtoRegister.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }

//        LambdaQueryWrapper<UserDtoRegister> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDtoRegister::getUserName, userDtoRegister.getUserName());
        UserDtoRegister registerUser = userMapper.selectOne1(userDtoRegister.getUserName());
        if(Objects.nonNull(registerUser)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        userDtoRegister.setCreateTime(date);
        userDtoRegister.setUpdateTime(date);
        userDtoRegister.setPassword(new BCryptPasswordEncoder().encode(userDtoRegister.getPassword()));

//        User user = BeanCopyUtils.copyBean(userDtoRegister, User.class);
        userMapper.insert(userDtoRegister);
        return ResponseResult.okResult();

    }
}
