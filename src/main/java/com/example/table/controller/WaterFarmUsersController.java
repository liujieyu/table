package com.example.table.controller;


import com.example.table.pojo.WaterFarmUsers;
import com.example.table.pojo.WaterParam;
import com.example.table.pojo.WaterSite;
import com.example.table.service.WaterFarmUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  农户用户信息前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/waterprice")
public class WaterFarmUsersController {
    @Autowired
    WaterFarmUsersService waterFarmUsersService;
    //分页查询农户用户信息
    @ResponseBody
    @RequestMapping(value="/farmuserpage",method = RequestMethod.GET)
    public Map<String,Object> getFarmUserList(WaterParam waterParam){
        Integer count=waterFarmUsersService.selectFarmUserByCount(waterParam);
        List<WaterFarmUsers> list=new ArrayList<>();
        if(count>0){
            list=waterFarmUsersService.selectFarmUserByPage(waterParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //根据ID获取农户用户信息
    @ResponseBody
    @RequestMapping(value="/farmuserbyid",method = RequestMethod.GET)
    public WaterFarmUsers getFarmUserById(int id){
        return waterFarmUsersService.selectFarmUserById(id);
    }
    //新增农户用户信息
    @RequestMapping(value="/addfarmuser",method = RequestMethod.POST)
    @ResponseBody
    public String addFarmUser(@RequestBody WaterFarmUsers pojo){
        waterFarmUsersService.insertFarmUser(pojo);
        return "ok";
    }
    //修改农户用户信息
    @RequestMapping(value="/updatefarmuser",method = RequestMethod.POST)
    @ResponseBody
    public String updateFarmUser(@RequestBody WaterFarmUsers pojo){
        waterFarmUsersService.updateFarmUserById(pojo);
        return "ok";
    }
    //删除农户用户信息
    @ResponseBody
    @RequestMapping(value="/delfarmuser",method = RequestMethod.GET)
    public String deleteFarmUser(String ids){
        waterFarmUsersService.deleteFarmUserByIds(ids);
        return "ok";
    }
    //获取渠道信息
    @ResponseBody
    @RequestMapping(value="/getcanalinfo",method = RequestMethod.GET)
    public List<Map<String,Object>> getCanalInfo(WaterParam waterParam){
        return waterFarmUsersService.selectCanalInfo(waterParam);
    }
    //判断农户用户编码是否存在
    @ResponseBody
    @RequestMapping(value="/checkfarmcode",method = RequestMethod.GET)
    public Map<String,Object> checkFarmCode(String farmcode){
        Integer existsign=waterFarmUsersService.selectExistFarmCode(farmcode);
        Map<String,Object> map=new HashMap<>();
        if(existsign>0){
            map.put("checksign","yes");
            map.put("warning","该农户用户编码已存在！");
        }else{
            map.put("checksign","no");
        }
        return map;
    }
    //获取水协会干渠下所有支渠信息
    @ResponseBody
    @RequestMapping(value="/getallcanal",method = RequestMethod.GET)
    public List<Map<String,Object>> getAllCanal(String canalcode){
        return waterFarmUsersService.selectAllCanalInfo(canalcode);
    }
    //获取水协会下所有支渠用户
    @ResponseBody
    @RequestMapping(value="/getwatersite",method = RequestMethod.GET)
    public List<WaterSite> getWaterSiteUsers(WaterParam waterParam){
        return waterFarmUsersService.selectCanalUser(waterParam);
    }
    //新增支渠用户
    @RequestMapping(value="/addcanaluser",method = RequestMethod.POST)
    @ResponseBody
    public String addCanalSiteUser(@RequestBody WaterSite pojo){
        waterFarmUsersService.insertCanalUser(pojo);
        return "ok";
    }
    //修改支渠用户
    @RequestMapping(value="/updatecanaluser",method = RequestMethod.POST)
    @ResponseBody
    public String updateCanalSiteUser(@RequestBody WaterSite pojo){
        waterFarmUsersService.updateCanalUser(pojo);
        return "ok";
    }
    //根据ID获取支渠用户
    @ResponseBody
    @RequestMapping(value="/canaluserbyid",method = RequestMethod.GET)
    public WaterSite getCanalUserById(int id){
        return waterFarmUsersService.selectWaterSiteById(id);
    }
    //根据ID数组删除支渠用户
    @ResponseBody
    @RequestMapping(value="/delcanaluser",method = RequestMethod.GET)
    public String deleteCanalUser(String ids){
        waterFarmUsersService.deleteWaterSite(ids);
        return "ok";
    }
    //判断支渠用户编码是否存在
    @ResponseBody
    @RequestMapping(value="/checkcanalcode",method = RequestMethod.GET)
    public Map<String,Object> checkCanalCode(String stcd){
        Integer existsign=waterFarmUsersService.selectCanalUserExist(stcd);
        Map<String,Object> map=new HashMap<>();
        if(existsign>0){
            map.put("checksign","yes");
            map.put("warning","该支渠用户编码已存在！");
        }else{
            map.put("checksign","no");
        }
        return map;
    }
}
