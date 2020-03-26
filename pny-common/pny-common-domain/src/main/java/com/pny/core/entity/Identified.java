package com.pny.core.entity;

import static java.util.Objects.hash;
import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 包含id字段，重写equals方法和hashcode
 *
 * @author pmyun
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString
public class Identified extends Model<Identified> implements HasId, Serializable {

    private static final long serialVersionUID = 1960358120486046226L;

    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    protected Identified() {

    }

    protected Identified(Long id) {
        this.id = id;
    }

    protected Identified(Identified copy) {
        this(copy.id);
    }

    @Override
    public int hashCode() {
        return hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (!(obj instanceof HasId)){
            return false;
        }
        HasId other = (HasId) obj;

        return Objects.equals(getId(), other.getId());
    }

    @JsonIgnore
    public boolean isFirst() {
        return getId() == 1;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
