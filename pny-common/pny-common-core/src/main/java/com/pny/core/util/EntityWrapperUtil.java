package com.pny.core.util;

import com.pny.core.entity.CommonQuery;
import com.pny.util.CollectionUtil;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
/**
 * EntityWrapper 工具
 *
 *
 */
public class EntityWrapperUtil<T> {

    public static <T> EntityWrapper<T> of() {

        EntityWrapper<T> wrapper = new EntityWrapper<>();
        wrapper.isWhere(false);

        return wrapper;
    }

    /**
     * 分页查询
     * 
     * @param service
     * @param page
     *        分页对像，不为null时使用selectPage；为null时使用selectList
     *        查询实体
     * @param wrapper
     * @return
     */
    public static <T> List<T> queryPage(
            IService<T> service,
            Page<T> page,
            EntityWrapper<T> wrapper) {

        if (service == null || wrapper == null) {
            return new ArrayList<>();
        }

        if (page != null) {
            service.selectPage(page, wrapper);
            return page.getRecords();
        }

        return service.selectList(wrapper);
    }

    /**
     * 通用查询参数查询
     * 
     * @param query
     * @return
     */
    public static <T> EntityWrapper<T> commonQuery(CommonQuery query) {

        EntityWrapper<T> wrapper = of();

        if (query == null) {
            return wrapper;
        }

        // ID查询
        if (query.getId() != null) {
            wrapper.eq("id", query.getId());
        }
        // ID 集合
        if (CollectionUtil.isNotBlank(query.getIdList())) {
            wrapper.in("id", query.getIdList());
        }

        if (query.getName() != null) {
            wrapper.like("name", query.getName());
        }

        // 创建时间
        if (!ObjectUtils.isEmpty(query.getCreateDateFrom())) {
            wrapper.ge("DATE_FORMAT(gmt_create,'%Y-%m-%d')", query.getCreateDateFrom());
        }
        if (!ObjectUtils.isEmpty(query.getCreateDateTo())) {
            wrapper.le("DATE_FORMAT(gmt_create,'%Y-%m-%d')", query.getCreateDateTo());
        }

        // 排序
        if (CollectionUtil.isNotBlank(query.getOrderByList())) {
            query.getOrderByList().stream().forEach(o -> {
                wrapper.orderBy(o.getColumn(), o.getIsAsc() == null ? true : o.getIsAsc());
            });
        }

        return wrapper;
    }

}
