package cn.itcast.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

import java.io.IOException;
import java.util.List;

public class JsonUtils {

     public  static  final ObjectMapper mapper = new ObjectMapper();

     private  static   final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

     public  static String  toString(Object obj){
          if( obj == null){
              return  null;
          }else {
              if(obj.getClass() == String.class){
                    return  (String) obj;
              }else {
                  try {
                      return  mapper.writeValueAsString(obj);
                  } catch (JsonProcessingException e) {
                      logger.error("Json序列化出错：" +obj,e);
                      e.printStackTrace();
                  }
              }

          }
          return "If 语句出错";

     }

     //反序列化

    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }


   public  static  <K,V>  Map<K,V> toMap(String json, Class<K> kClass, Class<V> vClass){
       try {
          return mapper.readValue(json,mapper.getTypeFactory().constructMapType(Map.class,kClass,vClass));
       } catch (Exception e) {
           logger.error("json解析出错：" + json, e);
           e.printStackTrace();
           return  null;
       }
   }


   public  static  <T> T nativeRead(String json , TypeReference<T> type){

       try {
           return  mapper.readValue(json,type);
       } catch (Exception e) {
           logger.error("json解析出错： "+ json, e);
           e.printStackTrace();
           return null;
       }
   }


}
