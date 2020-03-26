package com.pny.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * encryption tools
 */
@SuppressWarnings("restriction")
public class CryptUtil {

    public static void main(String[] args) {

    }

    /**
     * 字符串base64加密
     * 
     * @param str
     *        字符串
     * @return 加密后字符串
     */
    public static String encryptBase64(String str) {

        String result = null;
        byte[] b = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            result = new BASE64Encoder().encode(b);
            result = filter(result);
        }
        return result;

    }

    /**
     * 字符串base64解密
     * 
     * @param str
     *        字符串
     * @return 解密后字符串
     */
    public static String decryptBase64(String str) {

        String result = null;
        byte[] b = null;
        if (str != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    /**
     * 字符串md5加密
     * 
     * @param str
     *        字符串
     * @return 加密后字符串
     */
    public static String encryptMd5(String str) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char c[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                c[k++] = hexDigits[byte0 >>> 4 & 0xf];
                c[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 去掉字符串的换行符号 base64编码时，得到的字符串有换行符号，根据需要可以去掉
     * 
     * @param str
     * @return
     */
    private static String filter(String str) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int asc = str.charAt(i);
            if (asc != 10 && asc != 13) {
                sb.append(str.subSequence(i, i + 1));
            }
        }
        return new String(sb);

    }

    /**
     * 对字符串进行MD5签名
     * 
     * @param text
     *        明文
     * 
     * @return 密文
     */
    public static String md5(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

}
