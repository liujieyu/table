package com.example.table.mapper;

import com.example.table.pojo.PriceShow;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterPriceStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@Component
public interface WaterPriceStandardMapper extends BaseMapper<WaterPriceStandard> {
    //获取最新执行水价标准
    List<WaterPriceStandard> selectWaterPriceStandardNow();
    //支渠超额标准
    List<WaterPriceStandard> selectCanalWaterAboveStandard();
    //支渠回购标准
    List<WaterPriceStandard> selectCanalWaterBackStandard();
    //农户用户超额标准
    List<WaterPriceStandard> selectFarmWaterAboveStandard();
    //农户用户回购标准
    List<WaterPriceStandard> selectFarmWaterBackStandard();
    //支渠用户超额用水加价
    List<PriceShow> selectCanalStandardShowAbove(WaterParam waterParam);
    //支渠用户节约用水回购
    List<PriceShow> selectCanalStandardShowBack(WaterParam waterParam);
    //农户用户超额用水加价(回购)总记录数
    Integer selectFarmStandardShowByCount(WaterParam waterParam);
    //农户用户超额用水加价分页查询
    List<PriceShow> selectFarmStandardShowAboveByPage(WaterParam waterParam);
    //农户用户节约用水回购分页查询
    List<PriceShow> selectFarmStandardShowBackByPage(WaterParam waterParam);
}
