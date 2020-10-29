package com.leyou.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import com.leyou.client.UserClient;
import com.leyou.config.JwtProperties;
import com.leyou.entity.UserInfo;
import com.leyou.pojo.User;
import com.leyou.utils.JwtUtils;
import com.netflix.client.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AuthService {



    @Autowired
    private JwtProperties prop;


    public String login(String username, String password) {
        //1先根据用户名查询

         User user =null;
      return "aa";

    }
}
