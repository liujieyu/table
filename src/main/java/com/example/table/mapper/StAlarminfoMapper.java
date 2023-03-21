package com.example.table.mapper;

import com.example.table.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-19
 */
@Component
public interface StAlarminfoMapper extends BaseMapper<StAlarminfo> {
    //站点预警信息统计
    Integer selectWarmInfoByCount(AlarmParam alarmParam);
    //分页查询站点预警信息
    List<StAlarminfo> selectWarmInfoByPage(AlarmParam alarmParam);
    //根据STCD查询站点预警信息
    StAlarminfo selectWarmInfoBySTCD(@Param("stcd") String stcd,@Param("tm") String tm,@Param("plantsign") int plantsign);
    //降雨预警指标信息统计
    Integer selectStPpAlarmByCount(LvDingParam lvDingParam);
    //降雨分页查询预警指标信息
    List<StPpAlarm> selectStPpAlarmByPage(LvDingParam lvDingParam);
    //水库水位预警指标统计
    Integer selectStRsvAlarmByCount(LvDingParam lvDingParam);
    //水库水位分页查询预警指标信息
    List<StRsvAlarm> selectStRswAlarmByPage(LvDingParam lvDingParam);
    //河道水位预警指标统计
    Integer selectStRiverAlarmByCount(LvDingParam lvDingParam);
    //河道水位预警指标分页查询
    List<StRiverAlarm> selectStRiverAlarmByPage(LvDingParam lvDingParam);
    //雨情时段报表
    List<Map<String,Object>> selectHourRainExport(MedataParam medataParam);
    //雨情日表报表
    List<Map<String,Object>> selectDayRainExport(MedataParam medataParam);
    //雨情月表报表
    List<Map<String,Object>> selectMonthRainExport(MedataParam medataParam);
    //雨情年表报表
    List<Map<String,Object>> selectYearRainExport(MedataParam medataParam);
    //水库水情小时表-站点基本信息
    List<Map<String,Object>> selectRsvrInfoExport();
    //水库水情小时表-小时报表
    List<Map<String,Object>> selectRsvrHourExport(MedataParam medataParam);
    //水库水情历史表
    List<Map<String,Object>> selectRsvrHistoryExport(MedataParam medataParam);
    //水库水情日报表
    List<Map<String,Object>> selectRsvrDayExport(MedataParam medataParam);
    //水库水情月表
    List<Map<String,Object>> selectRsvrMonthExport(MedataParam medataParam);
    //河道水情小时表-站点基本信息
    List<Map<String,Object>> selectRiverInfoExport();
    //河道水情小时表-小时报表
    List<Map<String,Object>> selectRiverHourExport(MedataParam medataParam);
    //河道水情历史表
    List<Map<String,Object>> selectRiverHistoryExport(MedataParam medataParam);
    //河道水情日表
    List<Map<String,Object>> selectRiverDayExport(MedataParam medataParam);
    //河道水情月表
    List<Map<String,Object>> selectRiverMonthExport(MedataParam medataParam);
    //渠道水情日表
    List<Map<String,Object>> selectQudaoDayExport(MedataParam medataParam);
    //渠道水情月表
    List<Map<String,Object>> selectQudaoMonthExport(MedataParam medataParam);
    //获取水情渠道站点名称和编码
    List<Map<String,Object>> selectQudaoSiteName(MedataParam medataParam);
    //渠道水情小时表
    List<Map<String,Object>> selectQudaoHourExport(AlarmParam medataParam);
    //渠道水情历史表
    List<Map<String,Object>> selectQudaoLsExport(MedataParam medataParam);
}
