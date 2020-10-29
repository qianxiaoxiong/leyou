package com.leyou.service;

import cn.itcast.utils.JsonUtils;
import cn.itcast.vo.PageResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecClient;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SpecClient specClient;
     Logger logger = LoggerFactory.getLogger(SearchService.class);

    public PageResult<Goods> search(SearchRequest request) {
        String key = request.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (StringUtils.isBlank(key)) {
            return null;
        }

        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 1、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","skus","subTitle"}, null));
        // 2、对key进行全文检索查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));
        // 3、分页
        // 准备分页参数
        int page = request.getPage();
        int size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));


        // 4、查询，获取结果
        Page<Goods> pageInfo = this.goodsRepository.search(queryBuilder.build());

        // 封装结果并返回
        return new PageResult<>(pageInfo.getTotalElements(), pageInfo.getContent());
    }

    public void insertOrUpdate(Long id) {
        try{

        // 查询spu
        Spu spu = this.specClient.querySpuById(id);
        if(spu == null){
            logger.error("索引对应的spu不存在，spuId：{}", id);
            // 抛出异常，让消息回滚
            throw new RuntimeException();
        }
//        Goods goods = buildGoods(spu);
        // 保存数据到索引库
//        goodsRepository.save(goods);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteIndex(Long id) {
        goodsRepository.deleteById(id);
    }

//    public Goods buildGoods(Spu spu) {
//        Long spuId = spu.getId();
//        // 查询分类
//        String names = categoryClient.queryAllLevelByLevel3(spu.getCid3())
//                .stream().map(Category::getName)
//                .collect(Collectors.joining(" "));
//        // 查询品牌
//        Brand brand = brandClient.queryById(spu.getBrandId());
//        // 1 搜索条件的拼接，包含：分类、品牌、标题等
//        String all = spu.getTitle() + names + brand.getName();// TODO 拼接规格
//
//        // 2 查询sku
//        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
//        List<Map<String,Object>> skuMap = new ArrayList<>();
//        for (Sku sku : skuList) {
//            Map<String,Object> map = new HashMap<>();
//            map.put("id", sku.getId());
//            map.put("title", sku.getTitle());
//            map.put("price", sku.getPrice());
//            map.put("image", StringUtils.substringBefore(sku.getImages(), ","));
//            skuMap.add(map);
//        }
//        // 3 sku的所有价格集合
//        Set<Long> price = skuList.stream().map(Sku::getPrice).collect(Collectors.toSet());
//
//        // 4 可用来搜索的规格参数, 规格key来自于spec_param, 规格值在spu_detail中
//        Map<String,Object> specs = new HashMap<>();
//        // 4.1 查spec_param
//        List<SpecParam> specParams = specClient.queryParams(null, spu.getCid3(), true);
//        // 4.2 查询spu_detail
//        SpuDetail spuDetail = goodsClient.queryDetailBySpuId(spuId);
//        // 4.2.1 通用规格
//        Map<Long, String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, String.class);
//        // 4.2.2 特有规格
//        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(),
//                new TypeReference<Map<Long, List<String>>>() {});
//
//        for (SpecParam param : specParams) {
//            // 获取规格参数名称，作为key
//            String key = param.getName();
//            Object value = null;
//            // 判断是否是通用参数
//            if(param.getGeneric()){
//                // 通用规格，去genericSpec取
//                value = genericSpec.get(param.getId());
//                // 判断是否需要分断（判断是否是数值类型）
//                if(param.getNumeric()){
//                    value = chooseSegment(value.toString(), param);
//                }
//                value = StringUtils.isBlank(value.toString()) ? "其它" : value;
//            }else{
//                // 特有规格，去specialSpec取
//                value = specialSpec.get(param.getId());
//            }
//            value = value == null ? "其它" : value;
//            specs.put(key, value);
//        }
//
//        Goods goods = new Goods();
//        goods.setSubTitle(spu.getSubTitle());
//        goods.setSpecs(specs);// 可用来搜索的规格参数
//        goods.setSkus(JsonUtils.toString(skuMap));// spu下的所有SKU的集合
//        goods.setPrice(price);// sku的所有价格集合
//        goods.setId(spuId);
//        goods.setCreateTime(spu.getCreateTime());
//        goods.setCid3(spu.getCid3());
//        goods.setBrandId(spu.getBrandId());
//        goods.setAll(all);// 搜索条件的拼接，包含：分类、品牌、标题等
//        return goods;
//    }

//    private String chooseSegment(String value, SpecParam p) {
//        double val = NumberUtils.toDouble(value);
//        String result = "其它";
//        // 保存数值段
//        for (String segment : p.getSegments().split(",")) {
//            String[] segs = segment.split("-");
//            // 获取数值范围
//            double begin = NumberUtils.toDouble(segs[0]);
//            double end = Double.MAX_VALUE;
//            if(segs.length == 2){
//                end = NumberUtils.toDouble(segs[1]);
//            }
//            // 判断是否在范围内
//            if(val >= begin && val < end){
//                if(segs.length == 1){
//                    result = segs[0] + p.getUnit() + "以上";
//                }else if(begin == 0){
//                    result = segs[1] + p.getUnit() + "以下";
//                }else{
//                    result = segment + p.getUnit();
//                }
//                break;
//            }
//        }
//        return result;
//    }



}