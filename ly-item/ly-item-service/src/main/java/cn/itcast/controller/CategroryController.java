package cn.itcast.controller;

import com.leyou.pojo.Category;
import com.leyou.pojo.CategoryBrandKey;
import cn.itcast.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategroryController {

    @Autowired
    public CategoryService categoryService;


    /**
     * 根据父节点查询商品
     */
    @RequestMapping("list")
    public ResponseEntity<List<Category>>  queryByParentId(@RequestParam(value = "pid",defaultValue = "0") Long parentId){

           return  ResponseEntity.ok(   categoryService.queryByParentId(parentId) );
    }

    /**
     *  新增品牌时，数据回显（比如你新增了一个品牌，在打开时候就有以前的数据）
     */
    @GetMapping("bid/{bid}")
    public  ResponseEntity<List<Category>>  queryByBrandId(@PathVariable("bid") Long bid){
        List<Category>  list = categoryService.queryByBrandId(bid);
        if(list == null || list.size() <1){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }


    /**
     * search微服务   相关接口
     */


    /**
     * 根据商品分类id查询分类
     * @param ids 要查询的分类id集合
     * @return 多个名称的集合
     */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryByIds(@RequestParam("ids") List<Long> ids){
        List<Category> list = this.categoryService.queryCategoryByIds(ids);
        return ResponseEntity.ok(list);
    }
}
