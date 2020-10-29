package com.leyou.controller;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import com.leyou.pojo.User;
import com.leyou.service.UserService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("check/{param}/{type}")
    public ResponseEntity<Boolean> chechUserData(@PathVariable("param") String Data,
                                                 @PathVariable(value = "type",required = true) Integer type){
        Boolean aBoolean = userService.checkData(Data, type);

        return ResponseEntity.ok(aBoolean) ;
    }

    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public  ResponseEntity<Void> sendVerifyCode(String Code){
        userService.sendVerifyCode(Code);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *  发送手机验证码后面就可以注册了
     */

    @PostMapping("register")
    public  ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        userService.register(user,code);
//        new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 登录功能
     */
    @PostMapping("login")
    @ResponseBody
    public User queryUser(@RequestParam("username") String username, @RequestParam("password")String password){
        User user = userService.queryUser(username,password);
        return  user;
    }
}
