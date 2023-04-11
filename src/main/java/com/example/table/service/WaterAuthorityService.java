package com.example.table.service;

import com.example.table.pojo.WaterAuthority;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.table.pojo.WaterParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-11
 */
public interface WaterAuthorityService extends IService<WaterAuthority> {
    //权限信息查询
    List<WaterAuthority> selectWaterAuthority(WaterParam waterParam);
    //新增权限信息
    void insertWaterAuthority(WaterAuthority pojo);
    //修改权限信息
    void updateWaterAuthority(WaterAuthority pojo);
    //删除权限信息
    void deleteWaterAuthority(String ids);
}
