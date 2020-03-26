package com.pny.server.upms.entity.ex;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author: pmyun
 */
@Data
@ToString
public class SystemUserDto {

    private Long id;

    private String accountId;

    private String username;

    private Integer status;

    private String createPersonId;

    private Date createTime;

    private String updatedPersonId;

    private Date updateTime;

    private String phone;

    private Date lastLoginTime;

    private String password;

}
