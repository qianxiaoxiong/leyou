package cn.itcast.service;


import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.mapper.BrandMapper;
import cn.itcast.mapper.CategoryBrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.BrandExample;
import com.leyou.pojo.CategoryBrandKey;
import cn.itcast.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    public BrandMapper brandMapper;

    @Autowired
    public CategoryBrandMapper categoryBrandMapper;



    public PageResult<Brand> queryBrandByPage(Integer pages, Integer rows, String key
    , String sortBy, Boolean desc){
        //分页
        PageHelper.startPage(pages, rows);
        //过滤
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();

        if(StringUtils.isNotBlank(key)){
                  criteria.multiColumnOrClause("\'%"+key+"%\'","\'"+key+"\'");
//           brandExample.or( criteria.andNameLike();
//           brandExample.or( criteria.andLetterEqualTo(key));
        }


        //排序
        if(StringUtils.isNoneBlank(sortBy)){
            String orderByClause = sortBy +( desc ? " DESC":" ASC");
            brandExample.setOrderByClause(orderByClause);
        }

        //查询
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        //判断是否为空
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }


        //解析分页结果

        PageInfo<Brand> Info = new PageInfo<>(brands);
        PageResult<Brand> brandPageResult = new PageResult<>(Info.getTotal(), Info.getList());

        return  brandPageResult;
    }

    @Transactional
    public void saveBrand(Brand brand,List<Long> cids) {
        //接受图片  另一个服务的图片地址


        //新增品牌
        brand.setId(null);
        int insert = brandMapper.insert(brand);
        if(insert == 0){
            throw new LyException(ExceptionEnum.BRANDINSERTFAIL);
        }

        //把商品分类的id写入中间表   一个品牌有多个分类
        Brand band1 = brandMapper.select(brand);
        for (int i = 0; i < cids.size(); i++) {
            int insert1 = categoryBrandMapper.insert(new CategoryBrandKey(band1.getId(), cids.get(i)));
            if(insert1  != 1) throw
            new LyException(ExceptionEnum.CATEGORYBRADINSERTFAIL);
        }
    }


    /**
     * search 微服务相关接口
     * @param id
     * @return
     */
    public Brand queryById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return  brand;
    }
}
