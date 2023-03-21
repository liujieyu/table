package com.example.table.controller;


import com.example.table.entity.*;
import com.example.table.service.SmsBoxsendedService;
import com.example.table.service.StAlarminfoService;
import com.example.table.service.WrpFieldinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
 * @since 2019-09-19
 */
@RestController
@RequestMapping("/alarm")
public class StAlarminfoController {
    @Autowired
    StAlarminfoService stAlarminfoService;
    @Autowired
    SmsBoxsendedService smsBoxsendedService;
    @Autowired
    WrpFieldinfoService wrpFieldinfoService;
    //站点预警信息查询
    @ResponseBody
    @RequestMapping(value="/sitealarm",method = RequestMethod.GET)
    public Map<String,Object> getAlarmInfo(AlarmParam alarmParam){
        if(alarmParam.getOrderBy()!=null){
            if(alarmParam.getOrderBy().equals("STNM")){
                alarmParam.setOrderBy("b.STNM");
            }
            if(alarmParam.getOrderBy().equals("TYPENAME")){
                alarmParam.setOrderBy("(select stuff((select ',' + type from ST_STBPRP_F f where f.STCD =b.STCD for xml path('')),1,1,''))");
            }
            if(alarmParam.getOrderBy().equals("TMSTR")){
                alarmParam.setOrderBy("a.TM");
            }
            if(alarmParam.getOrderBy().equals("ALARMNAME")){
                alarmParam.setOrderBy("a.Alarm");
            }
            if(alarmParam.getOrderBy().equals("CONTENTS")){
                alarmParam.setOrderBy("a.CONTENTS");
            }
            if(alarmParam.getOrderBy().equals("MV")){
                alarmParam.setOrderBy("a.MV");
            }
            if(alarmParam.getOrderBy().equals("AlARMV")){
                alarmParam.setOrderBy("a.AlARMV");
            }
            if(alarmParam.getOrderBy().equals("ADNM")){
                alarmParam.setOrderBy("b.ADDVCD");
            }
        }
        Integer count=stAlarminfoService.selectWarmInfoByCount(alarmParam);
        List<StAlarminfo> list=stAlarminfoService.selectWarmInfoByPage(alarmParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //站点预警信息详情
    @RequestMapping(value="/siteinfo/{stcd}&{tm}",method = RequestMethod.GET)
    @ResponseBody
    public StAlarminfo getInfoByStcd(@PathVariable("stcd") String stcd,@PathVariable("tm") String tm){
        //平台级别 1 省级 2 市级 3 县级
        int plantsign=3;
       return stAlarminfoService.selectWarmInfoBySTCD(stcd,tm,plantsign);
    }
    //短信预警查询
    @ResponseBody
    @RequestMapping(value="/boxsended",method = RequestMethod.GET)
    public Map<String,Object> getBoxsended(AlarmParam alarmParam){
        Integer count=smsBoxsendedService.selectSmsBoxsendedByCount(alarmParam);
        List<SmsBoxsended> list=smsBoxsendedService.selectSmsBoxsendedByPage(alarmParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    @RequestMapping(value="/fieldinfo/{fcode}&{fnum}",method = RequestMethod.GET)
    @ResponseBody
    //预警等级数据获取
    public List<String> getFieldInfo(@PathVariable("fcode") String fcode, @PathVariable("fnum") Integer fnum){
        return smsBoxsendedService.selectFieldInfoByParam(fcode,fnum);
    }
    //接收短信查询
    @ResponseBody
    @RequestMapping(value="/boxreceived",method = RequestMethod.GET)
    public Map<String,Object> getBoxreceived(AlarmParam alarmParam){
        Integer count=smsBoxsendedService.selectSmsBoxreceivedByCount(alarmParam);
        List<SmsBoxreceived> list=smsBoxsendedService.selectSmsBoxreceivedByPage(alarmParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //发送短信查询
    @ResponseBody
    @RequestMapping(value="/boxsending",method = RequestMethod.GET)
    public Map<String,Object> getBoxSending(AlarmParam alarmParam){
        Integer count;
        List<SmsBoxsended> list;
        List<Map<String,Object>> tables=wrpFieldinfoService.selectSysFormInfoByFCodeAndFNum("FSJG20190927",alarmParam.getSign());
        if(tables.size()>1){
            String subsql="";
            for(int i=0;i<tables.size();i++){
                Map<String,Object> map=tables.get(i);
                String table=map.get("value").toString();
                String result=map.get("label").toString();
                subsql+="select id,appid,'"+result+"' as result,'"+table+"' as tablename,receiver,sendtime,content,addtion1,addtion2,addtion3 from "+table;
                if(i<tables.size()-1){
                    subsql+=" union all ";
                }
            }
            alarmParam.setSubsql(subsql);
            count=smsBoxsendedService.selectSmsBoxSendAllByCount(alarmParam);
            list=smsBoxsendedService.selectSmsBoxSendAllByPage(alarmParam);
        }else{
            Map<String,Object> map=tables.get(0);
            alarmParam.setTable(map.get("value").toString());
            alarmParam.setResult(map.get("label").toString());
            count=smsBoxsendedService.selectSmsBoxSendTableByCount(alarmParam);
            list=smsBoxsendedService.selectSmsBoxSendTableByPage(alarmParam);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //发送短信详情
    @RequestMapping(value="/boxinfo/{table}&{id}",method = RequestMethod.GET)
    @ResponseBody
    public SmsBoxsended getBoxDetail(@PathVariable("table") String table,@PathVariable("id") int id){
        return smsBoxsendedService.selectSmsBoxSendByDetail(table,id);
    }
    //值班安排查询
    @RequestMapping(value="/dutyplan/{date}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDutyplay(@PathVariable("date") String date){
        return smsBoxsendedService.selectDutyPlanByDateAndCols(date);
    }
    //值班安排详情
    @RequestMapping(value="/plandetail/{date}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> dutyplanDetail(@PathVariable("date") String date){
        return smsBoxsendedService.selectDutyPlanDetail(date);
    }
    //短信发送人员信息
    @ResponseBody
    @RequestMapping(value="/getman",method = RequestMethod.GET)
    public List<WrpMan> getWrpman(WrpMan wrpMan){
        return smsBoxsendedService.selectWrpManByTypes(wrpMan);
    }
    //新增短信
    @RequestMapping(value="/addmessage",method = RequestMethod.POST)
    @ResponseBody
    public String addSendmessage(@RequestBody AlarmParam alarmParam){
        smsBoxsendedService.insertSmsBoxsending(alarmParam);
        return "ok";
    }
    //值班通讯录分页查询
    @ResponseBody
    @RequestMapping(value="/getdutyinfo",method = RequestMethod.GET)
    public Map<String,Object> getdutyinfo(AlarmParam alarmParam){
        List<HhpdiDutyaddrlist> list=smsBoxsendedService.selectDutyAddrListByPage(alarmParam);
        Integer count=smsBoxsendedService.selectDutyAddrListCount(alarmParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //获取值班通讯录年份列表
    @ResponseBody
    @RequestMapping(value="/getdutyyears",method = RequestMethod.GET)
    public List<Map<String,Object>> getdutyyears(){
        return smsBoxsendedService.selectDutyAddrListYear();
    }
    //新增/修改通讯录
    @RequestMapping(value="/editdutyinfo",method = RequestMethod.POST)
    @ResponseBody
    public String editdutyinfo(@RequestBody AddrlistParam addrlistParam){
        if(addrlistParam.getOpsign()==1){
            smsBoxsendedService.insertDutyAddrlist(addrlistParam);
        }else {
            smsBoxsendedService.updateDutyAddrlist(addrlistParam);
        }
        return "ok";
    }
    //删除通讯录
    @ResponseBody
    @RequestMapping(value="/deldutyinfo",method = RequestMethod.GET)
    public String deldutyinfo(AddrlistParam addrlistParam){
        smsBoxsendedService.deleteDutyAddrlist(addrlistParam);
        return "ok";
    }
    //通讯录详情
    @ResponseBody
    @RequestMapping(value="/dutyinfodetail",method = RequestMethod.GET)
    public HhpdiDutyaddrlist dutyinfodetail(AddrlistParam addrlistParam){
        return smsBoxsendedService.selectDutyAddrlistDetail(addrlistParam);
    }
    //通过年份导入通讯录
    @ResponseBody
    @RequestMapping(value="/addbyyear",method = RequestMethod.GET)
    public String addbyyear(AddrlistParam addrlistParam){
        smsBoxsendedService.insertDutyAddrlistByYear(addrlistParam);
        return "ok";
    }
    //判断此年份的通讯录是否存在
    @ResponseBody
    @RequestMapping(value="/existdata",method = RequestMethod.GET)
    public Integer existdata(AddrlistParam addrlistParam){
        return smsBoxsendedService.existDutyAddrlistByyear(addrlistParam);
    }
    //根据年份删除通讯录
    @ResponseBody
    @RequestMapping(value="/deletebyyear",method = RequestMethod.GET)
    public String deletebyyear(AddrlistParam addrlistParam){
        smsBoxsendedService.deleteDutyAddrlistByyear(addrlistParam);
        return "ok";
    }
    //新增值班安排
    @RequestMapping(value="/adddutyplan",method = RequestMethod.POST)
    @ResponseBody
    public String adddutyplan(@RequestBody DutyPlanParam params){
        if(params.getDelsign()==1){
            smsBoxsendedService.deleteDutyplan(params.getBegintime(),params.getEndtime());
        }
        smsBoxsendedService.insertDutyplan(params.getList());
        return "ok";
    }
    //查询值班安排
    @ResponseBody
    @RequestMapping(value="/getdutyplan",method = RequestMethod.GET)
    public List<Map<String,Object>> getdutyplan(AlarmParam alarmParam){
        return smsBoxsendedService.selectDutyplanByDate(alarmParam);
    }
    //根据人员类型查询值班通讯录
    @RequestMapping(value="/getaddrtype/{type}&{text}",method = RequestMethod.GET)
    @ResponseBody
    public List<HhpdiDutyaddrlist> getaddrtype(@PathVariable("type") int type,@PathVariable("text") String text){
        return smsBoxsendedService.selectDutyAddrlistByType(type,text);
    }
    //查询降雨预警指标
    @RequestMapping(value="/getppalarm",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getppalarm(LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else if(lvDingParam.getOrderBy().equals("EWLNAME")){
            lvDingParam.setOrderBy("a.EWL");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StPpAlarm> list=stAlarminfoService.selectStPpAlarmByPage(lvDingParam);
        Integer count=stAlarminfoService.selectStPpAlarmByCount(lvDingParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //查询水库水位预警指标
    @RequestMapping(value="/getrsvalarm",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getrsvalarm(LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StRsvAlarm> list=stAlarminfoService.selectStRswAlarmByPage(lvDingParam);
        Integer count=stAlarminfoService.selectStRsvAlarmByCount(lvDingParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //查询河道水位预警指标
    @RequestMapping(value="/getriveralarm",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getriveralarm(LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StRiverAlarm> list=stAlarminfoService.selectStRiverAlarmByPage(lvDingParam);
        Integer count=stAlarminfoService.selectStRiverAlarmByCount(lvDingParam);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
}
