package cn.itcast.controller;

import com.leyou.pojo.Brand;
import com.leyou.pojo.CategoryBrandKey;
import cn.itcast.service.BrandService;
import cn.itcast.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    public BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandPage(
        @RequestParam(value="page",defaultValue="1")  Integer page,
        @RequestParam(value="rows",defaultValue="5")  Integer rows,
        @RequestParam(value="key", required=false) String key,
        @RequestParam(value="sortBy",required=false)  String sortBy,
        @RequestParam(value="desc",defaultValue="false") Boolean desc
    ){
         return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,key,sortBy,desc));

    }

    /**
     *    新增品牌（包括插入分类id）
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping()
    public  ResponseEntity<Void>  saveBrand(Brand brand, @RequestParam(value = "cids") List<Long> cids){
            brandService.saveBrand(brand,cids);
            return   new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * search微服务相关接口
     */
    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryById(@PathVariable("id") Long id){
        Brand brand = brandService.queryById(id);
        return ResponseEntity.ok(brand);
    }


}
