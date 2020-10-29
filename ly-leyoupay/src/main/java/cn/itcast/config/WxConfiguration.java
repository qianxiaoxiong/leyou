package cn.itcast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class WxConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "ly.pay")
    public OurWxPayConfig payConfig(){
        return new OurWxPayConfig();
    }

//    @Bean
//    public PayHelper payHelper(OurWxPayConfig payConfig){
//        return new PayHelper(payConfig);
//    }
}
