package cn.itcast.mapper;

import com.leyou.pojo.SpecGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecGroupMapper {

    @Select("Select count(*)  from tb_spec_group where id = #{id}")
    int deleteByPrimaryKey(Long id);
    @Select("Select * from tb_spec_group where id = #{id}")
    SpecGroup selectByPrimaryKey(Long id);
    @Select("Select * from tb_spec_group where cid = #{cid}")
    List<SpecGroup> selectBycid(@Param("cid") Long cid);

//    int updateByPrimaryKeySelective(SpecGroup record);
//
//    int updateByPrimaryKey(SpecGroup record);
//
//    int insert(SpecGroup record);

//    int insertSelective(SpecGroup record);

}