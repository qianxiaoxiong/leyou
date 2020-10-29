package com.leyou.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.utils.JsonUtils;
import com.leyou.entity.UserInfo;
import com.leyou.filter.LoginInterceptor;
import com.leyou.pojo.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "ly:cart:uid:";

    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {
        // 获取当前用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();

        // 获取商品id
        String hashKey = cart.getSkuId().toString();
        // 获取数量
        int num = cart.getNum();

        // 获取hash操作的对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        // 判断要添加的商品是否存在
        if(hashOps.hasKey(hashKey)){
            // 存在，修改数量
            cart = JsonUtils.toBean(hashOps.get(hashKey).toString(), Cart.class);
            cart.setNum(num + cart.getNum());
        }
        // 写入redis
        hashOps.put(hashKey, JsonUtils.toString(cart));
    }


    public List<Cart> queryCartList() {
        // 获取用户信息
        String key = KEY_PREFIX + LoginInterceptor.getLoginUser().getId();
        // 判断是否是第一次
        if(!redisTemplate.hasKey(key)){
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        // 获取hash操作，并绑定key
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        // 获取用户的所有购物车
        List<Cart> list = hashOps.values().stream()
                .map(s -> JsonUtils.toBean(s, Cart.class))
                .collect(Collectors.toList());
        return list;
    }
}