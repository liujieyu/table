package com.example.table.controller;


import com.example.table.entity.*;
import com.example.table.service.StStsectionStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2019-12-17
 */
@RestController
@RequestMapping("/ldparamter")
public class StStsectionStationController {
    @Autowired
    StStsectionStationService stStsectionStationService;
    @ResponseBody
    @RequestMapping(value="/bzdmlist",method = RequestMethod.GET)
    public Map<String,Object> bzdmlist(LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("TYPENAME")){
            param.setOrderBy("TYPES");
        }
        Integer count=stStsectionStationService.selectStStsectionStationCount(param);
        List<StStsectionStation> list= stStsectionStationService.selectStStsectionStationByPage(param);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/parslist",method = RequestMethod.GET)
    public Map<String,Object> parslist(LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("MODELNAME")){
            param.setOrderBy("MODEL");
        }
        Integer count=stStsectionStationService.selectStParStationCount(param);
        List<StParStation> list=stStsectionStationService.selectStParStationByPage(param);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/Parshalllist",method = RequestMethod.GET)
    public Map<String,Object> Parshalllist(LvDingParam param){
        Integer count=stStsectionStationService.selectStParshallFlumeCount();
        List<StParshallFlume> list=stStsectionStationService.selectStParshallFlumeByPage(param);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/gatelist",method = RequestMethod.GET)
    public Map<String,Object> gatelist(LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("SLTPNAME")){
            param.setOrderBy("SLTP");
        }
        Integer count=stStsectionStationService.selectStGateStationCount(param);
        List<StGateStation> list=stStsectionStationService.selectStGateStationByPage(param);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
}
