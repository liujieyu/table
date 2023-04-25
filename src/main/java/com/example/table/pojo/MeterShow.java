package com.example.table.pojo;

/**
 * 收费通知信息展示
 */
public class MeterShow {
    private Integer index;
    private String farmname;              //农户用户
    private Integer area;                 //承包面积
    private String canalname;             //所属渠道
    private String contacts;              //办卡人
    private String conphone;              //联系方式
    private Integer waternum;             //水表读数
    private String readtime;              //读数时间
    private Integer wateryield;           //水量
    private Integer lastnum;              //上期读数
    private Integer yieldbase;            //基础水权
    private Integer yieldfirst;           //一级水量
    private Integer yieldsecond;          //二级水量
    private Integer yieldthird;           //三级水量
    private Double waterrate;             //水费
    private Double available;             //可用余额
    private Integer surplus;              //剩余水量
    private String paytime;               //缴费时间

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getConphone() {
        return conphone;
    }

    public void setConphone(String conphone) {
        this.conphone = conphone;
    }

    public Integer getWaternum() {
        return waternum;
    }

    public void setWaternum(Integer waternum) {
        this.waternum = waternum;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public Integer getWateryield() {
        return wateryield;
    }

    public void setWateryield(Integer wateryield) {
        this.wateryield = wateryield;
    }

    public Integer getLastnum() {
        return lastnum;
    }

    public void setLastnum(Integer lastnum) {
        this.lastnum = lastnum;
    }

    public Integer getYieldbase() {
        return yieldbase;
    }

    public void setYieldbase(Integer yieldbase) {
        this.yieldbase = yieldbase;
    }

    public Integer getYieldfirst() {
        return yieldfirst;
    }

    public void setYieldfirst(Integer yieldfirst) {
        this.yieldfirst = yieldfirst;
    }

    public Integer getYieldsecond() {
        return yieldsecond;
    }

    public void setYieldsecond(Integer yieldsecond) {
        this.yieldsecond = yieldsecond;
    }

    public Integer getYieldthird() {
        return yieldthird;
    }

    public void setYieldthird(Integer yieldthird) {
        this.yieldthird = yieldthird;
    }

    public Double getWaterrate() {
        return waterrate;
    }

    public void setWaterrate(Double waterrate) {
        this.waterrate = waterrate;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }
}
