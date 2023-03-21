package com.example.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-20
 */
@TableName("WRP_FieldInfo")
public class WrpFieldinfo extends Model<WrpFieldinfo> {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "ID")
    private Integer id;

    @JsonProperty("FormANDFieldID")
    private String FormANDFieldID;

    @JsonProperty("FormID")
    private String FormID;

    @JsonProperty("FieldID")
    private String FieldID;

    @JsonProperty("Field")
    private String Field;

    @JsonProperty("FieldName")
    private String FieldName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormANDFieldID() {
        return FormANDFieldID;
    }

    public void setFormANDFieldID(String formANDFieldID) {
        FormANDFieldID = formANDFieldID;
    }

    public String getFormID() {
        return FormID;
    }

    public void setFormID(String formID) {
        FormID = formID;
    }

    public String getFieldID() {
        return FieldID;
    }

    public void setFieldID(String fieldID) {
        FieldID = fieldID;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
