package com.example.table.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.table.pojo.WaterAuthority;
import com.example.table.mapper.WaterAuthorityMapper;
import com.example.table.pojo.WaterParam;
import com.example.table.service.WaterAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-11
 */
@Service
public class WaterAuthorityServiceImpl extends ServiceImpl<WaterAuthorityMapper, WaterAuthority> implements WaterAuthorityService {
    @Autowired
    WaterAuthorityMapper waterAuthorityMapper;
    //权限信息查询
    public List<WaterAuthority> selectWaterAuthority(WaterParam waterParam){
        QueryWrapper<WaterAuthority> queryWrapper = new QueryWrapper();
        queryWrapper.eq("SYSSIGN",waterParam.getShowsign());
        if(waterParam.getLoginname()!=null && !waterParam.getLoginname().equals("")){
            queryWrapper.like("LOGINNAME",waterParam.getLoginname());
        }
        List<WaterAuthority> list=waterAuthorityMapper.selectList(queryWrapper);
        return list;
    }
    //新增权限信息
    public void insertWaterAuthority(WaterAuthority pojo){
        waterAuthorityMapper.insert(pojo);
    }
    //修改权限信息
    public void updateWaterAuthority(WaterAuthority pojo){
        waterAuthorityMapper.updateById(pojo);
    }
    //删除权限信息
    public void deleteWaterAuthority(String ids){
        waterAuthorityMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
    //判断登录账号是否存在(用户名密码是否存在)
    public Integer selectWaterAuthorityCount(WaterParam waterParam){
        QueryWrapper<WaterAuthority> queryWrapper = new QueryWrapper();
        queryWrapper.eq("SYSSIGN",waterParam.getShowsign());
        queryWrapper.eq("LOGINNAME",waterParam.getLoginname());
        if(waterParam.getLoginpass()!=null && !waterParam.getLoginpass().equals("")){
            queryWrapper.eq("LOGINPASS",waterParam.getLoginpass());
        }
        return waterAuthorityMapper.selectCount(queryWrapper);
    }
}
