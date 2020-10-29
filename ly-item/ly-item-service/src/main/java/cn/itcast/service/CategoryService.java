package cn.itcast.service;

import cn.itcast.mapper.CategoryBrandMapper;
import cn.itcast.mapper.CategoryMapper;
import com.leyou.pojo.Category;
import com.leyou.pojo.CategoryBrandKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    public CategoryMapper categoryMapper;
    @Autowired
    public CategoryBrandMapper categoryBrandMapper;

    public List<Category> queryByParentId(Long parentId){
        List<Category> list = categoryMapper.select(parentId);
        return list;
    }

    /////////////
    public  List<Category> queryByBrandId(Long bid) {

        List<Category> list = categoryBrandMapper.selectList(bid);
        return  list;
    }


    /**
     * search 微服务相关接口
     */


    public List<Category> queryCategoryByIds(List<Long> ids){
        return this.categoryMapper.selectByIdList(ids);
    }
}
