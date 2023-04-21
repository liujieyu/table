package com.example.table.mapper;

import com.example.table.pojo.FarmStandardShow;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterRecharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 充值信息接口
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
@Component
public interface WaterRechargeMapper extends BaseMapper<WaterRecharge> {
    //获取农户用户执行水价标准信息
    List<FarmStandardShow> selectFarmStandardInfo(WaterParam waterParam);
    //获取农户用户充值之前统计信息
    List<WaterRecharge> selectFarmBuyWaterTatalInfo(WaterParam waterParam);
}
