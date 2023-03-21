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
 * @since 2019-10-25
 */
@TableName("ST_ZQRL_B")
public class StZqrlB extends Model<StZqrlB> {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "ID")
    private Integer id;

    @JsonProperty("STNM")
    private String stnm;

    @JsonProperty("STCD")
    private String stcd;

    @JsonProperty("YR")
    private Integer yr;

    @JsonProperty("Z")
    private Double z;

    @JsonProperty("Q")
    private Double q;

    @JsonProperty("FLAG")
    private String flag;

    @JsonProperty("MEMO")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
