package com.leyou.pay;

import com.leyou.LyOrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyOrderApplication.class)
public class PayHelperTest {

    @Autowired
    private  PayHelper payHelper;

    @Test
    public  void test (){
//        String payUrl = payHelper.createPayUrl();
//        System.out.println(payUrl);

    }

}