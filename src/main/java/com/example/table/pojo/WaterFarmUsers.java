package com.example.table.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
/**
 * <p>
 * 农户承包用户信息表
 * </p>
 *
 * @author liujieyu
 * @since 2023-03-31
 */
@TableName("WATER_FARM_USERS")
public class WaterFarmUsers extends Model<WaterFarmUsers> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;                    //主键ID

    @TableField("FARMCODE")
    private String farmcode;               //农户用户编号

    @TableField("FARMNAME")
    private String farmname;               //农户用户名

    @TableField("CANALCODE")
    private String canalcode;              //渠道编号

    @TableField("AREA")
    private Integer area;                  //承包面积

    @TableField("CONTACTS")
    private String contacts;               //办卡人

    @TableField("CONPHONE")
    private String conphone;               //联系方式

    @TableField("CARDID")
    private String cardid;                 //身份证号

    @TableField("CARDDATE")
    private String carddate;               //办卡时间

    @TableField("SYSSIGN")
    private String syssign;                //水协会标识

    @TableField("ABLESIGN")
    private int ablesign;                  //是否注销  0 注销 1 启用



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public String getCanalcode() {
        return canalcode;
    }

    public void setCanalcode(String canalcode) {
        this.canalcode = canalcode;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getConphone() {
        return conphone;
    }

    public void setConphone(String conphone) {
        this.conphone = conphone;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }

    public String getFarmcode() {
        return farmcode;
    }

    public void setFarmcode(String farmcode) {
        this.farmcode = farmcode;
    }

    public String getCarddate() {
        return carddate;
    }

    public void setCarddate(String carddate) {
        this.carddate = carddate;
    }

    public int getAblesign() {
        return ablesign;
    }

    public void setAblesign(int ablesign) {
        this.ablesign = ablesign;
    }

}
