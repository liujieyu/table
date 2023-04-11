package com.example.table.pojo;

/**
 * 参数类
 */
public class WaterParam {
    private String stcd;       //站点编号
    private String stnm;       //站点名称
    private String begintime;  //开始时间
    private String endtime;    //结束时间
    private String showsign;   //水协会标识
    private String dt;         //日期
    private int tm;            //小时
    private int year;          //年
    private int month;         //月
    private String canalcode;  //渠道编号
    private String loginname;  //登录账号
    //分页开始记录数
    private int begincount=1;
    //分页结束记录数
    private int endcount=20;
    //排序字段
    private String orderBy="tm";
    //排序顺序
    private String sequence="desc";
    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
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

    public String getShowsign() {
        return showsign;
    }

    public void setShowsign(String showsign) {
        this.showsign = showsign;
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

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getTm() {
        return tm;
    }

    public void setTm(int tm) {
        this.tm = tm;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getCanalcode() {
        return canalcode;
    }

    public void setCanalcode(String canalcode) {
        this.canalcode = canalcode;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
}
