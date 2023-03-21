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
 * @since 2019-09-27
 */
@TableName("sms_boxreceived")
public class SmsBoxreceived extends Model<SmsBoxreceived> {

    private static final long serialVersionUID = 1L;
    @JsonProperty("ID")
    private int id;
    @JsonProperty("SENDER")
    private String sender;
    @JsonProperty("RECEIVER")
    private String receiver;
    @JsonProperty("CONTENT")
    private String content;
    @JsonProperty("INSERTTIME")
    private String inserttime;
    @JsonProperty("MODULEID")
    private String moduleid;
    @JsonProperty("READED")
    private String readed;
    @JsonProperty("MARK")
    private String mark;
    @JsonProperty("READTIME")
    private String readtime;
    @JsonProperty("MOBILEOWNER")
    private String mobileowner;
    @JsonProperty("CID")
    private String cid;
    @JsonProperty("LINKID1")
    private String linkid1;
    @JsonProperty("LINKID2")
    private String linkid2;
    @JsonProperty("SMSTITLE")
    private String smstitle;
    @JsonProperty("TPPID")
    private Integer tpPid;
    @JsonProperty("TPUDHI")
    private Integer tpUdhi;
    @JsonProperty("ROWID")
    private Integer rowid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public String getMobileowner() {
        return mobileowner;
    }

    public void setMobileowner(String mobileowner) {
        this.mobileowner = mobileowner;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLinkid1() {
        return linkid1;
    }

    public void setLinkid1(String linkid1) {
        this.linkid1 = linkid1;
    }

    public String getLinkid2() {
        return linkid2;
    }

    public void setLinkid2(String linkid2) {
        this.linkid2 = linkid2;
    }

    public String getSmstitle() {
        return smstitle;
    }

    public void setSmstitle(String smstitle) {
        this.smstitle = smstitle;
    }

    public Integer getTpPid() {
        return tpPid;
    }

    public void setTpPid(Integer tpPid) {
        this.tpPid = tpPid;
    }

    public Integer getTpUdhi() {
        return tpUdhi;
    }

    public void setTpUdhi(Integer tpUdhi) {
        this.tpUdhi = tpUdhi;
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
