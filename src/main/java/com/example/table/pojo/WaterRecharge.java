package com.example.table.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
/**
 * <p>
 * 充值信息
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
@TableName("WATER_RECHARGE")
public class WaterRecharge extends Model<WaterRecharge> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;                   //主键ID

    @TableField("FARMCODE")
    private String farmcode;              //农户用户编码

    @TableField("AMOUNT")
    private Integer amount;               //购买金额

    @TableField("CREATETIME")
    private String createtime;            //购买时间

    @TableField("BUYWATER")
    private Integer buywater;             //购买水量

    @TableField("BASEWATER")
    private Integer basewater;            //基础水权

    @TableField("BUYFIRST")
    private Integer buyfirst;             //一级水量

    @TableField("BUYSECOND")
    private Integer buysecond;            //二级水量

    @TableField("BUYTHIRD")
    private Integer buythird;             //三级水量

    @TableField("LASTSURPLUS")
    private Integer lastsurplus;          //上期剩余水量

    @TableField("REMAINWATER")
    private Integer remainwater;          //存余水量

    @TableField("SYSSIGN")
    private String syssign;               //水协会标识

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

    public String getFarmcode() {
        return farmcode;
    }

    public void setFarmcode(String farmcode) {
        this.farmcode = farmcode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getBuywater() {
        return buywater;
    }

    public void setBuywater(Integer buywater) {
        this.buywater = buywater;
    }

    public Integer getBuyfirst() {
        return buyfirst;
    }

    public void setBuyfirst(Integer buyfirst) {
        this.buyfirst = buyfirst;
    }

    public Integer getBuysecond() {
        return buysecond;
    }

    public void setBuysecond(Integer buysecond) {
        this.buysecond = buysecond;
    }

    public Integer getBuythird() {
        return buythird;
    }

    public void setBuythird(Integer buythird) {
        this.buythird = buythird;
    }

    public Integer getLastsurplus() {
        return lastsurplus;
    }

    public void setLastsurplus(Integer lastsurplus) {
        this.lastsurplus = lastsurplus;
    }

    public Integer getRemainwater() {
        return remainwater;
    }

    public void setRemainwater(Integer remainwater) {
        this.remainwater = remainwater;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }
}
