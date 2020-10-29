package cn.itcast.mapper;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    List<Sku> select(@Param("spuid") Long spuid);

    List<Sku> selectByIdList(List<Long> ids);
}