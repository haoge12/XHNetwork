package com.xuhao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuhao.entity.User;
import com.xuhao.entity.dto.UserDtoRegister;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-17 23:20:44
 */
public interface UserMapper extends BaseMapper<User> {
    void insert(UserDtoRegister userDtoRegister);

    UserDtoRegister selectOne1(String userName);

//    UserDtoRegister selectOne(Object getUserName);
}

