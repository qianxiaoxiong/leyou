package com.leyou.mapper;

import com.leyou.pojo.User;
import org.apache.ibatis.annotations.*;


//@Mapper
public interface UserMapper {

    @Select("select count(*) from tb_user where id= #{id} or username = #{username} or phone =#{phone}")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//加入该注解可以保持对象后，查看对象插入id
    Integer selectCount(User user);

    @Insert("Insert into tb_user(id,username,password,phone,created,salt) values (#{id},#{username},#{password},#{phone},#{created},#{salt})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insert(User user);

    @Select("Select * from tb_user where username=#{username}")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Results(  value = {
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "created", column = "created"),
            @Result(property = "salt", column = "salt")
    }
    )
    User queryUser(@Param("username")String username);
}
