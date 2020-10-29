package com.leyou.pay;


import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.leyou.config.PayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayHelper {

    @Autowired
    private PayConfig payConfig;

    private WXPay wxPay;
    private static final Logger log  = LoggerFactory.getLogger(PayHelper.class);

    public PayHelper(PayConfig payConfig) {
        wxPay = new WXPay(payConfig);
        this.payConfig = payConfig;
    }
    public String createOrder(Long orderId, Long actualPay, String desc){


        Map<String, String> data =new HashMap<>();
        // 商品描述
        data.put("body",  desc);
        // 订单号
        data.put("out_trade_no", orderId.toString());
        //货币
        data.put("fee_type", "CNY");
        //金额，单位是分
        data.put("total_fee",  actualPay.toString());
        //调用微信支付的终端IP
        data.put("spbill_create_ip", payConfig.getCreateIp());
        //回调地址   ,发给商户的地址
        data.put("notify_url",  payConfig.getNotifyUrl());
        // 交易类型为扫码支付
        data.put("trade_type", payConfig.getTradeType());

        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(data);
        } catch (Exception e) {

            log.error("【微信下单】创建预交易订单异常失败", e);
            e.printStackTrace();
        }

//        // 打印结果
//        for (Map.Entry<String, String> entry : result.entrySet()) {
//            String key = entry.getKey();
//            System.out.println(key + (key.length() >= 8 ? "\t: " : "\t\t: ") + entry.getValue());
//        }
//
//        System.out.println(" ------------------------ ");
//        // 下单成功，获取支付链接
//        String url = result.get("code_url");
//        return url;
//
//    }
//}

        // 判断通信和业务标示
        isSuccess(result);

        // 校验签名
        isValidSign(result);

        // 下单成功，获取支付链接
        String url = result.get("code_url");
        return url;

    }
    public void isSuccess(Map<String, String> result) {
        // 判断通信标示
        String returnCode = result.get("return_code");
        if("FAIL".equals(returnCode)){
            // 通信失败
            log.error("[微信下单] 微信下单通信失败,失败原因:{}", result.get("return_msg"));
            throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
        }

        // 判断业务标示
        String resultCode = result.get("result_code");
        if("FAIL".equals(resultCode)){
            // 通信失败
            log.error("[微信下单] 微信下单业务失败,错误码:{}, 错误原因:{}",
                    result.get("err_code"), result.get("err_code_des"));
            throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
        }
    }

    public void isValidSign(Map<String, String> result) {
        // 校验签名
        try {
            boolean boo = WXPayUtil.isSignatureValid(result, payConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            boolean boo2 = WXPayUtil.isSignatureValid(result, payConfig.getKey(), WXPayConstants.SignType.MD5);
            if (!boo && !boo2) {
                throw new LyException(ExceptionEnum.WX_PAY_SIGN_INVALID);
            }
        } catch (Exception e) {
            log.error("【微信支付】校验签名失败，数据：{}", result);
            throw new LyException(ExceptionEnum.WX_PAY_SIGN_INVALID);
        }
    }


}