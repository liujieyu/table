package com.example.table.service;

import com.example.table.pojo.PriceShow;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterPriceStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  执行水价标准服务类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
public interface WaterPriceStandardService extends IService<WaterPriceStandard> {
    //根据年份查询执行水价标准
    WaterPriceStandard selectWaterPriceStandardByYear(int year);
    //新增执行水价标准
    int insertWaterPriceStandard(WaterPriceStandard pojo);
    //修改执行水价标准
    void updateWaterPriceStandard(WaterPriceStandard pojo);
    //删除执行水价标准
    void deleteWaterPriceStandard(int id);
    //获取当前执行水价标准
    WaterPriceStandard selectWaterPriceStandardNow();
    //支渠超额标准
    List<WaterPriceStandard> selectCanalWaterAboveStandard();
    //支渠回购标准
    List<WaterPriceStandard> selectCanalWaterBackStandard();
    //农户用户超额标准
    List<WaterPriceStandard> selectFarmWaterAboveStandard();
    //农户用户回购标准
    List<WaterPriceStandard> selectFarmWaterBackStandard();
    //支渠用户超额用水加价
    List<PriceShow> selectCanalStandardShowAbove(WaterParam waterParam);
    //支渠用户节约用水回购
    List<PriceShow> selectCanalStandardShowBack(WaterParam waterParam);
    //农户用户超额用水加价(回购)总记录数
    Integer selectFarmStandardShowByCount(WaterParam waterParam);
    //农户用户超额用水加价分页查询
    List<PriceShow> selectFarmStandardShowAboveByPage(WaterParam waterParam);
    //农户用户节约用水回购分页查询
    List<PriceShow> selectFarmStandardShowBackByPage(WaterParam waterParam);
}
