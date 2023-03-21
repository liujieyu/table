package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2019-12-18
 */
@TableName("ST_Gate_Station")
public class StGateStation extends Model<StGateStation> {

    private static final long serialVersionUID = 1L;
    @JsonProperty("ROWID")
    private Integer rowid;

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("STCD")
    private String stcd;

    @JsonProperty("STNM")
    private String stnm;

    @JsonProperty("CANAL_CODE")
    private String canalCode;

        @JsonProperty("CANAL_NAME")
    private String canalName;

    @JsonProperty("SLCD")
    private String slcd;

    @JsonProperty("SLTP")
    private String sltp;

    @JsonProperty("SLTPNAME")
    private String sltpname;

    @JsonProperty("GTHLNTWD")
    private Double gthlntwd;

    @JsonProperty("GATE_HI")
    private Double gateHi;

    @JsonProperty("Z_MAX_F")
    private Double zMaxF;

    @JsonProperty("Q_MAX_F")
    private Double qMaxF;

    @JsonProperty("Z_MAX_B")
    private Double zMaxB;

    @JsonProperty("Q_MAX_B")
    private Double qMaxB;

    @JsonProperty("MEMO")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCanalCode() {
        return canalCode;
    }

    public void setCanalCode(String canalCode) {
        this.canalCode = canalCode;
    }

    public String getCanalName() {
        return canalName;
    }

    public void setCanalName(String canalName) {
        this.canalName = canalName;
    }

    public String getSlcd() {
        return slcd;
    }

    public void setSlcd(String slcd) {
        this.slcd = slcd;
    }

    public String getSltp() {
        return sltp;
    }

    public void setSltp(String sltp) {
        this.sltp = sltp;
    }

    public String getSltpname() {
        return sltpname;
    }

    public void setSltpname(String sltpname) {
        this.sltpname = sltpname;
    }

    public Double getGthlntwd() {
        return gthlntwd;
    }

    public void setGthlntwd(Double gthlntwd) {
        this.gthlntwd = gthlntwd;
    }

    public Double getGateHi() {
        return gateHi;
    }

    public void setGateHi(Double gateHi) {
        this.gateHi = gateHi;
    }

    public Double getzMaxF() {
        return zMaxF;
    }

    public void setzMaxF(Double zMaxF) {
        this.zMaxF = zMaxF;
    }

    public Double getqMaxF() {
        return qMaxF;
    }

    public void setqMaxF(Double qMaxF) {
        this.qMaxF = qMaxF;
    }

    public Double getzMaxB() {
        return zMaxB;
    }

    public void setzMaxB(Double zMaxB) {
        this.zMaxB = zMaxB;
    }

    public Double getqMaxB() {
        return qMaxB;
    }

    public void setqMaxB(Double qMaxB) {
        this.qMaxB = qMaxB;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
