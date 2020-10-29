package com.leyou.client;

import cn.itcast.dto.CartDTO;
import cn.itcast.vo.PageResult;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品服务接口：
 */
@FeignClient("item-service")
public interface GoodsClient {

    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    );

    @GetMapping("/spu/detail/{spuId}")
    SpuDetail queryDetailBySpuId(@PathVariable("spuId") Long spuId);

    @GetMapping("sku/list")
    List<Sku> querySkuListBySpuId(@RequestParam("id") Long spuId);

    @GetMapping("sku/list/ids")
    public List<Sku> querySkuByIds(@RequestParam("ids") List<Long> ids);


    /**
     * 减库存
     */
    @PostMapping("stock/decrease")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOS);
}