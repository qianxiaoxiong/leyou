package com.leyou.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.java.Log;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtilsTest {

    @Test
    public void test() {

//        // ' '  这是不允许的 再jackson中
//        String jsonString = "{\"id\": 13,\"name\":\"qlb\",\"sex\": 1}";
//        User qlb = new User(2L, "qlb", 1);
//        User qbx = new User(3L, "qbx", 0);
//        String s = JsonUtils.toString(qlb);
//        System.out.println("s = " + s);
//        ArrayList<User> users = new ArrayList<>();
//        users.add(qlb);
//        users.add(qbx);
//        String s1 = JsonUtils.toString(users);
//        System.out.println("s1 = " + s1);
//        //反序列化
//        User user = JsonUtils.toBean(jsonString, User.class);
//        System.out.println("user = " + user);
//
//
//        String josnArray = "[{\"id\":1, \"name\": \"qlb\",\"sex\": 1},{\"id\":2, \"name\": \"qlb\",\"sex\": 1}," +
//                "{\"id\":3, \"name\": \"qlb\",\"sex\": 1}]";
//        List<User> arrayLists = JsonUtils.toList(josnArray, User.class);
//        System.out.println("arrayLists = " + arrayLists);
//
//        HashMap<Long, User> userMap = new HashMap<>();
//        User user1 = new User(1L, "qlb", 1);
//        userMap.put(1L, user1);
//        User user2 = new User(2L, "qbx", 0);
//        userMap.put(2L, user2);
//        System.out.println("userMap = " + userMap);
//        String testJsonMapString = "{ \"1\": 1,   \"2\": 2, \"3\": 3}";
//        Map<String, Long> objectObjectMap = JsonUtils.toMap(testJsonMapString, String.class, Long.class);
//        System.out.println("objectObjectMap = " + objectObjectMap);
//        //复杂map
//        String json = "{\"0\":{\"id\":1,\"name\": \"qlb\", \"sex\":3}," +
//                "       \"1\":{\"id\":2,\"name\": \"qlb\", \"sex\":3}," +
//                "       \"2\":{\"id\":3,\"name\": \"qlb\", \"sex\":3}}";
//
//        Map<String, User> map = JsonUtils.toMap(json, String.class, User.class);
//        System.out.println("map = " + map);

        String jsonMapListString = "[{\"0\":{\"id\":1,\"name\": \"qlb\", \"sex\":3}}," +
                "       {\"1\":{\"id\":2,\"name\": \"qlb\", \"sex\":3}}," +
                "       {\"2\":{\"id\":3,\"name\": \"qlb\", \"sex\":3}}  ]";
        //protect  子类专用
        List<HashMap<String, User>> hashMaps = JsonUtils.nativeRead(jsonMapListString, new TypeReference<List<HashMap<String, User>>>() {
        });
        for (HashMap<String, User> hashMap : hashMaps) {

            System.out.println("hashMap = " + hashMap);

        }
//
//        Person person = new Person();
//        HashMap<Integer, User> userHashMap = new HashMap<>();
//        userHashMap.put(1,user);
//         person.setId(1L);
//         person.setUserHashMap(userHashMap);
//        String s2 = JSON.toJSONString(person);
//        System.out.println("s2 = " + s2);
//
//        User user3 = (User) JSON.parseObject("{ \"id\":1,\"name\":\"qlb\",\"sex\":1 }",User.class);
//        System.out.println("user3 = " + user3);

    }
}
