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
 * 读水表信息
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
@TableName("WATER_METER")
public class WaterMeter extends Model<WaterMeter> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;                       //主键ID

    @TableField("FARMCODE")
    private String farmcode;                  //农户用户编码

    @TableField("WATERNUM")
    private Integer waternum;                 //水表读数

    @TableField("READTIME")
    private String readtime;                  //读数时间

    @TableField("WATERYIELD")
    private Integer wateryield;               //水量

    @TableField("LASTNUM")
    private Integer lastnum;                  //上期读数

    @TableField("YIELDBASE")
    private Integer yieldbase;                //基础水权

    @TableField("YIELDFIRST")
    private Integer yieldfirst;               //一级水量

    @TableField("YIELDSECOND")
    private Integer yieldsecond;              //二级水量

    @TableField("YIELDTHIRD")
    private Integer yieldthird;               //三级水量

    @TableField("WATERRATE")
    private Double waterrate;                 //水费

    @TableField("AVAILABLE")
    private Double available;                 //可用余额

    @TableField("SURPLUS")
    private Integer surplus;                  //剩余水量

    @TableField("PAYTIME")
    private String paytime;                   //缴费时间

    @TableField("SYSSIGN")
    private String syssign;                   //水协会标识

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

    public Integer getWaternum() {
        return waternum;
    }

    public void setWaternum(Integer waternum) {
        this.waternum = waternum;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public Integer getWateryield() {
        return wateryield;
    }

    public void setWateryield(Integer wateryield) {
        this.wateryield = wateryield;
    }

    public Integer getYieldfirst() {
        return yieldfirst;
    }

    public void setYieldfirst(Integer yieldfirst) {
        this.yieldfirst = yieldfirst;
    }

    public Integer getYieldsecond() {
        return yieldsecond;
    }

    public void setYieldsecond(Integer yieldsecond) {
        this.yieldsecond = yieldsecond;
    }

    public Integer getYieldthird() {
        return yieldthird;
    }

    public void setYieldthird(Integer yieldthird) {
        this.yieldthird = yieldthird;
    }

    public Double getWaterrate() {
        return waterrate;
    }

    public void setWaterrate(Double waterrate) {
        this.waterrate = waterrate;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }

    public Integer getYieldbase() {
        return yieldbase;
    }

    public void setYieldbase(Integer yieldbase) {
        this.yieldbase = yieldbase;
    }

    public Integer getLastnum() {
        return lastnum;
    }

    public void setLastnum(Integer lastnum) {
        this.lastnum = lastnum;
    }
}
