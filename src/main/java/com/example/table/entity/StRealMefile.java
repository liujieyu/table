package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-29
 */
@TableName("ST_REAL_MEFILE")
public class StRealMefile extends Model<StRealMefile> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("TM")
    private Date tm;

    @JsonProperty("ME_CODE")
    private String meCode;

    @JsonProperty("FILE_NAME")
    private String fileName;

    @JsonProperty("UP_MAN")
    private String upMan;

    @JsonProperty("SAVE_PATH")
    private String savePath;

    @JsonProperty("ME_MAN")
    private String meMan;

    @JsonProperty("AUDINTING")
    private Integer audinting;

    @JsonProperty("AUDI_MAN")
    private String audiMan;

    @JsonProperty("AUDI_TM")
    private Date audiTm;

    @JsonProperty("MEMO")
    private String memo;

    @JsonProperty("ME_TM")
    private Date meTm;

    @JsonProperty("TMSTR")
    private String tmstr;

    @JsonProperty("ME_TMSTR")
    private String meTmstr;

    @JsonProperty("RESULT")
    private  String result;

    @JsonProperty("ROWID")
    private Integer rowId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public String getMeCode() {
        return meCode;
    }

    public void setMeCode(String meCode) {
        this.meCode = meCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpMan() {
        return upMan;
    }

    public void setUpMan(String upMan) {
        this.upMan = upMan;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getMeMan() {
        return meMan;
    }

    public void setMeMan(String meMan) {
        this.meMan = meMan;
    }

    public Integer getAudinting() {
        return audinting;
    }

    public void setAudinting(Integer audinting) {
        this.audinting = audinting;
    }

    public String getAudiMan() {
        return audiMan;
    }

    public void setAudiMan(String audiMan) {
        this.audiMan = audiMan;
    }

    public Date getAudiTm() {
        return audiTm;
    }

    public void setAudiTm(Date audiTm) {
        this.audiTm = audiTm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getMeTm() {
        return meTm;
    }

    public void setMeTm(Date meTm) {
        this.meTm = meTm;
    }

    public String getTmstr() {
        return tmstr;
    }

    public void setTmstr(String tmstr) {
        this.tmstr = tmstr;
    }

    public String getMeTmstr() {
        return meTmstr;
    }

    public void setMeTmstr(String meTmstr) {
        this.meTmstr = meTmstr;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
