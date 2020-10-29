package com.leyou.controller;

import cn.itcast.vo.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品
     *
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request) {
        PageResult<Goods> result = this.searchService.search(request);
        return ResponseEntity.ok(result);
    }
}