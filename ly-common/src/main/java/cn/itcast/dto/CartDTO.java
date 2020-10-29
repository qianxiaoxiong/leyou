package cn.itcast.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long skuId;// 商品skuId
    private Integer num;// 购买数量


//
//    public CartDTO(String json) throws IOException {
//        CartDTO cartDTO = new ObjectMapper().readValue(json, CartDTO.class);
//       this.skuId = cartDTO.getSkuId();
//       this.num= cartDTO.getNum();
//
//    }
}