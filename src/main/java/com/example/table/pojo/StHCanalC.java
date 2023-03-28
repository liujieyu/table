package com.example.table.pojo;

/**
 * 渠道小时水量信息
 */
public class StHCanalC {
    private Integer index;    //序号
    private String stcd;      //站点编号
    private String stnm;      //站点名称
    private String dt;        //日期时间
    private Double aq;        //平均流量
    private Double az;        //平均水深
    private Double wq;        //小时累计水量

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Double getAq() {
        return aq;
    }

    public void setAq(Double aq) {
        this.aq = aq;
    }

    public Double getAz() {
        return az;
    }

    public void setAz(Double az) {
        this.az = az;
    }

    public Double getWq() {
        return wq;
    }

    public void setWq(Double wq) {
        this.wq = wq;
    }
}
