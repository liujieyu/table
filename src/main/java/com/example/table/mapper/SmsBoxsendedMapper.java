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
 * @since 2019-09-26
 */
@Component
public interface SmsBoxsendedMapper extends BaseMapper<SmsBoxsended> {
    //短信预警统计
    Integer selectSmsBoxsendedByCount(AlarmParam alarmParam);
    //短信预警分页查询
    List<SmsBoxsended> selectSmsBoxsendedByPage(AlarmParam alarmParam);
    //字段选取表查询
    List<String> selectFieldInfoByParam(@Param("fcode") String fcode,@Param("fnum") Integer fnum);
    //接收短信统计
    Integer selectSmsBoxreceivedByCount(AlarmParam alarmParam);
    //接收短信分页查询
    List<SmsBoxreceived> selectSmsBoxreceivedByPage(AlarmParam alarmParam);
    //发送短信查询(全部)统计
    Integer selectSmsBoxSendAllByCount(AlarmParam alarmParam);
    //发送短信查询(全部)分页
    List<SmsBoxsended> selectSmsBoxSendAllByPage(AlarmParam alarmParam);
    //发送短信查询(指定表)统计
    Integer selectSmsBoxSendTableByCount(AlarmParam alarmParam);
    //发送短信查询(指定表)分页
    List<SmsBoxsended> selectSmsBoxSendTableByPage(AlarmParam alarmParam);
    //发送短信详情
    SmsBoxsended selectSmsBoxSendByDetail(@Param("table") String table,@Param("id") int id);
    //值班安排查询
    List<Map<String,Object>> selectDutyPlanByDate(@Param("sql") String sql);
    //值班安排详情
    List<Map<String,Object>> selectDutyPlanDetail(@Param("date") String date);
    //短信发送人员信息查询
    List<WrpMan> selectWrpManByTypes(WrpMan wrpMan);
    //新增短信
    void insertSmsBoxsending(AlarmParam alarmParam);
    //值班通讯录总记录数
    Integer selectDutyAddrListCount(AlarmParam alarmParam);
    //值班通讯录分页查询
    List<HhpdiDutyaddrlist> selectDutyAddrListByPage(AlarmParam alarmParam);
    //获取值班通讯录年份
    List<Map<String,Object>> selectDutyAddrListYear();
    //新增通讯录
    void insertDutyAddrlist(AddrlistParam addrlistParam);
    //修改通讯录
    void updateDutyAddrlist(AddrlistParam addrlistParam);
    //删除通讯录
    void deleteDutyAddrlist(AddrlistParam addrlistParam);
    //通讯录详情
    List<HhpdiDutyaddrlist> selectDutyAddrlistDetail(AddrlistParam addrlistParam);
    //通过年份导入通讯录
    void insertDutyAddrlistByYear(AddrlistParam addrlistParam);
    //判断此年份的通讯录是否存在
    Integer existDutyAddrlistByyear(AddrlistParam addrlistParam);
    //根据年份删除通讯录
    void deleteDutyAddrlistByyear(AddrlistParam addrlistParam);
    //新增值班安排信息
    void insertDutyplan(List<DutyPlan> list);
    //删除值班安排信息
    void deleteDutyplan(@Param("begintime") String begintime,@Param("endtime") String endtime);
    //查询值班安排信息
    List<Map<String,Object>> selectDutyplanByDate(AlarmParam alarmParam);
    //根据人员类型查询值班通讯录
    List<HhpdiDutyaddrlist> selectDutyAddrlistByType(@Param("type") int type,@Param("addtion1") String addtion1);
}
