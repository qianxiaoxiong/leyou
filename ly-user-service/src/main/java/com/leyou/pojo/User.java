//package com.leyou.pojo;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import org.hibernate.validator.constraints.Length;
//import org.springframework.data.annotation.Id;
//
//import javax.validation.constraints.Pattern;
//import java.util.Date;
//
//@Data
//public class User {
//
//    private Long id;
//    @Length(min=4, max=30,message = "用户名只能在4-30位之间")
//    private String username;// 用户名
//    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
//    @JsonIgnore    //Json序列化是或略字段
//    private String password;// 密码
//    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
//    private String phone;// 电话
//
//    private Date created;// 创建时间
//
//    @JsonIgnore
//    private String salt;// 密码的盐值
//}