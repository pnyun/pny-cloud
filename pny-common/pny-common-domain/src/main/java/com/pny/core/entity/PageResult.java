package com.pny.core.entity;

import java.util.List;

/**
 * 分页查询返回结果的封装结果
 */
public class PageResult<T> {

    /**
     * 当前页
     */
    private int current = 1;

    private Long total;

    private List<T> rows;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int total) {
        this.current = current;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
        super();
    }
}
