package com.example.table.service;

import com.example.table.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-26
 */
public interface SmsBoxsendedService extends IService<SmsBoxsended> {
    //短信预警统计
    Integer selectSmsBoxsendedByCount(AlarmParam alarmParam);
    //短信预警分页查询
    List<SmsBoxsended> selectSmsBoxsendedByPage(AlarmParam alarmParam);
    //字段选取表查询
    List<String> selectFieldInfoByParam(String fcode,Integer fnum);
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
    SmsBoxsended selectSmsBoxSendByDetail(String table,int id);
    //值班安排查询
    List<Map<String,Object>> selectDutyPlanByDate(String sql);
    //值班安排所有数据
    Map<String,Object> selectDutyPlanByDateAndCols(String date);
    //值班安排详情
    List<Map<String,Object>> selectDutyPlanDetail(String date);
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
    HhpdiDutyaddrlist selectDutyAddrlistDetail(AddrlistParam addrlistParam);
    //通过年份导入通讯录
    void insertDutyAddrlistByYear(AddrlistParam addrlistParam);
    //判断此年份的通讯录是否存在
    Integer existDutyAddrlistByyear(AddrlistParam addrlistParam);
    //根据年份删除通讯录
    void deleteDutyAddrlistByyear(AddrlistParam addrlistParam);
    //新增值班安排信息
    void insertDutyplan(List<DutyPlan> list);
    //删除值班安排信息
    void deleteDutyplan(String begintime,String endtime);
    //查询值班安排信息
    List<Map<String,Object>> selectDutyplanByDate(AlarmParam alarmParam);
    //根据人员类型查询值班通讯录
    List<HhpdiDutyaddrlist> selectDutyAddrlistByType(int type,String addtion1);
}
