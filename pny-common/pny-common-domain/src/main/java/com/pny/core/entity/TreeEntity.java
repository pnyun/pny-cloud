package com.pny.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 树结构entity,包含字段id_self和i_level
 *
 * @author pmyun
 */
@Getter
@Setter
@ToString
public abstract class TreeEntity extends NamedEntity implements HasParent {

    private static final long serialVersionUID = -7822029333656681292L;

    @TableField(value = "id_parent", el = "parent.id")
    private Identified parent = new Identified();

    @TableField("i_level")
    private int level;

    public TreeEntity() {
    }

//    protected TreeEntity(long id) {
//        super(id);
//    }
}
