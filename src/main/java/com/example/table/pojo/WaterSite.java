package com.example.table.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

//水协会支渠站点信息
@TableName("WATER_SITE_B")
public class WaterSite {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private int id;           //主键ID
    @TableField("STCD")
    private String stcd;      //站点编号
    @TableField("STNM")
    private String stnm;      //站点名称
    @TableField("CANALCODE")
    private String canalcode; //渠道编号
    @TableField("CANALNAME")
    private String canalname; //渠道名称
    @TableField("SYSSIGN")
    private String syssign;   //水协会标识
    @TableField("AREA")
    private BigDecimal area;  //管理面积
    @TableField("PX")
    private Integer px;       //排序

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCanalcode() {
        return canalcode;
    }

    public void setCanalcode(String canalcode) {
        this.canalcode = canalcode;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getPx() {
        return px;
    }

    public void setPx(Integer px) {
        this.px = px;
    }
}
