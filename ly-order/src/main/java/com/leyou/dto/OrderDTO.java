package com.leyou.dto;

import cn.itcast.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @NotNull
    private Long addressId; // 收获人地址id
    @NotNull
    private Integer paymentType;// 付款类型
    @NotNull
    private List<CartDTO> carts;// 订单详情


    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public List<CartDTO> getCarts() {
        return carts;
    }

    public void setCarts(List<CartDTO> carts) {
        this.carts = carts;
    }
}