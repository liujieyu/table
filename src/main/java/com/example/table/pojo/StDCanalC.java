package com.example.table.pojo;

/**
 * 渠道日水量信息
 */
public class StDCanalC {
    private Integer index;    //序号
    private String stcd;      //站点编号
    private String stnm;      //站点名称
    private String tm;        //日期时间
    private Double daq;       //日平均流量
    private Double daz;       //日平均水深
    private Double dwq;       //日水量

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

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public Double getDaq() {
        return daq;
    }

    public void setDaq(Double daq) {
        this.daq = daq;
    }

    public Double getDaz() {
        return daz;
    }

    public void setDaz(Double daz) {
        this.daz = daz;
    }

    public Double getDwq() {
        return dwq;
    }

    public void setDwq(Double dwq) {
        this.dwq = dwq;
    }
}
