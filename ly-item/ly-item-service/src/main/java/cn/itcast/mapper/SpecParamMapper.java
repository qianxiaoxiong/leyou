package cn.itcast.mapper;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SpecParamMapper {


    @Select("select * from tb_spec_param where group_id = #{gid}")
    @Results( {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT,id = true),
            @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.BIGINT)
    })
    //以前使用gid查询，才能在不用了，多了2个参数，变成了param,用下面的select方法
    List<SpecParam> selectByGId(Long gid);


    @Select("Select * from tb_spec_param where  group_id = #{groupId} or cid =#{cid} or searching =#{searching} ")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
    @Results(value = {
            @Result(property = "groupId",column = "group_id")
    })
    List<SpecParam> select(SpecParam param);
}