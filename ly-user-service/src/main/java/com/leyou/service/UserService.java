package com.leyou.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import com.leyou.mapper.UserMapper;
import com.leyou.pojo.User;
import com.leyou.tool.CodecUtils;
import com.leyou.tool.NumberUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate  stringRedisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;


    public Boolean checkData(String data, Integer type) {
        // 查询条件
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.REQUESTARGSEXCEPTION);
        }
        // 查询是否存在
        Integer count = userMapper.selectCount(user);
        return  count == 0;
    }

    /**
     * 1.将验证码存到redis 设置验证码存活时间
     * 2.利用消息队列将验证码 发到阿里大鱼 进而发送给用户
     * @param phone
     */
    public void sendVerifyCode(String phone) {
        //1生成随机验证码
        String code = NumberUtils.generateCode(6);
        //2.保存验证码  5分钟
        stringRedisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        //3.发送mq
        HashMap<String, String> iphoneMap = new HashMap<>();
        iphoneMap.put(phone,code);
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code", iphoneMap);
    }

    public void register(User user, String code) {
        //校验验证码
        String s = stringRedisTemplate.opsForValue().get(user.getPhone());

        if(StringUtils.isEmpty(s) || !s.equals(code)){
            throw  new LyException(ExceptionEnum.REGIRSTERFAIL);
        }
        //将用户信息加盐后保存到数据库
            //1 加盐
        String salt = CodecUtils.generateSalt();
           //2 加盐加密
        String md5Password = CodecUtils.md5Hex(user.getPassword(), salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
            //3.写入数据库
                    //补充字段
        user.setId(null);
        user.setCreated(new Date());
        int insert = userMapper.insert(user);
        if( insert != 1){
           new LyException(ExceptionEnum.WRITEERROR);
        }
        //4.注册成功后删除redis中的key value
        stringRedisTemplate.delete(user.getPassword());
    }


    public User queryUser(String username, String password) {

        User user = userMapper.queryUser(username);
        if(ObjectUtils.isEmpty(user)){
            throw new LyException(ExceptionEnum.BADUSERNAMEORPASSWORD);
        }

        String s = CodecUtils.md5Hex(password,user.getSalt());
        boolean b = !s.equals(user.getPassword());
        if(b){
            throw new LyException(ExceptionEnum.BADUSERNAMEORPASSWORD);
        }
        return user;
    }
}
