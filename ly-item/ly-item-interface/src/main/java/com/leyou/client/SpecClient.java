package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 规格参数接口
 */
@FeignClient("item-service")
public interface SpecClient {


    // 查询规格参数组，及组内参数
    @GetMapping("spec/{cid}")
    List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

    @GetMapping("spec/params")
    List<SpecParam> querySpecParam(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    );

    //spu
    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);
}