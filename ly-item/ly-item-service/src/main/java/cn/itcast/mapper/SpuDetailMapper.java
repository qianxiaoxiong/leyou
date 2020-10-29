package cn.itcast.mapper;

import com.leyou.pojo.SpuDetail;

public interface SpuDetailMapper {
    int deleteByPrimaryKey(Long spuId);

    int insert(SpuDetail record);

    int insertSelective(SpuDetail record);

    SpuDetail selectByPrimaryKey(Long spuId);

    int updateByPrimaryKeySelective(SpuDetail record);

    int updateByPrimaryKey(SpuDetail record);
}