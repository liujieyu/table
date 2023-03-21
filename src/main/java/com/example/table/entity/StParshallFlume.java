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
@TableName("ST_PARSHALL_FLUME")
public class StParshallFlume extends Model<StParshallFlume> {

    private static final long serialVersionUID = 1L;
    @JsonProperty("ROWID")
    private Integer rowid;

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("TYPES")
    private String types;

    @JsonProperty("MODEL")
    private Integer model;

    @JsonProperty("Z_MIN")
    private Double zMin;

    @JsonProperty("Z_MAX")
    private Double zMax;

    @JsonProperty("Q_MIN")
    private Double qMin;

    @JsonProperty("Q_MAX")
    private Double qMax;

    @JsonProperty("YMD")
    private Double ymd;

    @JsonProperty("SIZE")
    private String size;

    @JsonProperty("B")
    private Double b;

    @JsonProperty("L")
    private Double l;

    @JsonProperty("N")
    private Double n;

    @JsonProperty("B1")
    private Double b1;

    @JsonProperty("L1")
    private Double l1;

    @JsonProperty("LA")
    private Double la;

    @JsonProperty("B2")
    private Double b2;

    @JsonProperty("L2")
    private Double l2;

    @JsonProperty("K")
    private Double k;

    @JsonProperty("D")
    private Double d;

    @JsonProperty("C")
    private Double c;

    @JsonProperty("N1")
    private Double n1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Double getzMin() {
        return zMin;
    }

    public void setzMin(Double zMin) {
        this.zMin = zMin;
    }

    public Double getzMax() {
        return zMax;
    }

    public void setzMax(Double zMax) {
        this.zMax = zMax;
    }

    public Double getqMin() {
        return qMin;
    }

    public void setqMin(Double qMin) {
        this.qMin = qMin;
    }

    public Double getqMax() {
        return qMax;
    }

    public void setqMax(Double qMax) {
        this.qMax = qMax;
    }

    public Double getYmd() {
        return ymd;
    }

    public void setYmd(Double ymd) {
        this.ymd = ymd;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getN() {
        return n;
    }

    public void setN(Double n) {
        this.n = n;
    }

    public Double getB1() {
        return b1;
    }

    public void setB1(Double b1) {
        this.b1 = b1;
    }

    public Double getL1() {
        return l1;
    }

    public void setL1(Double l1) {
        this.l1 = l1;
    }

    public Double getLa() {
        return la;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    public Double getB2() {
        return b2;
    }

    public void setB2(Double b2) {
        this.b2 = b2;
    }

    public Double getL2() {
        return l2;
    }

    public void setL2(Double l2) {
        this.l2 = l2;
    }

    public Double getK() {
        return k;
    }

    public void setK(Double k) {
        this.k = k;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getN1() {
        return n1;
    }

    public void setN1(Double n1) {
        this.n1 = n1;
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
