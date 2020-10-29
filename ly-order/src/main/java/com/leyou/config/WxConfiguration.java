package com.leyou.config;

import com.leyou.pay.PayHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "wx")
    public PayConfig payConfig(){
        return new PayConfig();
    }

    @Bean
    public PayHelper payHelper(PayConfig payConfig){
        return new PayHelper(payConfig);
    }
}
