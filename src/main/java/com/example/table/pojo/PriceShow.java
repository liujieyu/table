package com.example.table.pojo;

/**
 * 支渠和农户水价标准查询字段
 */
public class PriceShow {
    private Integer priceyr;     //定价年份
    private Integer basewater;   //基础水量
    private Integer area;        //面积（灌溉或者承包）
    private String onewater;     //一级水量(超水或者节水)
    private String twowater;     //二级水量(超水或者节水)
    private String thrwater;     //三级水量(超水或者节水)
    private String canalname;    //所属渠道
    private String stnm;         //支渠用户名
    private String farmname;     //农户用户名

    public Integer getPriceyr() {
        return priceyr;
    }

    public void setPriceyr(Integer priceyr) {
        this.priceyr = priceyr;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getOnewater() {
        return onewater;
    }

    public void setOnewater(String onewater) {
        this.onewater = onewater;
    }

    public String getTwowater() {
        return twowater;
    }

    public void setTwowater(String twowater) {
        this.twowater = twowater;
    }

    public String getThrwater() {
        return thrwater;
    }

    public void setThrwater(String thrwater) {
        this.thrwater = thrwater;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }
}
