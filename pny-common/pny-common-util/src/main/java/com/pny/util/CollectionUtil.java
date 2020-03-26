package com.pny.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 *
 *
 */
public class CollectionUtil {

    /**
     * 集合是否为空或空集合
     * 
     * @param c
     * @return
     */
    public static boolean isBlank(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /**
     * 集合是否不为空或空集合
     * 
     * @param c
     * @return
     */
    public static boolean isNotBlank(Collection<?> c) {
        return !isBlank(c);
    }

    /**
     * 创建List集合
     * 
     * @return
     */
    public static <T> List<T> newArrayList() {
        List<T> list = new ArrayList<>();
        return list;
    }

    /**
     * 创建List集合,并添加元素
     * 
     * @param t
     * @return
     */
    public static <T> List<T> newArrayListAdd(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }

}
