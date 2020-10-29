package com.leyou.mapper2;

import com.leyou.pojo2.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    //自定义
    int insertList(List<OrderDetail> details);
     //自定义
    List<OrderDetail> select(@Param("detail") OrderDetail detail);
}