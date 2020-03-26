package com.pny.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 *
 * @author pmyun
 */
@Getter
@Setter
@ToString
public class NamedEntity extends BaseEntity implements HasName {

    private static final long serialVersionUID = -2540976120251573233L;

    @TableField("name")
    private String name;

    public NamedEntity() {

    }


}
