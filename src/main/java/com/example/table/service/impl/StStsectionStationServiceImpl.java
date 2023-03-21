package com.example.table.service.impl;

import com.example.table.entity.*;
import com.example.table.mapper.StStsectionStationMapper;
import com.example.table.service.StStsectionStationService;
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
 * @since 2019-12-17
 */
@Service
public class StStsectionStationServiceImpl extends ServiceImpl<StStsectionStationMapper, StStsectionStation> implements StStsectionStationService {
    @Autowired
    StStsectionStationMapper stStsectionStationMapper;
    //标准断面参数查询
    public List<StStsectionStation> selectStStsectionStationByPage(LvDingParam param){
        return stStsectionStationMapper.selectStStsectionStationByPage(param);
    }
    //标准断面参数总记录数
    public Integer selectStStsectionStationCount(LvDingParam param){
        return stStsectionStationMapper.selectStStsectionStationCount(param);
    }
    //巴歇尔槽站点参数查询
    public List<StParStation> selectStParStationByPage(LvDingParam param){
        return stStsectionStationMapper.selectStParStationByPage(param);
    }
    //巴歇尔槽站点参数总记录数
    public Integer selectStParStationCount(LvDingParam param){
        return stStsectionStationMapper.selectStParStationCount(param);
    }
    //标准巴歇尔槽参数查询
    public List<StParshallFlume> selectStParshallFlumeByPage(LvDingParam param){
        return stStsectionStationMapper.selectStParshallFlumeByPage(param);
    }
    //标准巴歇尔槽参数总记录数
    public Integer selectStParshallFlumeCount(){
        return stStsectionStationMapper.selectStParshallFlumeCount();
    }
    //水闸站点参数总记录数
    public Integer selectStGateStationCount(LvDingParam param){
        return stStsectionStationMapper.selectStGateStationCount(param);
    }
    //水闸站点参数查询
    public List<StGateStation> selectStGateStationByPage(LvDingParam param){
        return stStsectionStationMapper.selectStGateStationByPage(param);
    }
}
