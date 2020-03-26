package com.pny.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 *
 */
@Getter
@Setter
@ToString
public class OtherParamEntity extends BaseEntity{

    private static final long serialVersionUID = -2540976120251573233L;

    /**
     * 创建人
     */
    @TableField("createPersonId")
    private String createPersonId;

    /**
     * 修改人id
     */
    @TableField("updatedPersonId")
    private String updatedPersonId;


}
