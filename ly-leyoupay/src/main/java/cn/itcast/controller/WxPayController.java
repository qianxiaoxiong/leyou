package cn.itcast.controller;

import cn.itcast.config.OurWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/")
public class WxPayController {

//     @Autowired
//     private PayHelper payHelper;
    @Autowired
    private  OurWxPayConfig ourWxPayConfig;

    @GetMapping("pay")
    public Map<String,String> wxPayFunction() throws Exception {

//        String url = payHelper.createOrder(1111111L, (long) 1, "测试支付");
//        return  url;


//        WxpayParam wxpayParam = new WxpayParam();
        String notifyUrl = " http://g2zsns.natappfree.cc/wxpay/notify";  //我这里的回调地址是随便写的，到时候需要换成处理业务的回调接口

//        OurWxPayConfig ourWxPayConfig = new OurWxPayConfig();
        WXPay wxPay = new WXPay(ourWxPayConfig);




        //根据微信支付api来设置
        Map<String,String> data = new HashMap<>();
        data.put("appid",ourWxPayConfig.getAppID());
        data.put("mch_id", ourWxPayConfig.getMchID());         //商户号
        data.put("trade_type","JSAPI");                         //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
        data.put("notify_url",notifyUrl);                     //回调地址
        data.put("spbill_create_ip","127.0.0.1");             //终端ip
        data.put("total_fee","1");       //订单总金额
        data.put("fee_type","CNY");                           //默认人民币
        data.put("openid","oix-65SjGY5zdsT2M-obGUjG2ODA");
        data.put("out_trade_no","111111");   //交易号
        data.put("body","test商品");
        data.put("nonce_str","bfrhncjkfdkfd");   // 随机字符串小于32位
        String s = WXPayUtil.generateSignature(data, ourWxPayConfig.getKey());  //签名
        data.put("sign",s);

        /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口 */
        Map<String, String> respData = wxPay.unifiedOrder(data);
        if (respData.get("return_code").equals("SUCCESS")){

            //返回给APP端的参数，APP端再调起支付接口
            Map<String,String> repData = new HashMap<>();
            repData.put("appId", ourWxPayConfig.getAppID());
            repData.put("nonceStr",respData.get("nonce_str"));
            repData.put("package","prepay_id="+respData.get("prepay_id"));
            repData.put("signType","MD5");
            repData.put("timeStamp",String.valueOf(System.currentTimeMillis()/1000));
//            repData.put("mch_id", ourWxPayConfig.getMchID());
//            repData.put("prepay_id",respData.get("prepay_id"));
            String sign = WXPayUtil.generateSignature(repData, ourWxPayConfig.getKey()); //签名

            respData.put("paySign",sign);
            respData.put("timeStamp",repData.get("timeStamp"));
            respData.put("package",repData.get("package"));
            return respData;
        }
        throw new Exception(respData.get("return_msg"));
    }

}