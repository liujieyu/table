package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
@TableName("ST_STRATING_M")
public class StStratingM extends Model<StStratingM> {

    private static final long serialVersionUID = 1L;

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

    @JsonProperty("CANAL_COORDINATE")
    private String canalCoordinate;

    @JsonProperty("ME_TYPE")
    private Integer meType;

    @JsonProperty("BEGIN_SIGN")
    private Integer beginSign;

    @JsonProperty("BTM")
    private LocalDateTime btm;

    @JsonProperty("ETM")
    private LocalDateTime etm;

    @JsonProperty("RS_COUNT")
    private Integer rsCount;

    @JsonProperty("END_COUNT")
    private Integer endCount;

    @JsonProperty("POSTION")
    private String Postion;

    @JsonProperty("MEMO")
    private String memo;
    @JsonProperty("ZD_SIGN")
    private Integer zd_sign;
    @JsonProperty("TYPE_NM")
    private String type_nm;

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

    public String getCanalCoordinate() {
        return canalCoordinate;
    }

    public void setCanalCoordinate(String canalCoordinate) {
        this.canalCoordinate = canalCoordinate;
    }

    public Integer getMeType() {
        return meType;
    }

    public void setMeType(Integer meType) {
        this.meType = meType;
    }

    public Integer getBeginSign() {
        return beginSign;
    }

    public void setBeginSign(Integer beginSign) {
        this.beginSign = beginSign;
    }

    public LocalDateTime getBtm() {
        return btm;
    }

    public void setBtm(LocalDateTime btm) {
        this.btm = btm;
    }

    public LocalDateTime getEtm() {
        return etm;
    }

    public void setEtm(LocalDateTime etm) {
        this.etm = etm;
    }

    public Integer getRsCount() {
        return rsCount;
    }

    public void setRsCount(Integer rsCount) {
        this.rsCount = rsCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public String getPostion() {
        return Postion;
    }

    public void setPostion(String postion) {
        Postion = postion;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getZd_sign() {
        return zd_sign;
    }

    public void setZd_sign(Integer zd_sign) {
        this.zd_sign = zd_sign;
    }

    public String getType_nm() {
        return type_nm;
    }

    public void setType_nm(String type_nm) {
        this.type_nm = type_nm;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
