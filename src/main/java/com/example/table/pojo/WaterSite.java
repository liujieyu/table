package com.example.table.pojo;
//水协会支渠站点信息
public class WaterSite {
    private int id;           //主键ID
    private String stcd;      //站点编号
    private String stnm;      //站点名称
    private String canalcode; //渠道编号
    private String canalname; //渠道名称
    private String syssign;   //水协会标识

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCanalcode() {
        return canalcode;
    }

    public void setCanalcode(String canalcode) {
        this.canalcode = canalcode;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }
}
