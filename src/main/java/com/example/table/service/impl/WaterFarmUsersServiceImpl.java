package com.example.table.service.impl;

import com.example.table.mapper.WaterSiteBMapper;
import com.example.table.pojo.WaterFarmUsers;
import com.example.table.mapper.WaterFarmUsersMapper;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterSite;
import com.example.table.service.WaterFarmUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  农户用户服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@Service
public class WaterFarmUsersServiceImpl extends ServiceImpl<WaterFarmUsersMapper, WaterFarmUsers> implements WaterFarmUsersService {
    @Autowired
    private WaterFarmUsersMapper waterFarmUsersMapper;
    @Autowired
    private WaterSiteBMapper waterSiteBMapper;
    //农户用户信息总记录数
    public Integer selectFarmUserByCount(WaterParam waterParam){
        return waterFarmUsersMapper.selectFarmUserByCount(waterParam);
    }
    //农户用户信息分页查询
    public List<WaterFarmUsers> selectFarmUserByPage(WaterParam waterParam){
        return waterFarmUsersMapper.selectFarmUserByPage(waterParam);
    }
    //根据ID获取农户用户信息
    public WaterFarmUsers selectFarmUserById(int id){
        return waterFarmUsersMapper.selectById(id);
    }
    //新增农户用户信息
    public void insertFarmUser(WaterFarmUsers pojo){
        waterFarmUsersMapper.insert(pojo);
    }
    //修改农户用户信息
    public void updateFarmUserById(WaterFarmUsers pojo){
        waterFarmUsersMapper.updateById(pojo);
    }
    //删除农户用户信息
    public void deleteFarmUserByIds(String ids){
        waterFarmUsersMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
    //查询渠道信息
    public List<Map<String,Object>> selectCanalInfo(WaterParam waterParam){
        return waterFarmUsersMapper.selectCanalInfo(waterParam);
    }
    //判断农户用户编号是否存在
    public Integer selectExistFarmCode(String farmcode){
        return waterFarmUsersMapper.selectExistFarmCode(farmcode);
    }
    //获取水协会干渠下所有支渠信息
    public List<Map<String,Object>> selectAllCanalInfo(String canalcode){
        return waterFarmUsersMapper.selectAllCanalInfo(canalcode);
    }
    //获取水协会下所有支渠用户
    public List<WaterSite> selectCanalUser(WaterParam waterParam){
        return waterFarmUsersMapper.selectCanalUser(waterParam);
    }
    //新增支渠用户
    public void insertCanalUser(WaterSite waterSite){
        waterSiteBMapper.insert(waterSite);
    }
    //修改支渠用户
    public void updateCanalUser(WaterSite waterSite){
        waterSiteBMapper.updateById(waterSite);
    }
    //根据ID获取支渠用户
    public WaterSite selectWaterSiteById(Integer id){
        return waterSiteBMapper.selectById(id);
    }
    //根据ID数组删除支渠用户
    public void deleteWaterSite(String ids){
        waterSiteBMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
    //判断支渠用户编号是否存在
    public Integer selectCanalUserExist(String stcd){
        return waterFarmUsersMapper.selectCanalUserExist(stcd);
    }
}
