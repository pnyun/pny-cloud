package com.pny.admin.security.jwt;

import lombok.Data;

/**
 * @author pmyun
 * @since 2020/3/26 16:52
 */
@Data
public class User {

    private Long userId;

    private String username;

    private String realname;

    private String password;

}
