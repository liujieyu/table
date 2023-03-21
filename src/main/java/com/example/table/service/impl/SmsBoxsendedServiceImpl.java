package com.example.table.service.impl;

import com.example.table.entity.*;
import com.example.table.mapper.SmsBoxsendedMapper;
import com.example.table.mapper.WrpFieldinfoMapper;
import com.example.table.service.SmsBoxsendedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-26
 */
@Service
public class SmsBoxsendedServiceImpl extends ServiceImpl<SmsBoxsendedMapper, SmsBoxsended> implements SmsBoxsendedService {
    @Autowired
    SmsBoxsendedMapper smsBoxsendedMapper;
    @Autowired
    WrpFieldinfoMapper wrpFieldinfoMapper;
    //短信预警统计
    public Integer selectSmsBoxsendedByCount(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxsendedByCount(alarmParam);
    }
    //短信预警分页查询
    public List<SmsBoxsended> selectSmsBoxsendedByPage(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxsendedByPage(alarmParam);
    }
    //字段选取表查询
    public List<String> selectFieldInfoByParam(String fcode,Integer fnum){
        return smsBoxsendedMapper.selectFieldInfoByParam(fcode,fnum);
    }
    //接收短信统计
    public Integer selectSmsBoxreceivedByCount(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxreceivedByCount(alarmParam);
    }
    //接收短信分页查询
    public List<SmsBoxreceived> selectSmsBoxreceivedByPage(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxreceivedByPage(alarmParam);
    }
    //发送短信查询(全部)统计
    public Integer selectSmsBoxSendAllByCount(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxSendAllByCount(alarmParam);
    }
    //发送短信查询(全部)分页
    public List<SmsBoxsended> selectSmsBoxSendAllByPage(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxSendAllByPage(alarmParam);
    }
    //发送短信查询(指定表)统计
    public Integer selectSmsBoxSendTableByCount(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxSendTableByCount(alarmParam);
    }
    //发送短信查询(指定表)分页
    public List<SmsBoxsended> selectSmsBoxSendTableByPage(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectSmsBoxSendTableByPage(alarmParam);
    }
    //发送短信详情
    public SmsBoxsended selectSmsBoxSendByDetail(String table,int id){
        return smsBoxsendedMapper.selectSmsBoxSendByDetail(table,id);
    }
    //值班安排查询
    public List<Map<String,Object>> selectDutyPlanByDate(String sql){
        return smsBoxsendedMapper.selectDutyPlanByDate(sql);
    }
    //值班安排详情
    public List<Map<String,Object>> selectDutyPlanDetail(String date){
        return smsBoxsendedMapper.selectDutyPlanDetail(date);
    }
    //短信发送人员信息查询
    public List<WrpMan> selectWrpManByTypes(WrpMan wrpMan){
        return smsBoxsendedMapper.selectWrpManByTypes(wrpMan);
    }
    //新增短信
    public void insertSmsBoxsending(AlarmParam alarmParam){
        smsBoxsendedMapper.insertSmsBoxsending(alarmParam);
    }
    //值班安排所有数据
    public Map<String,Object> selectDutyPlanByDateAndCols(String date){
        List<Map<String,Object>> cols=new ArrayList<Map<String,Object>>();
        String[] weekstr=getFirstAndLastWeekDate(date);
        String dateyear=weekstr[0].substring(0,4)+"年";
        Map map1=new HashMap();
        map1.put("prop","YEAR");
        map1.put("label",dateyear);
        cols.add(map1);
        String sql="SELECT RIGHT('0'+CONVERT(varchar(2),datepart(mm,a.dt)),2)+\'月\'+RIGHT('0'+CONVERT(varchar(2),datepart(dd,a.dt)),2)+\'日 \'+DATENAME(dw,a.dt) AS YEAR,CONVERT(varchar(10),a.DT,120) as DT,";
        String subsql="";
        String typesql="";
        List<Map<String,Object>> infos=wrpFieldinfoMapper.selectWrpFieldinfoByFormIDAndFieldID("HHPDI_Duty_Plan","Type");
        for(int i=0;i<infos.size();i++){
            Map infomap=infos.get(i);
            String value=(String)infomap.get("value");
            String label=(String)infomap.get("label");
            Map mapz=new HashMap();
            mapz.put("prop","TYPE"+value);
            mapz.put("label",label);
            cols.add(mapz);
            subsql+="ISNULL(b.type"+value+", '') AS TYPE"+value;
            typesql+="type"+value+" = ( STUFF(( SELECT    ',' + Name+'('+Phone+')' FROM      HHPDI_Duty_Plan d WHERE     type="+value+" and d.OlanDate = p.OlanDate FOR XML PATH('')), 1, 1, '') )";
            if(i<infos.size()-1){
                subsql+=",";
                typesql+=",";
            }else{
                subsql+=" ";
                typesql+=" ";
            }
        }
        sql+=subsql;
        sql+=" FROM (SELECT dateadd(DD, number, '"+weekstr[0]+"') dt FROM master..spt_values WHERE type = 'p' AND dateadd(DD, number, '"+weekstr[0]+"') <= '"+weekstr[1]+"') a "
            +"LEFT JOIN (SELECT OlanDate AS dt,";
        sql+=typesql;
        sql+=" FROM    HHPDI_Duty_Plan AS p where OlanDate between '"+weekstr[0]+"' and '"+weekstr[1]+"' GROUP BY OlanDate)b on a.dt=b.dt";
        List<Map<String,Object>> list=selectDutyPlanByDate(sql);
        Map<String,Object> totalmap=new HashMap<>();
        totalmap.put("cols",cols);
        totalmap.put("rows",list);
        return totalmap;
    }
    //根据日期获取星期一和星期日的日期
    private String[] getFirstAndLastWeekDate(String date) {
        String[] weekstr=new String[2];
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time=new Date();
        try {
            time =sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(time);

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        weekstr[0]=sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        weekstr[1]=sdf.format(cal.getTime());
        return weekstr;
    }
    //值班通讯录总记录数
    public Integer selectDutyAddrListCount(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectDutyAddrListCount(alarmParam);
    }
    //值班通讯录分页查询
    public List<HhpdiDutyaddrlist> selectDutyAddrListByPage(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectDutyAddrListByPage(alarmParam);
    }
    //获取值班通讯录年份
    public List<Map<String,Object>> selectDutyAddrListYear(){
        return smsBoxsendedMapper.selectDutyAddrListYear();
    }
    //新增通讯录
    public void insertDutyAddrlist(AddrlistParam addrlistParam){
         smsBoxsendedMapper.insertDutyAddrlist(addrlistParam);
    }
    //修改通讯录
    public void updateDutyAddrlist(AddrlistParam addrlistParam){
        smsBoxsendedMapper.updateDutyAddrlist(addrlistParam);
    }
    //删除通讯录
    public void deleteDutyAddrlist(AddrlistParam addrlistParam){
        smsBoxsendedMapper.deleteDutyAddrlist(addrlistParam);
    }
    //通讯录详情
    public HhpdiDutyaddrlist selectDutyAddrlistDetail(AddrlistParam addrlistParam){
        List<HhpdiDutyaddrlist> list=smsBoxsendedMapper.selectDutyAddrlistDetail(addrlistParam);
        if(list!=null){
            return list.get(0);
        }else{
            return null;
        }
    }
    //通过年份导入通讯录
    public void insertDutyAddrlistByYear(AddrlistParam addrlistParam){
        smsBoxsendedMapper.insertDutyAddrlistByYear(addrlistParam);
    }
    //判断此年份的通讯录是否存在
    public Integer existDutyAddrlistByyear(AddrlistParam addrlistParam){
        return smsBoxsendedMapper.existDutyAddrlistByyear(addrlistParam);
    }
    //根据年份删除通讯录
    public void deleteDutyAddrlistByyear(AddrlistParam addrlistParam){
        smsBoxsendedMapper.deleteDutyAddrlistByyear(addrlistParam);
    }
    //新增值班安排信息
    public void insertDutyplan(List<DutyPlan> list){
        smsBoxsendedMapper.insertDutyplan(list);
    }
    //删除值班安排信息
    public void deleteDutyplan(String begintime,String endtime){
        smsBoxsendedMapper.deleteDutyplan(begintime,endtime);
    }
    //查询值班安排信息
    public List<Map<String,Object>> selectDutyplanByDate(AlarmParam alarmParam){
        return smsBoxsendedMapper.selectDutyplanByDate(alarmParam);
    }
    //根据人员类型查询值班通讯录
    public List<HhpdiDutyaddrlist> selectDutyAddrlistByType(int type,String addtion1){
        return smsBoxsendedMapper.selectDutyAddrlistByType(type,addtion1);
    }
}
