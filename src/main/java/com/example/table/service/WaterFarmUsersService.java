package com.example.table.service;

import com.example.table.pojo.WaterFarmUsers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterSite;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  农户用户服务类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
public interface WaterFarmUsersService extends IService<WaterFarmUsers> {
    //农户用户信息总记录数
    Integer selectFarmUserByCount(WaterParam waterParam);
    //农户用户信息分页查询
    List<WaterFarmUsers> selectFarmUserByPage(WaterParam waterParam);
    //根据ID获取农户用户信息
    WaterFarmUsers selectFarmUserById(int id);
    //新增农户用户信息
    void insertFarmUser(WaterFarmUsers pojo);
    //修改农户用户信息
    void updateFarmUserById(WaterFarmUsers pojo);
    //删除农户用户信息
    void deleteFarmUserByIds(String ids);
    //查询渠道信息
    List<Map<String,Object>> selectCanalInfo(WaterParam waterParam);
    //判断农户用户编号是否存在
    Integer selectExistFarmCode(String farmcode);
    //获取水协会干渠下所有支渠信息
    List<Map<String,Object>> selectAllCanalInfo(String canalcode);
    //获取水协会下所有支渠用户
    List<WaterSite> selectCanalUser(WaterParam waterParam);
    //新增支渠用户
    void insertCanalUser(WaterSite waterSite);
    //修改支渠用户
    void updateCanalUser(WaterSite waterSite);
    //根据ID获取支渠用户
    WaterSite selectWaterSiteById(Integer id);
    //根据ID数组删除支渠用户
    void deleteWaterSite(String ids);
    //判断支渠用户编号是否存在
    Integer selectCanalUserExist(String stcd);
}
