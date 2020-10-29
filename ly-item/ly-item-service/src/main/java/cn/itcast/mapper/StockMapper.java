package cn.itcast.mapper;

import com.leyou.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StockMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);


    //自定义
    List<Stock> selectByIdList(List<Long> idList);


    //自定义
    int insertForeach(List<Stock> stockList);
    @Update("UPDATE tb_stock SET stock = stock - #{num} WHERE sku_id = #{skuId} AND stock >= #{num}")
    int decreaseStock(Long skuId, Integer num);
}