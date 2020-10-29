package cn.itcast.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import cn.itcast.mapper.SpecGroupMapper;
import cn.itcast.mapper.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SpecParamMapper specParamMapper;

    @Autowired
    private SpecGroupMapper specGroupMapper;

    public List<SpecGroup> querySpecGroup(Long cid) {
        List<SpecGroup> groups = specGroupMapper.selectBycid(cid);
        // 查询当前分类下的所有参数
        List<SpecParam> params = querySpecParams(null, cid, null, null);
        // 把param放入一个Map中，key是组id，值是组内的所有参数
        Map<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : params) {
            // 判断当前参数所属的组在map中是否存在
            if (!map.containsKey(param.getGroupId())) {
                map.put(param.getGroupId(), new ArrayList<>());
            }
            // 存param到集合
            map.get(param.getGroupId()).add(param);
        }
        // 循环存储param数据
        for (SpecGroup group : groups) {
            group.setParams(map.get(group.getId()));
        }
        return groups;


    }

    public List<SpecParam> querySpecParams(Long gid,Long cid, Boolean searching,Boolean generic) {
        //以前使用这个，现在多了2个参数，不能使用
//        List<SpecParam> specParams = specParamMapper.selectByGId(gid);
        // 查询条件
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);

        // 查询
        List<SpecParam> list = specParamMapper.select(param);
        if(CollectionUtils.isEmpty(list)){
            // 没查到
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }


}
