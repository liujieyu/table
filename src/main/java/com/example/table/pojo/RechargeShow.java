package com.example.table.pojo;

/**
 * 充值信息查询展示
 */
public class RechargeShow {
    private int index;
    private String farmname;              //农户用户
    private Integer amount;               //购买金额
    private String createtime;            //购买时间
    private Integer buywater;             //购买水量
    private Integer basewater;            //基础水权
    private Integer buyfirst;             //一级水量
    private Integer buysecond;            //二级水量
    private Integer buythird;             //三级水量
    private Integer lastsurplus;          //上期剩余水量
    private Integer remainwater;          //存余水量
    private Integer area;                 //承包面积
    private String canalname;             //所属渠道

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getBuywater() {
        return buywater;
    }

    public void setBuywater(Integer buywater) {
        this.buywater = buywater;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }

    public Integer getBuyfirst() {
        return buyfirst;
    }

    public void setBuyfirst(Integer buyfirst) {
        this.buyfirst = buyfirst;
    }

    public Integer getBuysecond() {
        return buysecond;
    }

    public void setBuysecond(Integer buysecond) {
        this.buysecond = buysecond;
    }

    public Integer getBuythird() {
        return buythird;
    }

    public void setBuythird(Integer buythird) {
        this.buythird = buythird;
    }

    public Integer getLastsurplus() {
        return lastsurplus;
    }

    public void setLastsurplus(Integer lastsurplus) {
        this.lastsurplus = lastsurplus;
    }

    public Integer getRemainwater() {
        return remainwater;
    }

    public void setRemainwater(Integer remainwater) {
        this.remainwater = remainwater;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }
}
