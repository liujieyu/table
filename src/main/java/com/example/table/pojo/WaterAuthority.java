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
 * 系统权限管理（登录信息）
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-11
 */
@TableName("WATER_AUTHORITY")
public class WaterAuthority extends Model<WaterAuthority> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("LOGINNAME")
    private String loginname;

    @TableField("LOGINPASS")
    private String loginpass;

    @TableField("CREATETIME")
    private LocalDateTime createtime;

    @TableField("SYSSIGN")
    private String syssign;
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

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getSyssign() {
        return syssign;
    }

    public void setSyssign(String syssign) {
        this.syssign = syssign;
    }
}
