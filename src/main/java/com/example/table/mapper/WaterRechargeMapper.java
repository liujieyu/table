package com.example.table.mapper;

import com.example.table.pojo.FarmStandardShow;
import com.example.table.pojo.RechargeShow;
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
    //分页查询充值记录
    List<RechargeShow> selectRechargShowInfoByPage(WaterParam waterParam);
    //充值记录总记录数
    Integer selectRechargShowInfoByCount(WaterParam waterParam);
}
