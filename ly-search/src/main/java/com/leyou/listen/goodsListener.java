package com.leyou.listen;

import com.leyou.pojo.Spu;
import com.leyou.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class goodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(name ="ly.search.insert.queue", durable="true"),
            exchange = @Exchange(value = "ly.item.exchange",type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {"item.insert","item.update"}
            ))
    public void listenInsert(Long id){
        if(id != null){
            // 新增或修改
            searchService.insertOrUpdate(id);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.search.delete.queue",durable = "ture"),
            exchange = @Exchange(value = "ly.item.exchange",type = ExchangeTypes.TOPIC,ignoreDeclarationExceptions = "true"),
            key = "item.delete")
    )
            public  void listenDelete(Long id){
        // 删除
        searchService.deleteIndex(id);
    }

}
