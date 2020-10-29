package cn.itcast.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.mapper.SkuMapper;
import cn.itcast.mapper.SpuDetailMapper;
import cn.itcast.mapper.SpuMapper;
import cn.itcast.mapper.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  页面静态化
 */
@Service
public class Spuservice {

    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;


    @Autowired
    private SpuMapper spuMapper;
    public Spu querySpuById(Long id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 查询spu下的sku集合
        spu.setSkus(querySkuBySpuId(id));
        // 查询detail
        spu.setSpuDetail(queryDetailBySpuId(id));
        return spu;
    }

    public SpuDetail queryDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if(spuDetail == null){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        return spuDetail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        // 查询sku
        Sku r = new Sku();
        r.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(spuId);
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        // 取出sku的所有id
        List<Long> idList = skus.stream().map(Sku::getId).collect(Collectors.toList());
        // 填充stock
        fillSkuByStock(skus, idList);
        // 查询sku对应的库存
//        for (Sku sku : skus) {
//            Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
//            if(stock == null){
//                throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
//            }
//            sku.setStock(stock.getStock());
//        }

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

}
