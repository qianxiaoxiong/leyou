package cn.itcast.service;

import cn.itcast.dto.CartDTO;
import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.mapper.*;
import cn.itcast.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private Logger logger = LoggerFactory.getLogger(GoodService.class);


    @Transactional
    public PageResult<Spu> querySpuPage(Integer page, Integer rows, Boolean saleable, String key) {

        //1.分页
        PageHelper.startPage(page,rows);//从第一页起
        //2.过滤条件(  根据 title
        SpuExample spuExample = new SpuExample();
        SpuExample.Criteria criteria = spuExample.createCriteria();
        criteria.andTitleLike("%"+key+"%");
        criteria.andSaleableEqualTo(saleable);
        //3.调用mapper查询
        List<Spu> spus = spuMapper.selectByExample(spuExample);

        //处理spu中的  三级id问题  以及品牌
        dealwith(spus);

       //
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);


        return new PageResult<>(spuPageInfo.getTotal(),spus);

    }
    public  void   dealwith ( List<Spu> spu){
        for (Spu spu1: spu) {

            //获取品牌名称
            Brand brand = brandMapper.select(new Brand(spu1.getBrandId()));
            spu1.setBname(brand.getName());
            //获取三级分类
            Category category1 = categoryMapper.selectByPrimaryKey(spu1.getCid1());
            String categoryName1 = category1.getName();
            Category category2 = categoryMapper.selectByPrimaryKey(spu1.getCid2());
            String categoryName2 = category2.getName();
            Category category3 = categoryMapper.selectByPrimaryKey(spu1.getCid3());
            String categoryName3 = category3.getName();
            String  cname  =  categoryName1+"/|"+categoryName2+"/|"+categoryName3;
            spu1.setCname(cname);

        }
    }


    public List<Brand> queryBrandByCategory(Long id) {

        List<Brand> brands = brandMapper.queryByCategoryId(id);
        return  brands;
    }

    //保存商品
    @Transactional
    public void saveGoods(Spu spu) {
        // 新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

        int count = spuMapper.insert(spu);
        Spu spu1 = spuMapper.selectTop();
        spu.setId(spu1.getId());
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        // 新增detail
        SpuDetail detail = spu.getSpuDetail();

        detail.setSpuId(spu1.getId());
        spuDetailMapper.insert(detail);

        // 新增sku和库存
        saveSkuAndStock(spu);

        //一旦修改商品信息 就 发送mq
        this.sendMessage(spu.getId(),"insert");

    }
    //indexes eg:"0_0_1"要到前端解析判断因为数据库varchar 要少，传输层 数据要少
    private void saveSkuAndStock(Spu spu) {
        int count;// 定义库存集合
        List<Stock> stockList = new ArrayList<>();
        // 新增sku
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            count = skuMapper.insert(sku);
            if(count != 1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stockList.add(stock);
        }

        // 批量新增库存
        count = stockMapper.insertForeach(stockList);

        if(count != stockList.size()){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    private void sendMessage(Long id, String type){
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }

    public List<Sku> querySkuByIds(List<Long> ids) {
        //查询sku
        List<Sku> skus = skuMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //查库存
        fillSkuByStock(skus,ids);
        return skus;
    }

    private void fillSkuByStock(List<Sku> skus, List<Long> idList) {
        // 查询所有库存
        List<Stock> stocks = stockMapper.selectByIdList(idList);
        if(stocks.size() != idList.size()){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        // 库存以Map形式存储，key是skuId，值是stock
        Map<Long, Integer> map = stocks.stream()
                .collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        for (Sku sku : skus) {
            sku.setStock(map.get(sku.getId()));
        }
    }


    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            // 获取id
            Long skuId = cartDTO.getSkuId();
            // 更新
            int count = stockMapper.decreaseStock(skuId, cartDTO.getNum());
            if(count != 1){
                throw new LyException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }

}
