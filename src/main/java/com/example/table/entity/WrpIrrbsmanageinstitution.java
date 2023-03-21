package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2019-12-10
 */
@TableName("WRP_IrrBSManageInstitution")
public class WrpIrrbsmanageinstitution extends Model<WrpIrrbsmanageinstitution> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Organ_Code")
    private String organCode;

    @JsonProperty("Organ_Name")
    private String organName;

    @JsonProperty("TYPES")
    private Integer types;

    @JsonProperty("CANAL_CODE")
    private String canalCode;

    @JsonProperty("CANAL_NANME")
    private String canalName;

    @JsonProperty("children")
    private List<WrpIrrbsmanageinstitution> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
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

    public List<WrpIrrbsmanageinstitution> getChildren() {
        return children;
    }

    public void setChildren(List<WrpIrrbsmanageinstitution> children) {
        this.children = children;
    }

    @Override
    protected Serializable pkVal() {
        return this.organCode;
    }

}
