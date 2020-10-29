package cn.itcast.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    public String name;
    public int age;

    @Override
    public String toString() {
        return "User = {" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        User jack = new User("jack", 21);

        String jackStr = JsonUtils.toString(jack);
        System.out.println(jackStr + "\n");

        User user = JsonUtils.toBean(jackStr, User.class);
        System.out.println(user + "\n");

        // toList
        String json = "[20, -10, 5, 15]";
        List<Integer> list = JsonUtils.toList(json, Integer.class);
        System.out.println("list = " + list + "\n");


        // toMap
        String json1 = "{\"name\":\"Jack\", \"age\": \"21\"}";

        Map<String, String> map = JsonUtils.toMap(json1, String.class, String.class);
        System.out.println("map = " + map + "\n");

        String json3 = "[{\"name\":\"Jack\", \"age\": \"21\"}, {\"name\":\"Rose\", \"age\": \"18\"}]";

        List<Map<String, String>> maps = JsonUtils.nativeRead(json3, new TypeReference<List<Map<String, String>>>() {
        });

        for (Map<String, String> map1 : maps) {
            System.out.println("map = " + map1);
        }


    }




}
