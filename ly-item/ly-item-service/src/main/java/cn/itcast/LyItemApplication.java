package cn.itcast;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.itcast.mapper")
public class LyItemApplication {
    public static void main(String[] args) {

        SpringApplication.run(LyItemApplication.class,args);
    }

    @Bean
    public RestTemplate getRest(){
        return  new RestTemplate();
    }
}
