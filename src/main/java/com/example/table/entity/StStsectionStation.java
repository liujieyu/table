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
 * @since 2019-12-17
 */
@TableName("ST_STSECTION_STATION")
public class StStsectionStation extends Model<StStsectionStation> {

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

    @JsonProperty("B")
    private Double b;

    @JsonProperty("M")
    private Double m;

    @JsonProperty("N")
    private Double n;

    @JsonProperty("I")
    private Double i;

    @JsonProperty("TYPES")
    private Integer types;

    @JsonProperty("TYPENAME")
    private String typename;

    @JsonProperty("MAX_Z")
    private Double maxZ;

    @JsonProperty("MAX_Q")
    private Double maxQ;

    @JsonProperty("FORMULA")
    private String formula;

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

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Double getM() {
        return m;
    }

    public void setM(Double m) {
        this.m = m;
    }

    public Double getN() {
        return n;
    }

    public void setN(Double n) {
        this.n = n;
    }

    public Double getI() {
        return i;
    }

    public void setI(Double i) {
        this.i = i;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(Double maxZ) {
        this.maxZ = maxZ;
    }

    public Double getMaxQ() {
        return maxQ;
    }

    public void setMaxQ(Double maxQ) {
        this.maxQ = maxQ;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
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
