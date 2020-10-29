package com.leyou.pojo;

import lombok.Data;


@Data
public class OrderDetail {

    private Long id;

    private Long orderId;// 订单id

    private Long skuId;// 商品id

    private Integer num;// 商品购买数量

    private String title;// 商品标题

    private Long price;// 商品单价

    private String ownSpec;// 商品规格数据

    private String image;// 图片
}