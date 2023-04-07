package com.example.table.mapper;

import com.example.table.pojo.WaterFarmUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterSite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 农户用户信息 Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@Component
public interface WaterFarmUsersMapper extends BaseMapper<WaterFarmUsers> {
    //农户用户信息总记录数
    Integer selectFarmUserByCount(WaterParam waterParam);
    //农户用户信息分页查询
    List<WaterFarmUsers> selectFarmUserByPage(WaterParam waterParam);
    //查询渠道信息
    List<Map<String,Object>> selectCanalInfo(WaterParam waterParam);
    //判断农户用户编号是否存在
    Integer selectExistFarmCode(@Param("farmcode") String farmcode);
    //获取水协会干渠下所有支渠信息
    List<Map<String,Object>> selectAllCanalInfo(@Param("canalcode") String canalcode);
    //获取水协会下所有支渠用户
    List<WaterSite> selectCanalUser(WaterParam waterParam);
    //判断支渠用户编号是否存在
    Integer selectCanalUserExist(@Param("stcd") String stcd);
}
