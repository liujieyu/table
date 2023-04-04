package com.example.table.service.impl;

import com.example.table.pojo.WaterPriceStandard;
import com.example.table.mapper.WaterPriceStandardMapper;
import com.example.table.service.WaterPriceStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  执行水价标准服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@Service
public class WaterPriceStandardServiceImpl extends ServiceImpl<WaterPriceStandardMapper, WaterPriceStandard> implements WaterPriceStandardService {
    @Autowired
    private WaterPriceStandardMapper waterPriceStandardMapper;
    //根据年份查询执行水价标准
    public WaterPriceStandard selectWaterPriceStandardByYear(int year){
        Map<String,Object> mapcon=new HashMap<>();
        mapcon.put("PRICEYR",year);
        List<WaterPriceStandard> list = waterPriceStandardMapper.selectByMap(mapcon);
        if(list==null || list.size()==0){
            return new WaterPriceStandard();
        }else{
            return list.get(0);
        }
    }
    //获取当前执行水价标准
    public WaterPriceStandard selectWaterPriceStandardNow(){
        List<WaterPriceStandard> list = waterPriceStandardMapper.selectWaterPriceStandardNow();
        if(list==null || list.size()==0){
            return new WaterPriceStandard();
        }else{
            return list.get(0);
        }
    }
    //新增执行水价标准
    public int insertWaterPriceStandard(WaterPriceStandard pojo){
        return waterPriceStandardMapper.insert(pojo);
    }
    //修改执行水价标准
    public void updateWaterPriceStandard(WaterPriceStandard pojo){
        waterPriceStandardMapper.updateById(pojo);
    }
    //删除执行水价标准
    public void deleteWaterPriceStandard(int id){
        waterPriceStandardMapper.deleteById(id);
    }
}
