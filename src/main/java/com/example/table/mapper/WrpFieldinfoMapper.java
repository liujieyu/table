package com.example.table.mapper;

import com.example.table.entity.AlarmParam;
import com.example.table.entity.MedataParam;
import com.example.table.entity.WrpFieldinfo;
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
 * @since 2019-09-20
 */
@Component
public interface WrpFieldinfoMapper extends BaseMapper<WrpFieldinfo> {
    //获取基础数据配置信息
    List<Map<String,Object>> selectWrpFieldinfoByFormIDAndFieldID(@Param("formid") String formid,@Param("fieldid")  String fieldid);
    //表格选取表查询
    List<Map<String,Object>> selectSysFormInfoByFCodeAndFNum(@Param("fcode") String fcode,@Param("fnum") int fnum);
    //水量图数据
    List<Map<String,Object>> selectBorchartData(AlarmParam alarmParam);
    //饼状图数据
    List<Map<String,Object>> selectPiechartData();
    //图像站点数据
    List<Map<String,Object>> selectImgsiteData();
    //渠道实时信息
    List<Map<String,Object>> selectStCanalRBystcd();
    //闸阀实时信息
    List<Map<String,Object>> selectStWasBystcd();
    //视频站点数据
    List<Map<String,Object>> selectVideositeData();
    //雨情数据
    List<Map<String,Object>> selectRainData(AlarmParam alarmParam);
    //水位库容曲线
    List<Map<String,Object>> selectWlStcpData();
    //带条件水位库容曲线
    List<Map<String,Object>> selectWlStcpDataByDate(AlarmParam alarmParam);
    //闸门开度流量数据
    List<Map<String,Object>> selectGateData(AlarmParam alarmParam);
    //今日雨量
    List<Map<String,Object>> selectRainDateInfo(@Param("curdate") String curdate);
    //实时河道水位
    List<Map<String,Object>> selectRiverRData();
    //实时水库水位
    List<Map<String,Object>> selectRsvrRData();
    //今日雨量
    List<Map<String,Object>> selectTodayRainData();
    //入库出库流量和水量
    List<Map<String,Object>> selectTodayQandW();
    //判断闸门流量数据的日期是否今日更新
    List<Map<String,Object>> selectTodayQSign();
    //根据闸门编号获取闸门历史小时信息
    List<Map<String,Object>> selectStGatageHByTimeAndStcd(@Param("stcd") String stcd);
    //根据闸门编号获取闸门历史小时信息2
    List<Map<String,Object>> selectStGatageHByLastAndStcd(@Param("stcd") String stcd);
    //根据编号获取今日各小时出库水量
    List<Map<String,Object>> selectStGateHByTodayQStcd(@Param("stcd") String stcd);
    //获取生态、灌溉和溢洪洞流量站各小时出库水量
    List<Map<String,Object>> selectStHCanalCByToday();
    //获取今日入库流量站小时信息
    List<Map<String,Object>> selectStRiverHByToday();
    //获取水库小时库容增量信息
    List<Map<String,Object>> selectStRsvrHByToday();
    //河道水位2小时变化值
    List<Map<String,Object>> selectChangeRiverData();
    //水库水位2小时变化值
    List<Map<String,Object>> selectChangeRsvrRData();
    //最新1小时EXCEL导出SQL
    //当前水位，当前库容
    List<Map<String,Object>> selectLastSwAndKrHour();
    //闸门开度信息
    List<Map<String,Object>> selectZmKdHourInfo();
    //当前小时累计雨量
    List<Map<String,Object>> selectSumRainHourInfo();
    //生态用水流量站  水深、流量、水量
    List<Map<String,Object>> selectStllSiteHourInfo();
    //入库流量站水位
    List<Map<String,Object>> selectInSiteHourSw();
    //获取小时库容增量
    List<Map<String,Object>> selectKwAddHour();
    //判断最新小时是否有流量更新
    List<Map<String,Object>> selectReflashHour();
    //闸门出库流量和出库水量
    List<Map<String,Object>> selectOutWAndSLHour(@Param("stcd") String stcd);
    //前2个小时最新数据
    List<Map<String,Object>> selectBeforeTwoHourData(@Param("stcd") String stcd);
    //水库小时表信息
    List<Map<String,Object>> selectStRsvrHByHours(AlarmParam alarmparam);
    //当前时间水库没有记录，取前一条记录
    List<Map<String,Object>> selectStRsvrHByBefore(AlarmParam alarmparam);
    //获取水库站点正常蓄水位和汛限水位
    List<Map<String,Object>> selectWaterSiteSwByStRsvr();
    //灌溉、生态和溢洪洞流量小时信息
    List<Map<String,Object>> selectGSYllByTime(AlarmParam alarmparam);
    //任意时间段是否有开度信息更新
    List<Map<String,Object>> selectDldSignByTime(AlarmParam alarmparam);
    //任意时间段第0个数组的流量
    List<Map<String,Object>> selectZeroDldLlByTime(AlarmParam alarmparam);
    //任意时间段导流洞流量和水量
    List<Map<String,Object>> selectDldQAndWByTime(AlarmParam alarmparam);
    //任意时间段入库流量
    List<Map<String,Object>> selectInWQByTime(AlarmParam alarmparam);
    //获取渠道站点对应渠道站点
    List<Map<String,Object>> selectQudaoInfo();
    //获取所有渠道站点信息
    List<Map<String,Object>> selectAllQdSiteInfo(MedataParam medataParam);
    //根据条件获取渠道站点信息
    List<Map<String,Object>> selectQudaoSiteInfo(MedataParam medataParam);
    //根据条件获取无渠道站点信息
    List<Map<String,Object>> selectOtherQudaoSiteInfo(MedataParam medataParam);
}
