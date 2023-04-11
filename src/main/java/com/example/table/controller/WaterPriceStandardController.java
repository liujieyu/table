package com.example.table.controller;


import com.example.table.pojo.PriceShow;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterPriceStandard;
import com.example.table.service.WaterPriceStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  执行水价标准前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/waterprice")
public class WaterPriceStandardController {
    @Autowired
    WaterPriceStandardService waterPriceStandardService;
    //根据年份查询执行水价标准信息
    @ResponseBody
    @RequestMapping(value="/waterpriceinfo",method = RequestMethod.GET)
    public WaterPriceStandard getWaterPriceStandard(int year){
        return waterPriceStandardService.selectWaterPriceStandardByYear(year);
    }
    //当前执行水价标准信息
    @ResponseBody
    @RequestMapping(value="/nowwaterprice",method = RequestMethod.GET)
    public WaterPriceStandard getWaterPriceStandardnow(){
        return waterPriceStandardService.selectWaterPriceStandardNow();
    }
    //新增执行水价标准信息
    @RequestMapping(value="/addwaterprice",method = RequestMethod.POST)
    @ResponseBody
    public int addWaterPrice(@RequestBody WaterPriceStandard pojo){
        int id = waterPriceStandardService.insertWaterPriceStandard(pojo);
        return id;
    }
    //修改执行水价标准信息
    @RequestMapping(value="/updatewaterprice",method = RequestMethod.POST)
    @ResponseBody
    public String updateWaterPrice(@RequestBody WaterPriceStandard pojo){
        waterPriceStandardService.updateWaterPriceStandard(pojo);
        return "ok";
    }
    //删除执行水价标准信息
    @ResponseBody
    @RequestMapping(value="/delwaterprice",method = RequestMethod.GET)
    public String deleteWaterPrice(int id){
        waterPriceStandardService.deleteWaterPriceStandard(id);
        return "ok";
    }
    //支渠超额标准
    @ResponseBody
    @RequestMapping(value="/getcanalabovestand",method = RequestMethod.GET)
    public List<WaterPriceStandard> getCanalAboveStandard(){
        return waterPriceStandardService.selectCanalWaterAboveStandard();
    }
    //支渠回购标准
    @ResponseBody
    @RequestMapping(value="/getcanalbackstand",method = RequestMethod.GET)
    public List<WaterPriceStandard> getCanalBackStandard(){
        return waterPriceStandardService.selectCanalWaterBackStandard();
    }
    //农户用户超额标准
    @ResponseBody
    @RequestMapping(value="/getfarmabovestand",method = RequestMethod.GET)
    public List<WaterPriceStandard> getFarmAboveStandard(){
        return waterPriceStandardService.selectFarmWaterAboveStandard();
    }
    //农户用户回购标准
    @ResponseBody
    @RequestMapping(value="/getfarmabackstand",method = RequestMethod.GET)
    public List<WaterPriceStandard> getFarmBackStandard(){
        return waterPriceStandardService.selectFarmWaterBackStandard();
    }
    //支渠用户超额用水加价
    @ResponseBody
    @RequestMapping(value="/getcanalabovewater",method = RequestMethod.GET)
    public List<PriceShow> getCanalAboveWater(WaterParam waterParam){
        return waterPriceStandardService.selectCanalStandardShowAbove(waterParam);
    }
    //支渠用户节约用水回购
    @ResponseBody
    @RequestMapping(value="/getcanalbackwater",method = RequestMethod.GET)
    public List<PriceShow> getCanalBackWater(WaterParam waterParam){
        return waterPriceStandardService.selectCanalStandardShowBack(waterParam);
    }
    //农户用户超额用水加价分页查询
    @ResponseBody
    @RequestMapping(value="/getfarmabovewater",method = RequestMethod.GET)
    public Map getFarmAboveWater(WaterParam waterParam){
        Integer count=waterPriceStandardService.selectFarmStandardShowByCount(waterParam);
        List<PriceShow> list=new ArrayList<>();
        if(count>0){
            list=waterPriceStandardService.selectFarmStandardShowAboveByPage(waterParam);
        }
        Map<String,Object> map=new HashMap();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //农户用户节约用水回购分页查询
    @ResponseBody
    @RequestMapping(value="/getfarmbackwater",method = RequestMethod.GET)
    public Map getFarmBackWater(WaterParam waterParam){
        Integer count=waterPriceStandardService.selectFarmStandardShowByCount(waterParam);
        List<PriceShow> list=new ArrayList<>();
        if(count>0){
            list=waterPriceStandardService.selectFarmStandardShowBackByPage(waterParam);
        }
        Map<String,Object> map=new HashMap();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
}
