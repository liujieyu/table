package com.example.table.controller;


import com.example.table.pojo.WaterAuthority;
import com.example.table.pojo.WaterParam;
import com.example.table.service.WaterAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 *  权限管理前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/waterprice")
public class WaterAuthorityController {
    @Autowired
    WaterAuthorityService waterAuthorityService;
    //权限信息查询
    @ResponseBody
    @RequestMapping(value="/authorityinfo",method = RequestMethod.GET)
    public List<WaterAuthority> getAuthorityInfo(WaterParam waterParam){
        return waterAuthorityService.selectWaterAuthority(waterParam);
    }
    //新增权限信息
    @RequestMapping(value="/addauthority",method = RequestMethod.POST)
    @ResponseBody
    public String addAuthority(@RequestBody WaterAuthority pojo){
        waterAuthorityService.insertWaterAuthority(pojo);
        return "ok";
    }
    //修改权限信息
    @RequestMapping(value="/updateauthority",method = RequestMethod.POST)
    @ResponseBody
    public String updateAuthority(@RequestBody WaterAuthority pojo){
        waterAuthorityService.updateWaterAuthority(pojo);
        return "ok";
    }
    //删除权限信息
    @ResponseBody
    @RequestMapping(value="/delauthority",method = RequestMethod.GET)
    public String delAuthority(String ids){
        waterAuthorityService.deleteWaterAuthority(ids);
        return "ok";
    }
}
