package com.pny.util;

import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

/**
 * 数值工具类
 *
 *
 */
public class NumericUtil {

    /**
     * 将一个对象转换为一个长整型 示例：StrUtil.obj2long("1024",100); 结果：1024
     * 
     * @param o
     *        要进行转换的对象
     * @param
     *        默认值，对象不符合转换条件时返回
     * @return 返回的长整型
     */
    static public final long obj2long(Object o) {
        return obj2long(o, 0);
    }

    /**
     * 将一个对象转换为一个长整型 示例：StrUtil.obj2long("1024",100); 结果：1024
     * 
     * @param o
     *        要进行转换的对象
     * @param defaultValue
     *        默认值，对象不符合转换条件时返回
     * @return 返回的长整型
     */
    static public final long obj2long(Object o, long defaultValue) {
        if (o instanceof Number)
            return ((Number) o).longValue();
        if (o instanceof String) {
            String s = ((String) o).trim();
            if (s.endsWith("L") || s.endsWith("l")) {
                s = s.substring(0, s.length() - 1);
            }
            try {
                return Long.parseLong(s);
            } catch (Exception ex) {
            }
            {
                try {
                    return Double.valueOf(s).longValue();
                } catch (Exception ex) {
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将指定对象转换为整型 示例1：StrUtil.obj2int("1000.0"); 结果：1000 示例2：StrUtil.obj2int(new
     * Integer(1000); 结果：1000 示例3: StrUtil.obj2int("abc"); 结果:0
     * 
     * @param o
     *        要进行转换的对象
     * @return 转换后的整数，如果对象格式不正确则返回0
     */
    static public final int obj2int(Object o) {
        return obj2int(o, 0);
    }

    /**
     * 从一个对象转化为一个整数 示例1：StrUtil.obj2int("abc",100); 结果：100
     * 示例2：StrUtil.obj2int("200",100); 结果：200
     * 
     * @param o
     *        Number,String 类型的对象
     */
    static public final int obj2int(Object o, int defaultValue) {
        if (o instanceof Number)
            return ((Number) o).intValue();
        if (o instanceof String) {
            final String s = ((String) o).trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception ex) {
            }
            {
                try {
                    return Double.valueOf(s).intValue();
                } catch (Exception ex) {
                }
            }
        }
        return defaultValue;
    }

    /**
     * 用于将Double类型和String类型转化为基本类型double 示例1：StrUtil.obj2double("100"); 结果：100.0
     * 示例2：StrUtil.obj2double(new Double(10000.00)); 结果：10000.0
     * 示例3：StrUtil.obj2double(100); 结果：100.0 示例4：StrUtil.obj2double("abc");
     * 结果：0.0
     * 
     * @param o
     *        要转化的对象
     * @return double类型数据,如果传入的参数类型不正确，则返回默认值0
     */
    static public final double obj2double(Object o) {
        return obj2double(o, 0);
    }

    /**
     * 用于将Double类型和String类型转化为基本类型double 示例1：StrUtil.obj2double("100",0);
     * 结果：100.0 示例2：StrUtil.obj2double(new Double(10000.00),0); 结果：10000.0
     * 示例3：StrUtil.obj2double(100,0); 结果：100.0 示例4：StrUtil.obj2double("abc",10);
     * 结果：10.0
     * 
     * @param o
     *        要转化的对象
     * @param defaultValue
     *        如果传入的参数类型不正确，则返回默认值
     * @return double类型数据
     */
    static public final double obj2double(Object o, double defaultValue) {
        if (o instanceof Number)
            return ((Number) o).doubleValue();
        if (o instanceof String) {
            String s = ((String) o).trim();
            try {
                return Double.parseDouble(s);
            } catch (Exception ex) {
            }
        }
        return defaultValue;
    }

    /**
     * 分转元，保留两位小数，四舍五入
     * 
     * @param
     * @return
     */
    static public String fenToYuan(Integer price) {
        if (price == null) {
            return "0.00";
        }

        BigDecimal decimal = new BigDecimal(price);

        return decimal.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 分转元，保留两位小数，四舍五入
     * 
     * @param decimal
     * @return
     */
    static public String fenToYuan(BigDecimal decimal) {
        if (decimal == null) {
            return "";
        }

        return decimal.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 分转元，向下取整
     * 
     * @param decimal
     * @return
     */
    static public String fenToYuanFloor(int price) {

        BigDecimal decimal = new BigDecimal(price);

        return decimal.divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * 元转分
     * 
     * @param
     * @return
     */
    static public int yuanToFen(String payment) {
        if (StringUtils.isBlank(payment)) {
            return 0;
        }

        BigDecimal dec = new BigDecimal(payment);
        dec = dec.multiply(new BigDecimal(100));

        return dec.intValue();
    }

    /**
     * 整数分(四舍五入)
     * 
     * @param decimal
     * @return
     */
    static public int intFen(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        return decimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

}
