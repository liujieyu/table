package com.example.table.service.impl;

import com.example.table.pojo.StChanalR;
import com.example.table.mapper.StCanalRMapper;
import com.example.table.pojo.StHCanalC;
import com.example.table.pojo.WaterParam;
import com.example.table.service.StCanalRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-03-23
 */
@Service
public class StCanalRServiceImpl extends ServiceImpl<StCanalRMapper, StChanalR> implements StCanalRService {
    @Autowired
    StCanalRMapper stCanalRMapper;
    //查询实时监测数据
    public List<StChanalR> selectStCanalR1Info(WaterParam waterParam){
        return stCanalRMapper.selectStCanalR1Info(waterParam);
    }
    //查询监测历史数据总记录数
    public Integer selectStCanalHistoryByCount(WaterParam waterParam){
        return stCanalRMapper.selectStCanalHistoryByCount(waterParam);
    }
    //分页查询监测历史数据
    public List<StChanalR> selectStCanalHistoryByPage(WaterParam waterParam){
        return stCanalRMapper.selectStCanalHistoryByPage(waterParam);
    }
    //实时小时水量数据
    public List<StHCanalC> selectStHCanalCInfo(WaterParam waterParam){
        return stCanalRMapper.selectStHCanalCInfo(waterParam);
    }
    //历史小时水量分析总记录数
    public Integer selectStHCanalCHisByCount(WaterParam waterParam){
        return stCanalRMapper.selectStHCanalCHisByCount(waterParam);
    }
    //历史小时水量分析分页
    public List<StHCanalC> selectStHCanalCHisByPage(WaterParam waterParam){
        return stCanalRMapper.selectStHCanalCHisByPage(waterParam);
    }
}
