package com.leyou.controller;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.utils.CookieUtils;
import com.leyou.client.UserClient;
import com.leyou.config.JwtProperties;
import com.leyou.entity.UserInfo;
import com.leyou.pojo.User;
import com.leyou.service.AuthService;
import com.leyou.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties prop;
    @Autowired
    private UserClient userClient;
     //在这里登录调用用户中心的登录接口   成功则加入token  失败返回失败
    @PostMapping("login")
    public ResponseEntity<Void> authLogin(@RequestParam("username")String username, @RequestParam("password")String password,
                          HttpServletRequest req, HttpServletResponse res){
        //

        User user = userClient.queryUser(username, password);
        if( ObjectUtils.isEmpty(user)){
            throw new LyException(ExceptionEnum.BADUSERNAMEORPASSWORD);
        }

        //2.登陆成功之后，生成token
        String token =null;
        try {
             token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), prop.getPrivateKey(), prop.getExpire());

        } catch (Exception e) {
            e.printStackTrace();
            throw  new LyException(ExceptionEnum.WRITEERROR);
        }

//        String token =authService.login("zhangsan1","12456");
        //4.存入token到cookie中
           CookieUtils.newBuilder(res)
                   .httpOnly().request(req).build(prop.getCookieName(),token);



        return new ResponseEntity<>(HttpStatus.OK);


    }


    /**
     * 验证用户信息
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token,HttpServletRequest req,HttpServletResponse res) {
        try {
            // 获取token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //刷新token,从新生成30分钟的token
            String token1 = JwtUtils.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());
            CookieUtils.newBuilder(res)
                    .httpOnly().request(req).build(prop.getCookieName(),token1);

            // 成功后直接返回
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            // 抛出异常，证明token无效，直接返回401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
