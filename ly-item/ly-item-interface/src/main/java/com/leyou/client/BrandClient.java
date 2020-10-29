package com.leyou.client;

import com.leyou.pojo.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("item-service")
public interface BrandClient {

    @GetMapping("brand/{id}")
    Brand queryById(@PathVariable("id") Long id);


}