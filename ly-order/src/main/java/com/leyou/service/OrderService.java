package com.leyou.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.utils.IdWorker;
import com.leyou.client.GoodsClient;
import cn.itcast.dto.CartDTO;
import com.leyou.cusenum.OrderStatusEnum;
import com.leyou.dto.OrderDTO;
import com.leyou.mapper2.OrderDetailMapper;
import com.leyou.mapper2.OrderMapper;
import com.leyou.mapper2.OrderStatusMapper;
import com.leyou.pay.PayHelper;
import com.leyou.pojo2.OrderDetail;
import com.leyou.pojo2.OrderStatus;
import com.leyou.pojo.Sku;
import com.leyou.pojo.constant.AddressClient;
import com.leyou.pojo.dto.AddressDTO;
import com.leyou.pojo2.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper detailMapper;

    @Autowired
    private OrderStatusMapper statusMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private  GoodsClient goodsClient;

    @Autowired
    private PayHelper payHelper;

    public Order queryOrderById(Long id) {
        // 查询订单
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order == null) {
            // 不存在
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }

        // 查询订单详情
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(id);
        List<OrderDetail> details = detailMapper.select(detail);
        if(CollectionUtils.isEmpty(details)){
            throw new LyException(ExceptionEnum.ORDER_DETAIL_NOT_FOUND);
        }
        order.setOrderDetails(details);

        // 查询订单状态
        OrderStatus orderStatus = statusMapper.selectByPrimaryKey(id);
        if (orderStatus == null) {
            // 不存在
            throw new LyException(ExceptionEnum.ORDER_STATUS_NOT_FOUND);
        }
        order.setOrderStatus(orderStatus);
        return order;
    }

    public String  getNotifyUrl(Long orderId) {
        // 查询订单
        Order order = queryOrderById(orderId);
        // 判断订单状态
        Integer status = order.getOrderStatus().getStatus();
        if(status != OrderStatusEnum.UN_PAY.value()){
            // 订单状态异常
            throw new LyException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        // 支付金额
        Long actualPay = /*order.getActualPay()*/ 1L;
        // 商品描述
        OrderDetail detail = order.getOrderDetails().get(0);
        String desc = detail.getTitle();
        return payHelper.createOrder(orderId, actualPay, desc);
    }




    @Transactional
    public Long createOrder(OrderDTO orderDTO) {
        // 0 准备订单数据
        Order order = new Order();
        // 1）准备订单编号
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);

        // 2）买家信息
//        UserInfo user = LoginInterceptor.getLoginUser();
        order.setUserId(14L);
        order.setBuyerNick("zs");
        order.setBuyerRate(false);

        // 3）收货人信息
        Long addressId = orderDTO.getAddressId();
        AddressDTO address = AddressClient.findById(addressId);
        order.setReceiver(address.getName());
        order.setReceiverZip(address.getZipCode());
        order.setReceiverState(address.getState());
        order.setReceiverMobile(address.getPhone());
        order.setReceiverDistrict(address.getDistrict());
        order.setReceiverCity(address.getCity());
        order.setReceiverAddress(address.getAddress());

        // 4）金额相关
        List<CartDTO> carts = orderDTO.getCarts();
        // 处理cartDTO为map
        Map<Long, Integer> map = carts.stream()
                .collect(Collectors.toMap(CartDTO::getSkuId, CartDTO::getNum));
        // 查询商品
        List<Long> idList = carts.stream().map(CartDTO::getSkuId).collect(Collectors.toList());
        List<Sku> skuList = goodsClient.querySkuByIds(idList);
        // 准备订单详情集合，
        List<OrderDetail> details = new ArrayList<>();
        // 定义总价
        long total = 0;
        for (Sku sku : skuList) {
            Integer num = map.get(sku.getId());
            // 计算总金额
            total += sku.getPrice() * num;
            // 封装订单详情
            OrderDetail detail = new OrderDetail();
            BeanUtils.copyProperties(sku, detail);
            detail.setSkuId(sku.getId());
            detail.setOrderId(orderId);
            detail.setNum(num);
            detail.setImage(StringUtils.substringBefore(sku.getImages(), ","));
            details.add(detail);
        }
        order.setTotalPay(total);
        order.setPostFee(0L);
        order.setActualPay(total + order.getPostFee() - 0);// TODO 最后应减去优惠价格
        order.setPaymentType(orderDTO.getPaymentType());

        // 1 新增订单

        order.setCreateTime(new Date());
        int count = orderMapper.insert(order);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_ORDER_ERROR);
        }

        // 2 新增订单详情
        count = detailMapper.insertList(details);
        if (count != details.size()) {
            throw new LyException(ExceptionEnum.INSERT_ORDER_ERROR);
        }

        // 3 新增订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderStatusEnum.UN_PAY.value());
        orderStatus.setCreateTime(new Date());
        count = statusMapper.insertSelective(orderStatus);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_ORDER_ERROR);
        }

        // 4 减库存
        goodsClient.decreaseStock(carts);
        return orderId;
    }
}
