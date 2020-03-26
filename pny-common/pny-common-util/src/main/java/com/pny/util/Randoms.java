package com.pny.util;

import static java.lang.String.valueOf;

import com.pny.exception.Checks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomization utilities.
 */
public final class Randoms {

    private Randoms() {
    }

    /**
     * Return a random number with the specified number of digits.
     * 
     * @param digit
     *        number of the digits, should be within [4, 6]
     * @return a random number in string.
     */
    public static String getRandomNumber(int digit) {
        Checks.checkArgument("无效的验证码长度，验证码只允许4-6位！", digit >= 4 && digit <= 6);
        int number = ThreadLocalRandom.current().nextInt(1000000);
        String str = "000000" + valueOf(number);
        return str.substring(str.length() - digit);
    }

    /**
     * Return a random number for verifyCode.
     * 
     * @return
     */
    public static String getMarketCode() {
        LocalDateTime d = LocalDateTime.now();
        String day = d.format(
                DateTimeFormatter.ofPattern("dd"));
        String timeStr = String.valueOf(System.currentTimeMillis());
        timeStr = timeStr.substring(timeStr.length() - 4, timeStr.length());
        int num = 0;
        while (num < 100000) {
            num = (int) (Math.random() * 1000000);
        }
        String numStr = String.valueOf(num);
        StringBuffer buffer = new StringBuffer();
        buffer.append(day).append(timeStr).append(numStr);
        return buffer.toString();
    }

    /**
     * Return UUID.
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Return a random number for requesId.
     * 
     * @return
     */
    public static synchronized String getRequestId() {
        LocalDateTime d = LocalDateTime.now();
        String day = d.format(
                DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timeStr = String.valueOf(System.currentTimeMillis());
        timeStr = timeStr.substring(timeStr.length() - 4, timeStr.length());
        int num = 0;
        while (num < 100000) {
            num = (int) (Math.random() * 1000000);
        }
        String numStr = String.valueOf(num);
        StringBuffer buffer = new StringBuffer();
        buffer.append(day).append(timeStr).append(numStr);
        return buffer.toString();
    }
}
