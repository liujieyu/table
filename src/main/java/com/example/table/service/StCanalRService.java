package com.example.table.service;

import com.example.table.pojo.StChanalR;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.table.pojo.StDCanalC;
import com.example.table.pojo.StHCanalC;
import com.example.table.pojo.WaterParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2023-03-23
 */
public interface StCanalRService extends IService<StChanalR> {
    //查询实时监测数据
    List<StChanalR> selectStCanalR1Info(WaterParam waterParam);
    //查询监测历史数据总记录数
    Integer selectStCanalHistoryByCount(WaterParam waterParam);
    //分页查询监测历史数据
    List<StChanalR> selectStCanalHistoryByPage(WaterParam waterParam);
    //实时小时水量数据
    List<StHCanalC> selectStHCanalCInfo(WaterParam waterParam);
    //历史小时水量分析总记录数
    Integer selectStHCanalCHisByCount(WaterParam waterParam);
    //历史小时水量分析分页
    List<StHCanalC> selectStHCanalCHisByPage(WaterParam waterParam);
    //实时日水量数据
    List<StDCanalC> selectDCanalCInfo(WaterParam waterParam);
    //历史日水量分析总记录数
    Integer selectStDCanalCHisByCount(WaterParam waterParam);
    //历史日水量分析分页
    List<StDCanalC> selectDCanalCHisByPage(WaterParam waterParam);
}
