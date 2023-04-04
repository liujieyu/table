package com.example.table.service;

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
}
