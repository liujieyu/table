package com.example.table.controller;


import com.example.table.entity.AlarmParam;
import com.example.table.entity.StGateDd;
import com.example.table.service.StGateDdService;
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
 * @since 2019-10-10
 */
@RestController
@RequestMapping("/gatedd")
public class StGateDdController {
    @Autowired
    StGateDdService stGateDdService;
    @ResponseBody
    @RequestMapping(value="/xhxx",method = RequestMethod.GET)
    public Map<String,Object> getGateDd(AlarmParam alarmParam){
        Integer count=stGateDdService.selectStGateDdByCount(alarmParam);
        List<StGateDd> list=stGateDdService.selectStGateDdByPage(alarmParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/xhchart",method = RequestMethod.GET)
    public List<StGateDd> getDdChart(AlarmParam alarmParam){
        return stGateDdService.selectStGateDdByType(alarmParam);
    }
}
