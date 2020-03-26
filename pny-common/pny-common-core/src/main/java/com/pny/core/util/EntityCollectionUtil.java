package com.pny.core.util;

import com.pny.core.entity.Identified;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实体集合工具
 *
 *
 */
public class EntityCollectionUtil<T> {

    /**
     * 获取集合内的ID
     * 
     * @param c
     * @return
     */
    public static List<Long> getEntityId(Collection<? extends Identified> c) {
        List<Long> ids = c.stream().map(i -> i.getId()).collect(Collectors.toList());
        return ids;
    }

    /**
     * 获取集合内的ID
     * 
     * @param c
     * @return
     */
    public static <T> List<Long> getEntityId(Collection<T> c,
            Function<? super T, ? extends Long> function) {

        List<Long> ids = c.stream().map(function).collect(Collectors.toList());

        return ids;
    }

    /**
     * 按照ID分组
     * 
     * @param c
     * @return
     */
    public static <T> Map<Long, List<T>> groupingById(Collection<T> c,
            Function<? super T, Long> function) {

        Map<Long, List<T>> map = c.stream().collect(Collectors.groupingBy(function));

        return map;
    }

    // /**
    // * 查询关系表数据
    // *
    // * @param function
    // * @return
    // */
    // public static <T, R> List<R> getRelationship(List<T> c, IService<R>
    // service, Class<? extends Object> bClass) {
    //
    // if (CollectionUtil.isBlank(c)) {
    // return new ArrayList<>();
    // }
    //
    // T t = c.get(0);
    //
    // // A 对象的Class
    // Class<? extends Object> aClass = t.getClass();
    //
    // // A 的名称
    // String aName = Reflects.firstLetterLowerCase(aClass.getSimpleName());
    //
    // Field f = Reflects.getField(bClass, aName);
    // TableField tableField = f.getAnnotation(TableField.class);
    // String columnName = tableField.value();
    //
    // service.getClass().getTypeName();
    //
    // // TableName tableName = null;
    // // // 是否有转换目标类
    // // if (aClass.isAnnotationPresent(TableName.class)) {
    // // tableName = aClass.getAnnotation(TableName.class);
    // // } else {
    // // return new ArrayList<>();
    // // }
    //
    // // String dbTable = tableName.value();
    //
    // EntityWrapper<R> wrapper = EntityWrapperUtil.of();
    // wrapper.in(columnName, "");
    //
    // return new ArrayList<>();
    // //
    // // EntityWrapper<R> wrapper = EntityWrapperUtil.of();
    // //
    // // T t;
    // //
    // // Class<? extends Object> clazz = t.getClass();
    // //
    // // Reflects.getField(t, fieldName);
    // //
    // // wrapper.eq("", params);
    // //
    // // return ids;
    // }

}
