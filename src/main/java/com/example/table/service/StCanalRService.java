package com.example.table.service;

import com.example.table.pojo.StChanalR;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
