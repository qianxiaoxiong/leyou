package cn.itcast.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import cn.itcast.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroup(@PathVariable("cid") Long cid){

        return   ResponseEntity.ok(specificationService.querySpecGroup(cid));
    }

    @GetMapping("/params")
    //改造这个接口
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value="gid", required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    ){
        return ResponseEntity.ok(this.specificationService.querySpecParams(gid,cid,searching,generic));
    }


    //前端规格组
    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecGroup(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }




}
