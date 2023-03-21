package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2020-07-07
 */
@TableName("ST_RSV_Alarm")
public class StRsvAlarm extends Model<StRsvAlarm> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("ROWID")
    private Integer rowid;

    @JsonProperty("STNM")
    private String stnm;

    @JsonProperty("STCD")
    private String stcd;

    @JsonProperty("SWL")
    private Double swl;

    @JsonProperty("FWL")
    private Double fwl;

    @JsonProperty("FWL79")
    private Double fwl79;

    @JsonProperty("ZCWL")
    private Double zcwl;

    @JsonProperty("SJWL")
    private Double sjwl;

    @JsonProperty("XHWL")
    private Double xhwl;

    @JsonProperty("BDGC")
    private Double bdgc;

    @JsonProperty("JYWL")
    private Double jywl;

    @JsonProperty("MEMO")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public Double getSwl() {
        return swl;
    }

    public void setSwl(Double swl) {
        this.swl = swl;
    }

    public Double getFwl() {
        return fwl;
    }

    public void setFwl(Double fwl) {
        this.fwl = fwl;
    }

    public Double getFwl79() {
        return fwl79;
    }

    public void setFwl79(Double fwl79) {
        this.fwl79 = fwl79;
    }

    public Double getZcwl() {
        return zcwl;
    }

    public void setZcwl(Double zcwl) {
        this.zcwl = zcwl;
    }

    public Double getSjwl() {
        return sjwl;
    }

    public void setSjwl(Double sjwl) {
        this.sjwl = sjwl;
    }

    public Double getXhwl() {
        return xhwl;
    }

    public void setXhwl(Double xhwl) {
        this.xhwl = xhwl;
    }

    public Double getBdgc() {
        return bdgc;
    }

    public void setBdgc(Double bdgc) {
        this.bdgc = bdgc;
    }

    public Double getJywl() {
        return jywl;
    }

    public void setJywl(Double jywl) {
        this.jywl = jywl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
