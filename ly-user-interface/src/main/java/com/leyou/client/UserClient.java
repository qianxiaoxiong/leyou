package com.leyou.client;

import com.leyou.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service")
public interface UserClient   {

    @PostMapping("login")
    @ResponseBody
    public User queryUser(@RequestParam("username") String username, @RequestParam("password")String password);
}