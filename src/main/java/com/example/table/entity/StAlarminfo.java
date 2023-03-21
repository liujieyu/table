package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-19
 */
@TableName("ST_AlarmInfo")
public class StAlarminfo extends Model<StAlarminfo> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ID")
    private Integer id;   //ID

    @JsonProperty("STNM")
    private String stnm;  //站点名称

    @JsonProperty("STCD")
    private String stcd;  //站点编码

    @JsonProperty("AlARM")
    private Integer Alarm; //预警等级

    @JsonProperty("TM")
    private Date tm;       //预警时间
    @JsonProperty("TMSTR")
    private String tmstr;  //预警时间显示字符串

    @JsonProperty("CONTENTS")
    private String contents;  //预警内容

    @JsonProperty("MV")
    private Double mv;      //测量值

    @JsonProperty("AlARMV")
    private Double AlarmV;   //预警指标值

    @JsonProperty("MEMO")
    private String memo;    //备注

    /**
     * 预警短信发送标志，已发送为1
     */
    @JsonProperty("SENDSIGN")
    private Integer sendsign;//是否已预警
    @JsonProperty("TYPENAME")
    private String typename; //监测类型名称
    @JsonProperty("ADNM")
    private String adnm;//行政区划名称
    @JsonProperty("ROWID")
    private String rowid; //序号
    @JsonProperty("ALARMNAME")
    private String alarmname;//预警等级名称
    @JsonProperty("SIGNNAME")
    private String signname;//预警名称

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

    public Integer getAlarm() {
        return Alarm;
    }

    public void setAlarm(Integer alarm) {
        Alarm = alarm;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Double getMv() {
        return mv;
    }

    public void setMv(Double mv) {
        this.mv = mv;
    }

    public Double getAlarmV() {
        return AlarmV;
    }

    public void setAlarmV(Double alarmV) {
        AlarmV = alarmV;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSendsign() {
        return sendsign;
    }

    public void setSendsign(Integer sendsign) {
        this.sendsign = sendsign;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getAlarmname() {
        return alarmname;
    }

    public void setAlarmname(String alarmname) {
        this.alarmname = alarmname;
    }

    public String getTmstr() {
        return tmstr;
    }

    public void setTmstr(String tmstr) {
        this.tmstr = tmstr;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
