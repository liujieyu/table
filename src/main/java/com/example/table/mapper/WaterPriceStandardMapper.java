package com.example.table.mapper;

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
    List<WaterPriceStandard> selectWaterPriceStandardNow();
}
