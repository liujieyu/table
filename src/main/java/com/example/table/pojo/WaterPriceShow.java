package com.example.table.pojo;

/**
 * 支渠超水节水信息统计
 */
public class WaterPriceShow {
    private String stnm;         //支渠用户名
    private String canalname;    //所属渠道
    private Integer area;        //灌溉面积
    private Integer basewater;   //毛供水量
    private Integer oneuplim;    //一级超(节)水量上限
    private Integer twouplim;    //二级超(节)水量上限
    private Integer totalwater;  //总供水量（实际供水量）
    private Integer totalover;   //总超水量
    private Integer onewater;    //一级超(节)水量
    private Integer twowater;    //二级超(节)水量
    private Integer thrwater;    //三级超(节)水量
    private Integer totalback;   //总节水量

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }

    public Integer getOneuplim() {
        return oneuplim;
    }

    public void setOneuplim(Integer oneuplim) {
        this.oneuplim = oneuplim;
    }

    public Integer getTwouplim() {
        return twouplim;
    }

    public void setTwouplim(Integer twouplim) {
        this.twouplim = twouplim;
    }

    public Integer getTotalwater() {
        return totalwater;
    }

    public void setTotalwater(Integer totalwater) {
        this.totalwater = totalwater;
    }

    public Integer getOnewater() {
        return onewater;
    }

    public void setOnewater(Integer onewater) {
        this.onewater = onewater;
    }

    public Integer getTwowater() {
        return twowater;
    }

    public void setTwowater(Integer twowater) {
        this.twowater = twowater;
    }

    public Integer getThrwater() {
        return thrwater;
    }

    public void setThrwater(Integer thrwater) {
        this.thrwater = thrwater;
    }

    public Integer getTotalover() {
        return totalover;
    }

    public void setTotalover(Integer totalover) {
        this.totalover = totalover;
    }

    public Integer getTotalback() {
        return totalback;
    }

    public void setTotalback(Integer totalback) {
        this.totalback = totalback;
    }
}
