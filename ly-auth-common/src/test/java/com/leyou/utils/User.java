package com.leyou.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private  Long id;
    private  String name;
    private  Integer sex;

}
