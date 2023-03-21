package com.example.table.mapper;

import com.example.table.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2019-12-17
 */
@Component
public interface StStsectionStationMapper extends BaseMapper<StStsectionStation> {
    //标准断面参数查询
    List<StStsectionStation> selectStStsectionStationByPage(LvDingParam param);
    //标准断面参数总记录数
    Integer selectStStsectionStationCount(LvDingParam param);
    //巴歇尔槽站点参数查询
    List<StParStation> selectStParStationByPage(LvDingParam param);
    //巴歇尔槽站点参数总记录数
    Integer selectStParStationCount(LvDingParam param);
    //标准巴歇尔槽参数查询
    List<StParshallFlume> selectStParshallFlumeByPage(LvDingParam param);
    //标准巴歇尔槽参数总记录数
    Integer selectStParshallFlumeCount();
    //水闸站点参数总记录数
    Integer selectStGateStationCount(LvDingParam param);
    //水闸站点参数查询
    List<StGateStation> selectStGateStationByPage(LvDingParam param);
}
