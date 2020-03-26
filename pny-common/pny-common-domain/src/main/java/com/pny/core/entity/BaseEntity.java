package com.pny.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 包含id，createTime,updateTime
 *
 * @author pmyun
 */
@JsonInclude(Include.NON_NULL)
@Data
@ToString
public class BaseEntity{

    private static final long serialVersionUID = 1245857421815854333L;

    @TableField("id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "createTime")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updateTime")
    private Date updateTime;

//    @TableField("status")
//    @TableLogic(delval = "2")
//    private Integer deleted;

    @JsonIgnore
    @TableField(exist = false)
    private String signature;

    public BaseEntity() {
    }

//    public void delete() {
//        setDeleted(2);
//    }

    @JsonIgnore
    public String getSignature() {
        return signature;
    }


}
