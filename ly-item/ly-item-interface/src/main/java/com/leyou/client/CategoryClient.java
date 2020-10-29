package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 商品分类服务接口：
 */
@FeignClient("item-service")
public interface CategoryClient {
    @GetMapping("category/list/ids")
    List<Category> queryByIdList(@RequestParam("ids") List<Long> ids);
}