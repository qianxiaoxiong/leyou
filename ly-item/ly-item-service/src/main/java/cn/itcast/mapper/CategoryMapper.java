package cn.itcast.mapper;

import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    @Select("select * from tb_category where parent_id = #{parentId}")
    @Results({
            @Result(column="id", property="id" ,jdbcType=JdbcType.BIGINT,id=true),
            @Result(column="name" ,property="name", jdbcType= JdbcType.VARCHAR ),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT ),
            @Result(column="is_parent",property="isParent" ,jdbcType=JdbcType.BIT ),
            @Result(column="sort", property="sort", jdbcType=JdbcType.INTEGER )
    })
    List<Category> select(Long parentId);
    //自定义
    List<Category> selectByIdList(List<Long> ids);
}