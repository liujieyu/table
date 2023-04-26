package com.example.table.service;

import com.example.table.pojo.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  充值读表服务类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
public interface WaterRechargeService extends IService<WaterRecharge> {
    //水费充值
    Map<String,Object> insertWaterRechargeInfo(WaterRecharge pojo);
    //抄表
    Map<String,Object> insertWaterMeterInfo(WaterMeter pojo);
    //分页查询充值记录
    List<RechargeShow> selectRechargShowInfoByPage(WaterParam waterParam);
    //充值记录总记录数
    Integer selectRechargShowInfoByCount(WaterParam waterParam);
    //分页查询收费通知
    List<MeterShow> selectMeterShowInfoByPage(WaterParam waterParam);
    //收费通知总记录数
    Integer selectMeterShowInfoByCount(WaterParam waterParam);
    //充值统计信息
    Map<String,Object> selectSumRecharge(WaterParam waterParam);
    //农户用户超水信息查询
    Map<String,Object> selectMeterSumInfo(WaterParam waterParam);
    //农户用户节水信息查询
    Map<String,Object> selectMeterBackSumInfo(WaterParam waterParam);
}
