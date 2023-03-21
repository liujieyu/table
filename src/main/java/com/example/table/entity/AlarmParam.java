package com.example.table.entity;

import java.util.Date;

/**
 * <p>
 *预警参数类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-19
 */
public class AlarmParam {
    //开始时间
    private String begintime;
    //结束时间
    private String endtime;
    //行政区划编码
    private String adcd;
    //预警等级
    private Integer alarmlevel;
    //站点名称
    private String sitename;
    //分页开始记录数
    private int begincount=1;
    //分页结束记录数
    private int endcount=20;
    //平台标识 1 省级平台  2 市级平台  3 县级平台
    private int plantsign=3;
    //排序字段
    private String orderBy="a.TM";
    //排序顺序
    private String sequence="desc";
    //短信预警等级名称
    private String appid;
    //发送号码
    private String phone;
    //结果标识
    private int sign;
    //表名
    private String table;
    //显示结果
    private String result;
    //子sql
    private String subsql;
    //发送平台
    private String sender;
    //接收手机号码
    private String receiver;
    //短信内容
    private String content;
    //姓名
    private String addtion1;
    //单位
    private String addtion2;
    //职务
    private String addtion3;
    //发送表
    private String tablename;
    //年份
    private int year;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAdcd() {
        return adcd;
    }

    public void setAdcd(String adcd) {
        this.adcd = adcd;
    }

    public Integer getAlarmlevel() {
        return alarmlevel;
    }

    public void setAlarmlevel(Integer alarmlevel) {
        this.alarmlevel = alarmlevel;
    }

    public String getSitename() {
        return sitename;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtion1() {
        return addtion1;
    }

    public void setAddtion1(String addtion1) {
        this.addtion1 = addtion1;
    }

    public String getAddtion2() {
        return addtion2;
    }

    public void setAddtion2(String addtion2) {
        this.addtion2 = addtion2;
    }

    public String getAddtion3() {
        return addtion3;
    }

    public void setAddtion3(String addtion3) {
        this.addtion3 = addtion3;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public int getBegincount() {
        return begincount;
    }

    public void setBegincount(int begincount) {
        this.begincount = begincount;
    }

    public int getEndcount() {
        return endcount;
    }

    public void setEndcount(int endcount) {
        this.endcount = endcount;
    }

    public int getPlantsign() {
        return plantsign;
    }

    public void setPlantsign(int plantsign) {
        this.plantsign = plantsign;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getSubsql() {
        return subsql;
    }

    public void setSubsql(String subsql) {
        this.subsql = subsql;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
