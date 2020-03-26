package com.pny.core.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class BaseRequest implements Serializable{

    /**
     * 登录id
     */
    private String loginId;
    /**
     * 登录名
     */
    private String loginName;

}
