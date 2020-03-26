package com.pny.server.upms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pny.core.entity.OtherParamEntity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * cms用户表
 * </p>
 *
 * @author pmyun
 * @since 2019-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemUser  extends OtherParamEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @TableField("accountId")
    private String accountId;
    /**
     * 密码，密文
     */
    @JsonIgnore
    @TableField("password")
    private String password;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 状态 1：启用2：禁用
     */
    @TableField("status")
    private Integer status;
    /**
     * 角色id
     */
    @TableField("roleId")
    private Long roleId;

    /**
     * 最后登录时间
     */
    @TableField("lastLoginTime")
    private Date lastLoginTime;


}
