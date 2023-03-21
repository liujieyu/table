package com.example.table.service;

import com.example.table.entity.AlarmParam;
import com.example.table.entity.MedataParam;
import com.example.table.entity.WrpFieldinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-20
 */
public interface WrpFieldinfoService extends IService<WrpFieldinfo> {
    //获取基础数据配置信息
    List<Map<String,Object>> selectWrpFieldinfoByFormIDAndFieldID(String formid, String fieldid);
    //表格选取表查询
    List<Map<String,Object>> selectSysFormInfoByFCodeAndFNum(String fcode,int fnum);
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
    List<Map<String,Object>> selectRainDateInfo();
    //实时河道水位
    List<Map<String,Object>> selectRiverRData();
    //实时水库水位
    List<Map<String,Object>> selectRsvrRData();
    //今日雨量
    List<Map<String,Object>> selectTodayRainData();
    //入库出库流量和水量
    List<Map<String,Object>> selectTodayQandW();
    //河道水位2小时变化值
    List<Map<String,Object>> selectChangeRiverData();
    //水库水位2小时变化值
    List<Map<String,Object>> selectChangeRsvrRData();
    //最新1小时EXCEL导出SQL
    public Map<String,Object> selectLastHourExcelData();
    //水库小时表信息
    public Map<String,Object> selectStRsvrHByHours(AlarmParam alarmparam);
    //当前时间水库没有记录，取前一条记录
    public List<Map<String,Object>> selectStRsvrHByBefore(AlarmParam alarmparam);
    //获取渠道站点对应渠道站点
    public List<Map<String,Object>> selectQudaoInfo();
    //获取渠道站点信息
    public List<Map<String,Object>> selectQudaoSiteInfo(MedataParam medataParam);
}
