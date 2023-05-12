package com.example.table.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.table.mapper.WaterMeterMapper;
import com.example.table.mapper.WaterRechargeMapper;
import com.example.table.mapper.WaterSiteBMapper;
import com.example.table.pojo.*;
import com.example.table.mapper.WaterFarmUsersMapper;
import com.example.table.service.WaterFarmUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  农户用户服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@Service
public class WaterFarmUsersServiceImpl extends ServiceImpl<WaterFarmUsersMapper, WaterFarmUsers> implements WaterFarmUsersService {
    @Autowired
    private WaterFarmUsersMapper waterFarmUsersMapper;
    @Autowired
    private WaterSiteBMapper waterSiteBMapper;
    @Autowired
    WaterMeterMapper waterMeterMapper;
    @Autowired
    WaterRechargeMapper waterRechargeMapper;
    //农户用户信息总记录数
    public Integer selectFarmUserByCount(WaterParam waterParam){
        return waterFarmUsersMapper.selectFarmUserByCount(waterParam);
    }
    //农户用户信息分页查询
    public List<WaterFarmUsers> selectFarmUserByPage(WaterParam waterParam){
        return waterFarmUsersMapper.selectFarmUserByPage(waterParam);
    }
    //根据ID获取农户用户信息
    public WaterFarmUsers selectFarmUserById(int id){
        return waterFarmUsersMapper.selectById(id);
    }
    //新增农户用户信息
    public void insertFarmUser(WaterFarmUsers pojo){
        waterFarmUsersMapper.insert(pojo);
        WaterMeter waterMeter=new WaterMeter();
        waterMeter.setFarmcode(pojo.getFarmcode());
        waterMeter.setWaternum(0);
        waterMeter.setReadtime(pojo.getCarddate());
        waterMeter.setWateryield(0);
        waterMeter.setYieldbase(0);
        waterMeter.setYieldfirst(0);
        waterMeter.setYieldsecond(0);
        waterMeter.setYieldthird(0);
        waterMeter.setWaterrate(0.00);
        waterMeter.setAvailable(0.00);
        waterMeter.setSurplus(0);
        waterMeter.setSyssign(pojo.getSyssign());
        waterMeterMapper.insert(waterMeter);
    }
    //修改农户用户信息
    public void updateFarmUserById(WaterFarmUsers pojo){
        waterFarmUsersMapper.updateById(pojo);
    }
    //判断农户用户下是否存在充值或者抄表信息
    public Map checkExistFarmMenterOrRecharge(String farmcodes){
        int yuancount=farmcodes.split(",").length;
        String querycon="'"+farmcodes.replace(",","','")+"'";
        QueryWrapper<WaterRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("FARMCODE in ("+querycon+")");
        Integer chargecount=waterRechargeMapper.selectCount(queryWrapper);
        QueryWrapper<WaterMeter> queryMeter=new QueryWrapper<>();
        queryMeter.apply("FARMCODE in ("+querycon+")");
        Integer metercount=waterMeterMapper.selectCount(queryMeter);
        Map map=new HashMap();
        if(chargecount>0 && metercount>yuancount){
            map.put("sign",3);
            map.put("warm","所选农户用户存在充值和抄表收费信息，是否确定将其充值记录和抄表收费记录一起删除？");
        }else if(chargecount>0){
            map.put("sign",2);
            map.put("warm","所选农户用户存在充值信息，是否确定将其充值记录一起删除？");
        }else if(metercount>yuancount){
            map.put("sign",1);
            map.put("warm","所选农户用户存在抄表收费信息，是否确定将其抄表收费记录一起删除？");
        }else{
            map.put("sign",0);
        }
        return map;
    }
    //删除农户用户信息
    public void deleteFarmUserByIds(String ids,String farmcodes,int czsign){
        String querycon="'"+farmcodes.replace(",","','")+"'";
        QueryWrapper<WaterMeter> queryMeter=new QueryWrapper<>();
        queryMeter.apply("FARMCODE in ("+querycon+")");
        waterMeterMapper.delete(queryMeter);
        if(czsign>1){
            QueryWrapper<WaterRecharge> queryWrapper = new QueryWrapper<>();
            queryWrapper.apply("FARMCODE in ("+querycon+")");
            waterRechargeMapper.delete(queryWrapper);
        }
        waterFarmUsersMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
    //查询渠道信息
    public List<Map<String,Object>> selectCanalInfo(WaterParam waterParam){
        return waterFarmUsersMapper.selectCanalInfo(waterParam);
    }
    //判断农户用户编号是否存在
    public Integer selectExistFarmCode(String farmcode){
        return waterFarmUsersMapper.selectExistFarmCode(farmcode);
    }
    //获取水协会干渠下所有支渠信息
    public List<Map<String,Object>> selectAllCanalInfo(String canalcode){
        return waterFarmUsersMapper.selectAllCanalInfo(canalcode);
    }
    //获取水协会下所有支渠用户
    public List<WaterSite> selectCanalUser(WaterParam waterParam){
        return waterFarmUsersMapper.selectCanalUser(waterParam);
    }
    //新增支渠用户
    public void insertCanalUser(WaterSite waterSite){
        waterSiteBMapper.insert(waterSite);
    }
    //修改支渠用户
    public void updateCanalUser(WaterSite waterSite){
        waterSiteBMapper.updateById(waterSite);
    }
    //根据ID获取支渠用户
    public WaterSite selectWaterSiteById(Integer id){
        return waterSiteBMapper.selectById(id);
    }
    //根据ID数组删除支渠用户
    public void deleteWaterSite(String ids){
        waterSiteBMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
    //判断支渠用户编号是否存在
    public Integer selectCanalUserExist(String stcd){
        return waterFarmUsersMapper.selectCanalUserExist(stcd);
    }
    //获取农户用户信息下拉框
    public List<Map<String,Object>> selectFarmInfoByQuery(WaterParam waterParam){
        QueryWrapper<WaterFarmUsers> queryWrapper = new QueryWrapper();
        queryWrapper.select("FARMCODE as value","FARMNAME as label")
                .eq("SYSSIGN",waterParam.getShowsign())
                .eq("ABLESIGN",1);
        if(waterParam.getCanalcode()!=null && !waterParam.getCanalcode().equals("")){
            queryWrapper.eq("CANALCODE",waterParam.getCanalcode());
        }
        queryWrapper.orderByAsc("FARMCODE");
        List<Map<String, Object>> maps = waterFarmUsersMapper.selectMaps(queryWrapper);
        return maps;
    }
}
