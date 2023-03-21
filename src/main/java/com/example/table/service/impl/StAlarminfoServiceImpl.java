package com.example.table.service.impl;

import com.example.table.entity.*;
import com.example.table.mapper.StAlarminfoMapper;
import com.example.table.service.StAlarminfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-19
 */
@Service
public class StAlarminfoServiceImpl extends ServiceImpl<StAlarminfoMapper, StAlarminfo> implements StAlarminfoService {
    @Autowired
    StAlarminfoMapper stAlarminfoMapper;
    //站点预警信息统计
    public Integer selectWarmInfoByCount(AlarmParam alarmParam){
        return stAlarminfoMapper.selectWarmInfoByCount(alarmParam);
    }
    //分页查询站点预警信息
    public List<StAlarminfo> selectWarmInfoByPage(AlarmParam alarmParam){
        return stAlarminfoMapper.selectWarmInfoByPage(alarmParam);
    }
    //根据STCD查询站点预警信息
    public StAlarminfo selectWarmInfoBySTCD(String stcd,String tm,int plantsign){
        return stAlarminfoMapper.selectWarmInfoBySTCD(stcd,tm,plantsign);
    }
    //预警指标信息统计
    public Integer selectStPpAlarmByCount(LvDingParam lvDingParam){
        return stAlarminfoMapper.selectStPpAlarmByCount(lvDingParam);
    }
    //分页查询预警指标信息
    public List<StPpAlarm> selectStPpAlarmByPage(LvDingParam lvDingParam){
        return stAlarminfoMapper.selectStPpAlarmByPage(lvDingParam);
    }
    //水库水位预警指标统计
    public Integer selectStRsvAlarmByCount(LvDingParam lvDingParam){
        return stAlarminfoMapper.selectStRsvAlarmByCount(lvDingParam);
    }
    //水库水位分页查询预警指标信息
    public List<StRsvAlarm> selectStRswAlarmByPage(LvDingParam lvDingParam){
        return stAlarminfoMapper.selectStRswAlarmByPage(lvDingParam);
    }
    //河道水位预警指标统计
    public Integer selectStRiverAlarmByCount(LvDingParam lvDingParam){
        return stAlarminfoMapper.selectStRiverAlarmByCount(lvDingParam);
    }
    //河道水位预警指标分页查询
    public List<StRiverAlarm> selectStRiverAlarmByPage(LvDingParam lvDingParam){
       return stAlarminfoMapper.selectStRiverAlarmByPage(lvDingParam);
    }
    //雨情时段报表
    public List<Map<String,Object>> selectHourRainExport(MedataParam medataParam){
        return stAlarminfoMapper.selectHourRainExport(medataParam);
    }
    //雨情日表报表
    public List<Map<String,Object>> selectDayRainExport(MedataParam medataParam){
        return stAlarminfoMapper.selectDayRainExport(medataParam);
    }
    //雨情月表报表
    public List<Map<String,Object>> selectMonthRainExport(MedataParam medataParam){
        return stAlarminfoMapper.selectMonthRainExport(medataParam);
    }
    //雨情年表报表
    public List<Map<String,Object>> selectYearRainExport(MedataParam medataParam){
        return stAlarminfoMapper.selectYearRainExport(medataParam);
    }
    //水库水情小时表-站点基本信息
    public List<Map<String,Object>> selectRsvrInfoExport(){
        return stAlarminfoMapper.selectRsvrInfoExport();
    }
    //水库水情小时表-小时报表
    public List<Map<String,Object>> selectRsvrHourExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRsvrHourExport(medataParam);
    }
    //水库水情历史表
    public List<Map<String,Object>> selectRsvrHistoryExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRsvrHistoryExport(medataParam);
    }
    //水库水情日报表
    public List<Map<String,Object>> selectRsvrDayExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRsvrDayExport(medataParam);
    }
    //水库水情月表
    public List<Map<String,Object>> selectRsvrMonthExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRsvrMonthExport(medataParam);
    }
    //河道水情小时表-站点基本信息
    public List<Map<String,Object>> selectRiverInfoExport(){
        return stAlarminfoMapper.selectRiverInfoExport();
    }
    //河道水情小时表-小时报表
    public List<Map<String,Object>> selectRiverHourExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRiverHourExport(medataParam);
    }
    //河道水情历史表
    public List<Map<String,Object>> selectRiverHistoryExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRiverHistoryExport(medataParam);
    }
    //河道水情日表
    public List<Map<String,Object>> selectRiverDayExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRiverDayExport(medataParam);
    }
    //河道水情月表
    public List<Map<String,Object>> selectRiverMonthExport(MedataParam medataParam){
        return stAlarminfoMapper.selectRiverMonthExport(medataParam);
    }
    //渠道水情日表
    public List<Map<String,Object>> selectQudaoDayExport(MedataParam medataParam){
        return stAlarminfoMapper.selectQudaoDayExport(medataParam);
    }
    //渠道水情月表
    public List<Map<String,Object>> selectQudaoMonthExport(MedataParam medataParam){
        return stAlarminfoMapper.selectQudaoMonthExport(medataParam);
    }
    //获取水情渠道站点名称和编码
    public List<Map<String,Object>> selectQudaoSiteName(MedataParam medataParam){
        return stAlarminfoMapper.selectQudaoSiteName(medataParam);
    }
    //渠道水情小时表
    public List<Map<String,Object>> selectQudaoHourExport(List<Map<String,Object>> sitelist,MedataParam medataParam){
        String subsql="";
        for(int i=0;i<sitelist.size();i++){
            Map<String,Object> map=sitelist.get(i);
            String stcd=map.get("stcd").toString();
            subsql+="max(CASE stcd WHEN '"+stcd+"' THEN a_z ELSE 0 end) as '"+stcd+"平均水深',";
            subsql+="max(CASE stcd WHEN '"+stcd+"' THEN a_q ELSE 0 end) as '"+stcd+"平均流量',";
            subsql+="max(CASE stcd WHEN '"+stcd+"' THEN wq ELSE 0 end) as '"+stcd+"小时累计水量'";
            if(i<sitelist.size()-1){
                subsql+=",";
            }
        }
        AlarmParam alarmParam=new AlarmParam();
        alarmParam.setSubsql(subsql);
        alarmParam.setBegintime(medataParam.getBegintime());
        alarmParam.setEndtime(medataParam.getEndtime());
        return stAlarminfoMapper.selectQudaoHourExport(alarmParam);
    }
    //渠道水情历史表
    public List<Map<String,Object>> selectQudaoLsExport(MedataParam medataParam){
        return stAlarminfoMapper.selectQudaoLsExport(medataParam);
    }
}
