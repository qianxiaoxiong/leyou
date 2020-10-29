package com.leyou.utils;

import lombok.Data;

import java.util.HashMap;

@Data
public class Person {

    private  Long id;
    private HashMap<Integer,User> userHashMap;
}
