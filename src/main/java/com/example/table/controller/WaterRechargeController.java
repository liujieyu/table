package com.example.table.controller;


import com.example.table.pojo.*;
import com.example.table.service.WaterRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  充值、读表前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/waterprice")
public class WaterRechargeController {
    @Autowired
    WaterRechargeService waterRechargeService;
    //充值
    @ResponseBody
    @RequestMapping(value="/addRecharge",method = RequestMethod.POST)
    public Map addRecharge(@RequestBody WaterRecharge pojo){
        return waterRechargeService.insertWaterRechargeInfo(pojo);
    }
    //抄表
    @ResponseBody
    @RequestMapping(value="/addWatermeter",method = RequestMethod.POST)
    public Map addWaterMeter(@RequestBody WaterMeter pojo){
        return waterRechargeService.insertWaterMeterInfo(pojo);
    }
    //充值记录查询
    @ResponseBody
    @RequestMapping(value="/getrechargeshow",method = RequestMethod.GET)
    public Map getRechargeshow(WaterParam waterParam){
        Integer count=waterRechargeService.selectRechargShowInfoByCount(waterParam);
        List<RechargeShow> list=new ArrayList<>();
        if(count>0){
            list=waterRechargeService.selectRechargShowInfoByPage(waterParam);
        }
        Map map=new HashMap();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //收费通知查询
    @ResponseBody
    @RequestMapping(value="/getmetershow",method = RequestMethod.GET)
    public Map getMetershow(WaterParam waterParam){
        Integer count=waterRechargeService.selectMeterShowInfoByCount(waterParam);
        List<MeterShow> list=new ArrayList<>();
        if(count>0){
            list=waterRechargeService.selectMeterShowInfoByPage(waterParam);
        }
        Map map=new HashMap();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //充值统计查询
    @ResponseBody
    @RequestMapping(value="/getrechargetj",method = RequestMethod.GET)
    public Map getWaterRechargeTj(WaterParam waterParam){
        return waterRechargeService.selectSumRecharge(waterParam);
    }
    //农户用户超水信息统计
    @ResponseBody
    @RequestMapping(value="/gemeteramount",method = RequestMethod.GET)
    public Map getWaterMeterAmount(WaterParam waterParam){
        return waterRechargeService.selectMeterSumInfo(waterParam);
    }
    //农户用户节水信息统计
    @ResponseBody
    @RequestMapping(value="/gemeterback",method = RequestMethod.GET)
    public Map getWaterMeterBack(WaterParam waterParam){
        return waterRechargeService.selectMeterBackSumInfo(waterParam);
    }
}
