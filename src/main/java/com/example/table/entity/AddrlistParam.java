package com.example.table.entity;

public class AddrlistParam {
    private Integer id;

    private Integer yr;

    private String nm;

    private String unit;

    private String position;

    private Integer dutytype;

    private String mobile;

    private String ophone;

    private Integer wzpx;

    private int opsign;//1 add 2 update

    private int toyr;//要导入通讯录的年份

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDutytype() {
        return dutytype;
    }

    public void setDutytype(Integer dutytype) {
        this.dutytype = dutytype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOphone() {
        return ophone;
    }

    public void setOphone(String ophone) {
        this.ophone = ophone;
    }

    public Integer getWzpx() {
        return wzpx;
    }

    public void setWzpx(Integer wzpx) {
        this.wzpx = wzpx;
    }

    public int getOpsign() {
        return opsign;
    }

    public void setOpsign(int opsign) {
        this.opsign = opsign;
    }

    public int getToyr() {
        return toyr;
    }

    public void setToyr(int toyr) {
        this.toyr = toyr;
    }
}
