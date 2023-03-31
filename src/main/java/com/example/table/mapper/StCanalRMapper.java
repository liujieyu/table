package com.example.table.mapper;

import com.example.table.pojo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 渠道水情监测DAO
 * @author liujieyu
 * @since 2023-03-23
 */
@Component
public interface StCanalRMapper extends BaseMapper<StChanalR> {
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
    //实时月水量数据
    List<StMCanalC> selectMCananlCInfo(WaterParam waterParam);
    //历史月水量分析总记录数
    Integer selectMCananlCHisByCount(WaterParam waterParam);
    //历史月水量分析分页
    List<StMCanalC> selectMCananlCHisByPage(WaterParam waterParam);
}
