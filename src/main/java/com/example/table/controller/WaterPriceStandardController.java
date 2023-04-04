package com.example.table.controller;


import com.example.table.pojo.WaterPriceStandard;
import com.example.table.service.WaterPriceStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
