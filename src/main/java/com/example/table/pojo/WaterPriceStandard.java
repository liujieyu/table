package com.example.table.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 执行水价标准
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-03
 */
@TableName("WATER_PRICE_STANDARD")
public class WaterPriceStandard extends Model<WaterPriceStandard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;                     //主键ID

    @TableField("PRICEYR")
    private Integer priceyr;                //定价年份

    @TableField("BASEWATER")
    private Integer basewater;              //基础水权

    @TableField("BASEPRICE")
    private Double baseprice;               //基础水价

    @TableField("ONEUPLIM")
    private Integer oneuplim;               //超额一级用水上限

    @TableField("ONELOLIM")
    private Integer onelolim;               //超额一级用水下限

    @TableField("ONEPRICE")
    private Double oneprice;                //超额一级用水单价

    @TableField("TWOUPLIM")
    private Integer twouplim;               //超额二级用水上限

    @TableField("TWOLOLIM")
    private Integer twololim;               //超额二级用水下限

    @TableField("TOWPRICE")
    private Double towprice;                //超额二级用水单价

    @TableField("THRUPLIM")
    private Integer thruplim;               //超额三级用水上限

    @TableField("THRLOLIM")
    private Integer thrlolim;               //超额三级用水下限

    @TableField("THRPRICE")
    private Double thrprice;                //超额三级用水单价

    @TableField("SAVEONEUP")
    private Integer saveoneup;              //节约一级用水上限

    @TableField("SAVEONELO")
    private Integer saveonelo;              //节约一级用水下限

    @TableField("SAVEONEPR")
    private Double saveonepr;               //节约一级用水回购单价

    @TableField("SAVETWOUP")
    private Integer savetwoup;              //节约二级用水上限

    @TableField("SAVETWOLO")
    private Integer savetwolo;              //节约二级用水下限

    @TableField("SAVETWOPR")
    private Double savetwopr;               //节约二级用水回购单价

    @TableField("SAVETHRUP")
    private Integer savethrup;              //节约三级用水上限

    @TableField("SAVETHRLO")
    private Integer savethrlo;              //节约三级用水下限

    @TableField("SAVETHRPR")
    private Double savethrpr;               //节约三级用水回购单价

    @TableField("CANALRATE")
    private Double canalrate;               //渠系利用率


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

    public Integer getPriceyr() {
        return priceyr;
    }

    public void setPriceyr(Integer priceyr) {
        this.priceyr = priceyr;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }

    public Double getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(Double baseprice) {
        this.baseprice = baseprice;
    }

    public Integer getOneuplim() {
        return oneuplim;
    }

    public void setOneuplim(Integer oneuplim) {
        this.oneuplim = oneuplim;
    }

    public Integer getOnelolim() {
        return onelolim;
    }

    public void setOnelolim(Integer onelolim) {
        this.onelolim = onelolim;
    }

    public Double getOneprice() {
        return oneprice;
    }

    public void setOneprice(Double oneprice) {
        this.oneprice = oneprice;
    }

    public Integer getTwouplim() {
        return twouplim;
    }

    public void setTwouplim(Integer twouplim) {
        this.twouplim = twouplim;
    }

    public Integer getTwololim() {
        return twololim;
    }

    public void setTwololim(Integer twololim) {
        this.twololim = twololim;
    }

    public Double getTowprice() {
        return towprice;
    }

    public void setTowprice(Double towprice) {
        this.towprice = towprice;
    }

    public Integer getThruplim() {
        return thruplim;
    }

    public void setThruplim(Integer thruplim) {
        this.thruplim = thruplim;
    }

    public Integer getThrlolim() {
        return thrlolim;
    }

    public void setThrlolim(Integer thrlolim) {
        this.thrlolim = thrlolim;
    }

    public Double getThrprice() {
        return thrprice;
    }

    public void setThrprice(Double thrprice) {
        this.thrprice = thrprice;
    }

    public Integer getSaveoneup() {
        return saveoneup;
    }

    public void setSaveoneup(Integer saveoneup) {
        this.saveoneup = saveoneup;
    }

    public Integer getSaveonelo() {
        return saveonelo;
    }

    public void setSaveonelo(Integer saveonelo) {
        this.saveonelo = saveonelo;
    }

    public Double getSaveonepr() {
        return saveonepr;
    }

    public void setSaveonepr(Double saveonepr) {
        this.saveonepr = saveonepr;
    }

    public Integer getSavetwoup() {
        return savetwoup;
    }

    public void setSavetwoup(Integer savetwoup) {
        this.savetwoup = savetwoup;
    }

    public Integer getSavetwolo() {
        return savetwolo;
    }

    public void setSavetwolo(Integer savetwolo) {
        this.savetwolo = savetwolo;
    }

    public Double getSavetwopr() {
        return savetwopr;
    }

    public void setSavetwopr(Double savetwopr) {
        this.savetwopr = savetwopr;
    }

    public Integer getSavethrup() {
        return savethrup;
    }

    public void setSavethrup(Integer savethrup) {
        this.savethrup = savethrup;
    }

    public Integer getSavethrlo() {
        return savethrlo;
    }

    public void setSavethrlo(Integer savethrlo) {
        this.savethrlo = savethrlo;
    }

    public Double getSavethrpr() {
        return savethrpr;
    }

    public void setSavethrpr(Double savethrpr) {
        this.savethrpr = savethrpr;
    }

    public Double getCanalrate() {
        return canalrate;
    }

    public void setCanalrate(Double canalrate) {
        this.canalrate = canalrate;
    }
}
