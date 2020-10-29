package cn.itcast.controller;

import cn.itcast.dto.CartDTO;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import cn.itcast.service.GoodService;
import cn.itcast.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodController {


    @Autowired
    private GoodService goodService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>>  queryBySpuPage(
            @RequestParam(value = "page",defaultValue = "1")  Integer page,
            @RequestParam(value = "rows",defaultValue = "5")  Integer rows,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,//默认是1上架
            @RequestParam(value = "key", required = false) String key){


        return  ResponseEntity.ok(goodService.querySpuPage(page,rows,saleable,key));
    }

    /**
     * 根据分类查询品牌
     */
    @GetMapping("brand/cid/{cid}")
    public ResponseEntity<List<Brand>>  queryBrandByCategory(@PathVariable("cid") Long id){

         return  ResponseEntity.ok(this.goodService.queryBrandByCategory(id));
    }


    /**
     * 新增商品
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        this.goodService.saveGoods(spu);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

      //sku ids
    @GetMapping("sku/list/ids")
    public  ResponseEntity<List<Sku>> querySkuByIds(@RequestParam("ids") List<Long> ids){
        return  ResponseEntity.ok(goodService.querySkuByIds(ids));
    }



    /**
     * 减库存
     */
    @PostMapping("stock/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDTO> cartDTOS){
        goodService.decreaseStock(cartDTOS);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
