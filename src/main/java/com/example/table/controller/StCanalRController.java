package com.example.table.controller;
import com.example.table.pojo.*;
import com.example.table.service.StCanalRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/waterprice")
public class StCanalRController {
    @Autowired
    StCanalRService stCanalRService;
    //查询实时监测数据
    @ResponseBody
    @RequestMapping(value="/realcanalwater",method = RequestMethod.GET)
    public List<StChanalR> getRealCanalWater(WaterParam waterParam){
        List<StChanalR> list=stCanalRService.selectStCanalR1Info(waterParam);
        return list;
    }
    //分页查询历史监测数据
    @ResponseBody
    @RequestMapping(value="/hiscanalwater",method = RequestMethod.GET)
    public Map<String,Object> getHisCanalWater(WaterParam waterParam){
        Integer count=stCanalRService.selectStCanalHistoryByCount(waterParam);
        List<StChanalR> list=new ArrayList<>();
        if(count>0){
         list=stCanalRService.selectStCanalHistoryByPage(waterParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //历史监测过程图数据
    @ResponseBody
    @RequestMapping(value="/hiswaterchart",method = RequestMethod.GET)
    public List<StChanalR> getHisCanalWaterChart(WaterParam waterParam){
        waterParam.setBegincount(1);
        waterParam.setEndcount(999999);
        waterParam.setOrderBy("TM");
        waterParam.setSequence("asc");
        return stCanalRService.selectStCanalHistoryByPage(waterParam);
    }
    //实时小时水量数据
    @ResponseBody
    @RequestMapping(value="/hourcanalwater",method = RequestMethod.GET)
    public List<StHCanalC> getHourCanalWater(WaterParam waterParam){
        List<StHCanalC> list=stCanalRService.selectStHCanalCInfo(waterParam);
        return list;
    }
    //分页查询小时水量数据
    @ResponseBody
    @RequestMapping(value="/hishourwater",method = RequestMethod.GET)
    public Map<String,Object> getHishourCanalWater(WaterParam waterParam){
        Integer count=stCanalRService.selectStHCanalCHisByCount(waterParam);
        List<StHCanalC> list=new ArrayList<>();
        if(count>0){
            list=stCanalRService.selectStHCanalCHisByPage(waterParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //历史小时水量过程图数据
    @ResponseBody
    @RequestMapping(value="/hishourchart",method = RequestMethod.GET)
    public List<StHCanalC> getHishourWaterChart(WaterParam waterParam){
        waterParam.setBegincount(1);
        waterParam.setEndcount(999999);
        waterParam.setSequence("asc");
        List<StHCanalC> list=stCanalRService.selectStHCanalCHisByPage(waterParam);
        return list;
    }
    //实时日水量数据
    @ResponseBody
    @RequestMapping(value="/daycanalwater",method = RequestMethod.GET)
    public List<StDCanalC> getDayCanalWater(WaterParam waterParam){
        List<StDCanalC> list=stCanalRService.selectDCanalCInfo(waterParam);
        return list;
    }
    //分页查询日水量数据
    @ResponseBody
    @RequestMapping(value="/hisdaywater",method = RequestMethod.GET)
    public Map<String,Object> getHisdayCanalWater(WaterParam waterParam){
        Integer count=stCanalRService.selectStDCanalCHisByCount(waterParam);
        List<StDCanalC> list=new ArrayList<>();
        if(count>0){
            list=stCanalRService.selectDCanalCHisByPage(waterParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //历史日水量过程图数据
    @ResponseBody
    @RequestMapping(value="/hisdaychart",method = RequestMethod.GET)
    public List<StDCanalC> getHisdayWaterChart(WaterParam waterParam){
        waterParam.setBegincount(1);
        waterParam.setEndcount(999999);
        waterParam.setSequence("asc");
        List<StDCanalC> list=stCanalRService.selectDCanalCHisByPage(waterParam);
        return list;
    }
    //实时月水量数据
    @ResponseBody
    @RequestMapping(value="/monthcanalwater",method = RequestMethod.GET)
    public List<StMCanalC> getMonthCanalWater(WaterParam waterParam){
        List<StMCanalC> list=stCanalRService.selectMCananlCInfo(waterParam);
        return list;
    }
    //分页查询月水量数据
    @ResponseBody
    @RequestMapping(value="/hismonthwater",method = RequestMethod.GET)
    public Map<String,Object> getHisMonthCanalWater(WaterParam waterParam){
        Integer count=stCanalRService.selectMCananlCHisByCount(waterParam);
        List<StMCanalC> list=new ArrayList<>();
        if(count>0){
            list=stCanalRService.selectMCananlCHisByPage(waterParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //历史月水量过程图数据
    @ResponseBody
    @RequestMapping(value="/hismonthchart",method = RequestMethod.GET)
    public List<StMCanalC> getHismonthWaterChart(WaterParam waterParam) {
        waterParam.setBegincount(1);
        waterParam.setEndcount(999999);
        waterParam.setSequence("asc");
        List<StMCanalC> list=stCanalRService.selectMCananlCHisByPage(waterParam);
        return list;
    }
}
