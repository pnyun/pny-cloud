package com.pny.core.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 
 * @author pmyun
 */
@Data
public class BaseQuery implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1135803337159966583L;

    /**
     * ID
     */
    private Long id;

    /**
     * ID 数组
     */
    private Long[] ids;

    /**
     * ID 集合
     */
    private List<Long> idList;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间-起
     */
    private String createDateFrom;

    /**
     * 创建时间-止
     */
    private String createDateTo;

}
