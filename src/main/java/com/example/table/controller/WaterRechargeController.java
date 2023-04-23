package com.example.table.controller;


import com.example.table.pojo.RechargeShow;
import com.example.table.pojo.WaterMeter;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterRecharge;
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
}
