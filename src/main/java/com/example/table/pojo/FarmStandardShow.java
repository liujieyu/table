package com.example.table.pojo;
//农户用户执行水价标准查询
public class FarmStandardShow {
    private String farmname;     //农户用户名
    private String canalname;    //所属渠道
    private Integer area;        //灌溉面积
    private Integer basewater;   //基础水量
    private Integer oneuplim;    //一级超(节)水量上限
    private Integer twouplim;    //二级超(节)水量上限
    private Double baseprice;    //基础水价
    private Double oneprice;     //超额一级用水单价
    private Double towprice;     //超额二级用水单价
    private Double thrprice;     //超额三级用水单价

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
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

    public Double getOneprice() {
        return oneprice;
    }

    public void setOneprice(Double oneprice) {
        this.oneprice = oneprice;
    }

    public Double getTowprice() {
        return towprice;
    }

    public void setTowprice(Double towprice) {
        this.towprice = towprice;
    }

    public Double getThrprice() {
        return thrprice;
    }

    public void setThrprice(Double thrprice) {
        this.thrprice = thrprice;
    }

    public Double getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(Double baseprice) {
        this.baseprice = baseprice;
    }
}
