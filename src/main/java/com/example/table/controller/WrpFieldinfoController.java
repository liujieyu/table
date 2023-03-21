package com.example.table.controller;


import com.example.table.entity.AlarmParam;
import com.example.table.entity.MedataParam;
import com.example.table.entity.StHpC;
import com.example.table.service.WrpFieldinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-20
 */
@RestController
@RequestMapping("/fieldinfo")
public class WrpFieldinfoController {
    @Autowired
    WrpFieldinfoService wrpFieldinfoService;
    @RequestMapping(value="/common/{formid}&{fieldid}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> fieldinfo(@PathVariable("formid") String formid, @PathVariable("fieldid") String fieldid){
        return wrpFieldinfoService.selectWrpFieldinfoByFormIDAndFieldID(formid,fieldid);
    }
    @RequestMapping(value="/formcommon/{fcode}&{fnum}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> forminfo(@PathVariable("fcode") String fcode, @PathVariable("fnum") int fnum){
        return wrpFieldinfoService.selectSysFormInfoByFCodeAndFNum(fcode, fnum);
    }
    @RequestMapping(value="/borchart",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> borchart(){
        AlarmParam alarmParam=new AlarmParam();
        alarmParam.setSubsql("'611H0038HC','611H0039HC'");
        Date now=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        alarmParam.setEndtime(sdf.format(now));
        Calendar date = Calendar.getInstance();
        date.setTime(now);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 9);
        alarmParam.setBegintime(sdf.format(date.getTime()));
        return wrpFieldinfoService.selectBorchartData(alarmParam);
    }
    @RequestMapping(value="/imgdata",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> imgdata(){
        return wrpFieldinfoService.selectImgsiteData();
    }
    @RequestMapping(value="/piechart",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> piechart(){
        return wrpFieldinfoService.selectPiechartData();
    }
    @RequestMapping(value="/getrinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getRinfo(){
        Map<String,Object> map=new HashMap<>();
        map.put("typecanal",wrpFieldinfoService.selectStCanalRBystcd());
        map.put("typegate",wrpFieldinfoService.selectStWasBystcd());
        return map;
    }
    @RequestMapping(value="/videodata",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> videodata(){
        return wrpFieldinfoService.selectVideositeData();
    }
    @RequestMapping(value="/raindata",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> raindata(AlarmParam alarmParam){
        return wrpFieldinfoService.selectRainData(alarmParam);
    }
    @RequestMapping(value="/swkrdata",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> swkrdata(){
        return wrpFieldinfoService.selectWlStcpData();
    }
    @RequestMapping(value="/swkrdatabydate",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> swkrdatabydate(AlarmParam alarmParam){
        return wrpFieldinfoService.selectWlStcpDataByDate(alarmParam);
    }
    @RequestMapping(value="/gatedata",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> gatedata(AlarmParam alarmParam){
        return wrpFieldinfoService.selectGateData(alarmParam);
    }
    @RequestMapping(value="/getksrinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getksrinfo(){
        Map<String,Object> map=new HashMap<>();
        map.put("typecanal",wrpFieldinfoService.selectStCanalRBystcd());
        map.put("typerain",wrpFieldinfoService.selectRainDateInfo());
        map.put("typehd",wrpFieldinfoService.selectRiverRData());
        map.put("typesk",wrpFieldinfoService.selectRsvrRData());
        return map;
    }
    @RequestMapping(value="/gettodayinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getTodayinfo(){
        Map<String,Object> map=new HashMap<>();
        map.put("todayrain",wrpFieldinfoService.selectTodayRainData());
        map.put("todayqw",wrpFieldinfoService.selectTodayQandW());
        return map;
    }
    @RequestMapping(value="/getchangeinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getchangeinfo(){
        Map<String,Object> map=new HashMap<>();
        map.put("changeriver",wrpFieldinfoService.selectChangeRiverData());
        map.put("changersvrr",wrpFieldinfoService.selectChangeRsvrRData());
        return map;
    }
    @RequestMapping(value="/getskhourinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getskhourinfo(AlarmParam alarmParam){
        Map<String,Object> map=wrpFieldinfoService.selectStRsvrHByHours(alarmParam);
        return map;
    }
    //获取渠道站点对应渠道站点
    @RequestMapping(value="/canalinfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> canalinfo(){
        return wrpFieldinfoService.selectQudaoInfo();
    }
    //获取渠道站点信息
    @RequestMapping(value="/siteinfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> siteinfo(MedataParam medataParam){
        return wrpFieldinfoService.selectQudaoSiteInfo(medataParam);
    }
}
