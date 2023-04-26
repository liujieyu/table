package com.example.table.pojo;

/**
 * 农户用户节水信息展示
 */
public class MeterBackShow {
    private Integer index;                 //序号
    private String farmname;              //农户用户
    private Integer area;                 //承包面积
    private String canalname;             //所属渠道
    private Integer wateryield;           //总水量
    private Integer basewater;            //基础水权
    private Integer saveoneup;            //节约一级用水上限
    private Double saveonepr;             //节约一级用水回购单价
    private Integer savetwoup;            //节约二级用水上限
    private Double savetwopr;             //节约二级用水回购单价
    private Double savethrpr;             //节约三级用水回购单价
    private Integer realbase;              //实际基础水权
    private Double backamount;            //总回购金额
    private Integer totalwater;           //总节水量
    private Integer onewater;             //一级节水量
    private Integer twowater;             //二级节水量
    private Integer thridwater;           //三级节水量

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCanalname() {
        return canalname;
    }

    public void setCanalname(String canalname) {
        this.canalname = canalname;
    }

    public Integer getBasewater() {
        return basewater;
    }

    public void setBasewater(Integer basewater) {
        this.basewater = basewater;
    }

    public Integer getSaveoneup() {
        return saveoneup;
    }

    public void setSaveoneup(Integer saveoneup) {
        this.saveoneup = saveoneup;
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

    public Double getSavetwopr() {
        return savetwopr;
    }

    public void setSavetwopr(Double savetwopr) {
        this.savetwopr = savetwopr;
    }

    public Double getSavethrpr() {
        return savethrpr;
    }

    public void setSavethrpr(Double savethrpr) {
        this.savethrpr = savethrpr;
    }

    public Double getBackamount() {
        return backamount;
    }

    public void setBackamount(Double backamount) {
        this.backamount = backamount;
    }

    public Integer getTotalwater() {
        return totalwater;
    }

    public void setTotalwater(Integer totalwater) {
        this.totalwater = totalwater;
    }

    public Integer getOnewater() {
        return onewater;
    }

    public void setOnewater(Integer onewater) {
        this.onewater = onewater;
    }

    public Integer getTwowater() {
        return twowater;
    }

    public void setTwowater(Integer twowater) {
        this.twowater = twowater;
    }

    public Integer getThridwater() {
        return thridwater;
    }

    public void setThridwater(Integer thridwater) {
        this.thridwater = thridwater;
    }

    public Integer getWateryield() {
        return wateryield;
    }

    public void setWateryield(Integer wateryield) {
        this.wateryield = wateryield;
    }

    public Integer getRealbase() {
        return realbase;
    }

    public void setRealbase(Integer realbase) {
        this.realbase = realbase;
    }
}
