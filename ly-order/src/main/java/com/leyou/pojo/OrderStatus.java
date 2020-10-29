package com.leyou.pojo;

import lombok.Data;

import java.util.Date;


@Data
public class OrderStatus {

    private Long orderId;
    
    private Integer status;

    private Date createTime;// 创建时间

    private Date paymentTime;// 付款时间

    private Date consignTime;// 发货时间

    private Date endTime;// 交易结束时间

    private Date closeTime;// 交易关闭时间

    private Date commentTime;// 评价时间
}