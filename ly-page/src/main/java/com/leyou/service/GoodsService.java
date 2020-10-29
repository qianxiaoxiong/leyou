package com.leyou.service;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecClient;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.Spu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodsService {


    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

	public Map<String, Object> loadData(Long spuId) {
        // 查询spu
        Spu spu = specClient.querySpuById(spuId);
        // 查询分类
        List<Category> categories = categoryClient.queryByIdList(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        // 查询品牌
        Brand brand = brandClient.queryById(spu.getBrandId());
        // 查询规格参数
        List<SpecGroup> specs = specClient.querySpecsByCid(spu.getCid3());

        // 封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("specs", specs);
        data.put("brand", brand);
        data.put("categories", categories);
        data.put("skus", spu.getSkus());
        data.put("detail", spu.getSpuDetail());

        // 防止重复数据
        spu.setSkus(null);
        spu.setSpuDetail(null);
        data.put("spu", spu);
        return data;
    }
}