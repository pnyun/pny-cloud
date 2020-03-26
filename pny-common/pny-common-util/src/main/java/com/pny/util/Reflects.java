package com.pny.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Java reflection utilities.
 */
public final class Reflects {

    private Reflects() {
    }

    /**
     * Gets the class of the first argument in a generic type.
     * 
     * @param klass
     *        the class of a generic type.
     * @return the class of the first argument.
     */
    public static Class<?> getGenericClass(Class<?> klass) {
        return getGenericClassByIndex(klass, 0);
    }

    public static Class<?> getGenericClassByIndex(Class<?> klass, int index) {
        ParameterizedType type = (ParameterizedType) klass
                .getGenericSuperclass();
        Type[] arguments = type.getActualTypeArguments();

        return (Class<?>) arguments[index];
    }

    /**
     * 利用反射获取指定对象的指定属性
     * 
     * @param obj
     *        目标对象
     * @param fieldName
     *        目标属性
     * @return 目标属性的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     * 
     * @param obj
     *        目标对象
     * @param fieldName
     *        目标属性
     * @param fieldValue
     *        目标值
     */
    public static void setFieldValue(Object obj, String fieldName,
            String fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     * 
     * @param obj
     *        目标对象
     * @param fieldName
     *        目标属性
     * @return 目标字段
     */
    public static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj
                .getClass(); clazz != Object.class; clazz = clazz
                        .getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     * 
     * @param obj
     *        目标对象
     * @param fieldName
     *        目标属性
     * @return 目标字段
     */
    public static Field getField(Class<? extends Object> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName,
            final Object value) {
        Field field = getField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String firstLetterUpperCase(String str) {

        String fristLetter = str.substring(0, 1).toUpperCase();
        String otherLetter = str.substring(1, str.length());

        return fristLetter + otherLetter;
    }

    public static String firstLetterLowerCase(String str) {

        String fristLetter = str.substring(0, 1).toLowerCase();
        String otherLetter = str.substring(1, str.length());

        return fristLetter + otherLetter;
    }
}
