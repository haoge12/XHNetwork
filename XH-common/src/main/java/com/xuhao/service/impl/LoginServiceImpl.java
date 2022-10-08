package com.xuhao.service.impl;

import com.xuhao.entity.LoginUser;
import com.xuhao.entity.User;
import com.xuhao.entity.vo.UserVo;
import com.xuhao.service.LoginService;
import com.xuhao.utils.JwtUtil;
import com.xuhao.utils.RedisCache;
import com.xuhao.utils.ResponseResult;
import com.xuhao.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // authenticationManager会调用UserDetailService
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("请输入用户名和密码");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject("front-token" + userId, loginUser);
        UserVo userVo = new UserVo(loginUser.getUser().getUserName(), jwt);
        return ResponseResult.okResult(userVo);
    }

    @Override
    public ResponseResult logout(){
        String userId = SecurityUtils.getUserId().toString();
        redisCache.deleteObject("front-token" + userId);
        return ResponseResult.okResult();
    }
}
