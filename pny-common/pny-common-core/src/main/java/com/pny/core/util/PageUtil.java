package com.pny.core.util;

import com.pny.core.entity.CommonQuery;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 分页工具
 * 
 * @author pnyun
 *
 */
public class PageUtil<T> {

    public static <T> Page<T> of(Optional<Integer> pageNo, Optional<Integer> pageSize) {

        Page<T> page = new Page<>();

        // 不存在页码，使用默认页码（1）
        page.setCurrent(pageNo.orElse(page.getCurrent()));

        // 不存在分页条数，使用默认条数(10)
        page.setSize(pageSize.orElse(page.getSize()));

        return page;
    }

    public static <T> Page<T> of(Integer pageNo, Integer pageSize) {

        Page<T> page = new Page<>();

        // 不存在页码，使用默认页码（1）
        page.setCurrent(pageNo == null ? page.getCurrent() : pageNo);

        // 不存在分页条数，使用默认条数(10)
        page.setSize(pageSize == null ? page.getSize() : pageSize);

        return page;
    }

    public static <T> Page<T> of(CommonQuery query) {

        Page<T> page = new Page<>();

        // 不存在页码，使用默认页码（1）
        page.setCurrent((query == null || query.getPageIndex() == null) ? page.getCurrent() : query.getPageIndex());

        // 不存在分页条数,查询全部数据
        page.setSize((query == null || query.getPageSize() == null) ? Page.NO_ROW_LIMIT : query.getPageSize());

        return page;
    }

    /**
     * 解析查询参数
     *
     * @param param
     * @return
     */
    public static <T extends CommonQuery> T getQuery(String param, Class<T> cla) {

        T query = JSONObject.parseObject(param, cla);

        if (query == null) {
            try {
                query = cla.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return query;
    }

}
