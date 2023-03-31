package com.example.table.pojo;

/**
 * 渠道水情月水量数据
 */
public class StMCanalC {
    private Integer index;    //序号
    private String stcd;      //站点编号
    private String stnm;      //站点名称
    private String tm;        //日期时间
    private Double maq;       //月平均流量
    private Double maz;       //月平均水深
    private Double mwq;       //月水量

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

    public Double getMaq() {
        return maq;
    }

    public void setMaq(Double maq) {
        this.maq = maq;
    }

    public Double getMaz() {
        return maz;
    }

    public void setMaz(Double maz) {
        this.maz = maz;
    }

    public Double getMwq() {
        return mwq;
    }

    public void setMwq(Double mwq) {
        this.mwq = mwq;
    }
}
