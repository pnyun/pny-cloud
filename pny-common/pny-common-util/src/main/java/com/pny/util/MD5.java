package com.pny.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 */
public class MD5 {

    private static final String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
        "b", "c", "d", "e",
        "f"};

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return digits[iD1] + digits[iD2];
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0, length = bByte.length; i < length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String getMD5Code(String... objs) throws RuntimeException {
        StringBuffer buffer = new StringBuffer(100);
        for (String obj : objs) {
            buffer.append(obj);
        }
        MessageDigest mdigest = null;
        try {
            mdigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return byteToString(mdigest.digest(buffer.toString().getBytes()));
    }

    public static <T> String getSign(T data, String appKey, String... ignoredProps)
        throws RuntimeException {
        @SuppressWarnings("unchecked")
        Map<String, Object> dataMap =
            data instanceof Map ? (Map<String, Object>) data : toMap(data, true, ignoredProps);
        if ((null == dataMap) || (dataMap.isEmpty())) {
            return "";
        }
        dataMap = new TreeMap<>(dataMap);
        StringBuffer buff = new StringBuffer();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            Object obj = entry.getValue();
            if ((null != obj) && StringUtils.hasText(obj.toString())) {
                buff.append((String) entry.getKey()).append("=").append(entry.getValue())
                    .append("&");
            }
        }
        buff.append("key=").append(appKey);
        String newSign = getMD5Code(buff.toString());
        return newSign;
    }

    public static <T> void checkSign(T data, String appKey, String reqSign, String... ignoredProps)
        throws RuntimeException {
        String newSign = getSign(data, appKey, ignoredProps);
        if (!newSign.equals(reqSign)) {
            throw new RuntimeException("签名异常");
        }
    }

    /**
     * 字符串加密
     *
     * @param str 明文
     * @return md5
     */
    public static String strMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            byte[] hex = md.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte x : hex) {
                buffer.append(String.format("%02X", x));
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Object> toMap(Object obj, boolean containNull,
        String... ignoredProps) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> propMap = new HashMap<>();
        PropertyDescriptor[] properties = BeanUtils.getPropertyDescriptors(obj.getClass());
        List<String> props =
            Objects.isNull(ignoredProps) ? Collections.emptyList() : Arrays.asList(ignoredProps);
        for (PropertyDescriptor property : properties) {
            Method readMethod = property.getReadMethod();
            String key = property.getName();
            if ((readMethod != null) && ((!props.contains(key))) && (!key.equals("class"))) {
                try {
                    Object value = readMethod.invoke(obj, new Object[0]);
                    if ((!containNull) || (null != value)) {
                        propMap.put(key, value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(
                        "Read property value error, propName=" + property.getName(), e);
                }
            }
        }
        return propMap;
    }


}
