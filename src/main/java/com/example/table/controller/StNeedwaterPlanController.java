package com.example.table.controller;


import com.example.table.entity.LvDingParam;
import com.example.table.entity.StNeedwaterPlan;
import com.example.table.entity.WrpIrrbsmanageinstitution;
import com.example.table.service.StNeedwaterPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/waterplan")
public class StNeedwaterPlanController {
    @Autowired
    StNeedwaterPlanService stNeedwaterPlanService;
    //需水计划
    @ResponseBody
    @RequestMapping(value="/planinfo",method = RequestMethod.GET)
    public List<StNeedwaterPlan> getwaterplan(LvDingParam param){
        return stNeedwaterPlanService.selectWaterPlan(param);
    }
    //管理机构
    @RequestMapping(value="/gljginfo/{types}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> gljginfo(@PathVariable("types") String types){
        return stNeedwaterPlanService.selectGljgInfo(types);
    }
    //用水额定计划
    @ResponseBody
    @RequestMapping(value="/quotainfo",method = RequestMethod.GET)
    public List<StNeedwaterPlan> getwaterquota(LvDingParam param){
        return stNeedwaterPlanService.selectWaterquota(param);
    }
    //支渠用水管理
    @ResponseBody
    @RequestMapping(value="/useinfo",method = RequestMethod.GET)
    public List<StNeedwaterPlan> useinfo(LvDingParam param){
        return stNeedwaterPlanService.selectUsewaterByCanalcode(param);
    }
    //管理局-支渠树结构
    @ResponseBody
    @RequestMapping(value="/managetree",method = RequestMethod.GET)
    public Map<String,Object> managetree(LvDingParam param){
        WrpIrrbsmanageinstitution obj=stNeedwaterPlanService.selectOrganCanalTree(param);
        Map<String,Object> map=new HashMap<>();
        map.put("tree",obj);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/managedemo",method = RequestMethod.GET)
    public List<WrpIrrbsmanageinstitution> managedemo(LvDingParam param){
        return stNeedwaterPlanService.selectOrganCanalDemo(param);
    }
}
