package com.leyou.controller;


import com.leyou.dto.OrderDTO;
import com.leyou.pojo2.Order;
import com.leyou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderDTO orderDTO){
        Long orderId = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    /**
     * 根据id查询订单
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> queryOrderById(@PathVariable("orderId")Long orderId){
        return ResponseEntity.ok(orderService.queryOrderById(orderId));
    }
    //生成支付连接
    @GetMapping("url/{orderId}")
    public ResponseEntity<String>  getNotifyUrl(@PathVariable("orderId") Long id){
        return  ResponseEntity.ok(orderService.getNotifyUrl(id));
    }
    @GetMapping("test")
    public String test(){
        System.out.println("test");
        return "这是我首次使用内网穿透NatAPP";
    }




}
