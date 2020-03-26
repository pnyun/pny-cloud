package com.pny.exception;

import com.pny.util.YunHttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;


/**
 * <u>Copied</u> from google guava's Preconditions class to break dependency on
 * this library.
 *
 *
 */
public final class Checks {

    public static String checkName(String name) {
        checkYun(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(name.trim()),
                "config.name.blank");
        return name.trim();
    }

    /**
     * Checks that an expression should be {@code true}, otherwise an
     * {@code checkYunException} will be thrown.
     * 
     * @param expression
     *        a boolean expression expected to be {@code true}
     * @param message
     *        a dot-separated message identifier.
     * @param args
     *        arbitrary arguments to be inserted into the message.
     *         if the expression is {@code false}.
     */
    public static void checkYun(boolean expression, String message,
            Object... args) {
        if (!expression) {
            throw new PnyYunException("500", message, args);
        }
    }

    /**
     * Checks that an expression should be {@code true}, otherwise an
     * {@code checkYunException} will be thrown.
     * 
     * @param expression
     *        a boolean expression expected to be {@code true}
     * @param message
     *        a dot-separated message identifier.
     * @param args
     *        arbitrary arguments to be inserted into the message.
     * @throws PnyYunException
     *         if the expression is {@code false}.
     */
    public static void checkYun(boolean expression, String code,
            String message, Object... args) {
        if (!expression) {
            throw new PnyYunException(code, message, args);
        }
    }

    /**
     * Checks that an expression should be {@code true}, otherwise an
     * {@code checkYunException} will be thrown.
     * 
     * @param expression
     *        a boolean expression expected to be {@code true}
     *        a dot-separated message identifier.
     *        where occur the expression
     * @throws PnyYunException
     *         if the expression is {@code false}.
     */
    public static void checkYun(boolean expression, String code, String msg) {
        if (!expression) {
            throw new PnyYunException(code, msg);
        }
    }

    /**
     * Checks that an object should not be {@code null}, otherwise an
     * {@code checkYunException} will be thrown.
     * 
     * @param object
     *        an object not expected to be {@code null}
     * @param message
     *        a dot-separated message identifier.
     * @param args
     *        arbitrary arguments to be inserted into the message.
     * @throws PnyYunException
     *         if the object is {@code null}.
     */
    public static Object checkYunNonNull(Object object, String message,
            Object... args) {
        if (object == null) {
            throw new PnyYunException(message, new NullPointerException(),
                    args);
        }

        return object;
    }

    /**
     * 
     * checkException:返回错误. <br/>
     * 用于catch到异常后，直接报错<br/>
     * info 为上报的错误信息
     * @param info
     * @since JDK 1.8
     */
    public static void checkException(String info) {
        throw new PnyYunException(ErrorCodes.UNKNOWN_ERROR,
                format(ErrorCodes.UNKNOWN_ERROR_MSG + " -> %s", info),
                info);
    }

    /**
     * 
     * checkArgument:校验. <br/>
     *
     * @param info
     * @param expression
     * @since JDK 1.8
     */
    public static void checkArgument(String info, boolean expression) {
        if (!expression) {
            throw new PnyYunException(ErrorCodes.ARGUMENT_FALSE,
                    format(ErrorCodes.ARGUMENT_FALSE_MSG + " -> %s", info),
                    info);
        }
    }

    /**
     * 
     * checkObjectNotNull <br/>
     * 资源不存在异常<br/>
     * 检查对象是否为空<br/>
     *
     * @param o
     * @since JDK 1.8
     */
    public static void checkObjectNotNull(String info, Object o) {
        if (o == null) {
            throw new PnyYunException(ErrorCodes.NO_CONTENT, format(ErrorCodes.NO_CONTENT_MSG + " -> %s", info),
                    info, o);
        }
    }

    /**
     * 
     * checkParamNotNull <br/>
     * 缺少必填参数<br/>
     *
     * @param paramNames
     *        需要检查的参数名称，逗号分隔 例如：a,b,c
     * @param object
     *        需要检查的参数
     * @since JDK 1.8
     */
    public static void checkParamNotNull(String paramNames, Object... object) {
        if (object != null) {
            List<Object> l = new ArrayList<>();
            List<Object> strO = new ArrayList<>();
            String[] str = paramNames.split(",");
            for (int i = 0; i < object.length; i++) {
                if (object[i] == null) {
                    l.add(object[i]);
                    strO.add(str[i]);
                }
            }
            if (l.size() > 0) {
                throw new PnyYunException(ErrorCodes.MISSING_PARAM_ERROR,
                        format(ErrorCodes.MISSING_PARAM_ERROR_MSG + " -> %s", strO),
                        ErrorCodes.MISSING_PARAM_ERROR_MSG, l);
            }
        } else {
            return;
        }

    }

    /**
     * 
     * checkListNotNull:. <br/>
     * TODO 检查list为空<br/>
     *
     * @param l
     * @since JDK 1.8
     */
    public static void checkListNotNull(String listName, List<?> l) {
        if (l == null || l.size() < 1) {
            throw new PnyYunException(ErrorCodes.NO_CONTENT, format(ErrorCodes.NO_CONTENT_MSG + " -> %s", listName),
                    ErrorCodes.NO_CONTENT_MSG);
        }
    }

    /**
     * 
     * checkListOne:. <br/>
     * TODO 某些查询只能有一条，存在多条的情况下可能会有垃圾数据.<br/>
     * TODO 适用于查询db，selectList 检查返回结果只有1个.<br/>
     *
     * @param l
     * @since JDK 1.8
     */
    public static void checkListOne(List<?> l) {
        if (l == null || l.size() != 1) {
            throw new PnyYunException(ErrorCodes.DATA_MULTI, ErrorCodes.DATA_MULTI_MSG, ErrorCodes.DATA_MULTI_MSG);
        }
    }

    /**
     * 
     * checkDB:检查数据库操作是否正常. <br/>
     * TODO 用于 update insert delete 等共有方法.<br/>
     *
     * @param expression
     * @since JDK 1.8
     */
    public static void checkDB(boolean expression) {
        if (!expression) {
            throw new PnyYunException(ErrorCodes.DB_ERROR, ErrorCodes.DB_ERROR_MSG, ErrorCodes.DB_ERROR_MSG);
        }
    }

    /*
     * below google's code.
     */

    private Checks() {
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to
     * the calling method.
     *
     * @param expression
     *        a boolean expression
     * @param errorMessage
     *        the exception message to use if the check fails; will be converted
     *        to a string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException
     *         if {@code expression} is false
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to
     * the calling method.
     *
     * @param expression
     *        a boolean expression
     * @param errorMessageTemplate
     *        a template for the exception message should the check fail. The
     *        message is formed by replacing each {@code %s} placeholder in the
     *        template with an argument. These are matched by position - the
     *        first {@code %s} gets {@code errorMessageArgs[0]}, etc. Unmatched
     *        arguments will be appended to the formatted message in square
     *        braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *        the arguments to be substituted into the message template.
     *        Arguments are converted to strings using
     *        {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException
     *         if {@code expression} is false
     * @throws NullPointerException
     *         if the check fails and either {@code errorMessageTemplate} or
     *         {@code errorMessageArgs} is null (don't let this happen)
     */
    public static void checkArgument(boolean expression,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression
     *        a boolean expression
     * @throws IllegalStateException
     *         if {@code expression} is false
     */
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression
     *        a boolean expression
     * @param errorMessage
     *        the exception message to use if the check fails; will be converted
     *        to a string using {@link String#valueOf(Object)}
     * @throws IllegalStateException
     *         if {@code expression} is false
     */
    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression
     *        a boolean expression
     * @param errorMessageTemplate
     *        a template for the exception message should the check fail. The
     *        message is formed by replacing each {@code %s} placeholder in the
     *        template with an argument. These are matched by position - the
     *        first {@code %s} gets {@code errorMessageArgs[0]}, etc. Unmatched
     *        arguments will be appended to the formatted message in square
     *        braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *        the arguments to be substituted into the message template.
     *        Arguments are converted to strings using
     *        {@link String#valueOf(Object)}.
     * @throws IllegalStateException
     *         if {@code expression} is false
     * @throws NullPointerException
     *         if the check fails and either {@code errorMessageTemplate} or
     *         {@code errorMessageArgs} is null (don't let this happen)
     */
    public static void checkState(boolean expression,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference
     *        an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException
     *         if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference
     *        an object reference
     * @param errorMessage
     *        the exception message to use if the check fails; will be converted
     *        to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException
     *         if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference
     *        an object reference
     * @param errorMessageTemplate
     *        a template for the exception message should the check fail. The
     *        message is formed by replacing each {@code %s} placeholder in the
     *        template with an argument. These are matched by position - the
     *        first {@code %s} gets {@code errorMessageArgs[0]}, etc. Unmatched
     *        arguments will be appended to the formatted message in square
     *        braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *        the arguments to be substituted into the message template.
     *        Arguments are converted to strings using
     *        {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException
     *         if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, String errorMessageTemplate,
            Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens
            // anyway
            throw new NullPointerException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }


    public static void checkArgument(boolean expression, String code, String msgtemplate, Object... arguments) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }


    /*
     * All recent hotspots (as of 2009) *really* like to have the natural code
     * 
     * if (guardExpression) { throw new BadException(messageExpression); }
     * 
     * refactored so that messageExpression is moved to a separate
     * String-returning method.
     * 
     * if (guardExpression) { throw new BadException(badMsg(...)); }
     * 
     * The alternative natural refactorings into void or Exception-returning
     * methods are much slower. This is a big deal - we're talking factors of
     * 2-8 in microbenchmarks, not just 10-20%. (This is a hotspot optimizer
     * bug, which should be fixed, but that's a separate, big project).
     * 
     * The coding pattern above is heavily used in java.util, e.g. in ArrayList.
     * There is a RangeCheckMicroBenchmark in the JDK that was used to test
     * this.
     * 
     * But the methods in this class want to throw different exceptions,
     * depending on the args, so it appears that this pattern is not directly
     * applicable. But we can use the ridiculous, devious trick of throwing an
     * exception in the middle of the construction of another exception. Hotspot
     * is fine with that.
     */

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from
     * zero, inclusive, to {@code size}, exclusive.
     *
     * @param index
     *        a user-supplied index identifying an element of an array, list or
     *        string
     * @param size
     *        the size of that array, list or string
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException
     *         if {@code index} is negative or is not less than {@code size}
     * @throws IllegalArgumentException
     *         if {@code size} is negative
     */
    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from
     * zero, inclusive, to {@code size}, exclusive.
     *
     * @param index
     *        a user-supplied index identifying an element of an array, list or
     *        string
     * @param size
     *        the size of that array, list or string
     * @param desc
     *        the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException
     *         if {@code index} is negative or is not less than {@code size}
     * @throws IllegalArgumentException
     *         if {@code size} is negative
     */
    public static int checkElementIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment
        // above)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    badElementIndex(index, size, desc));
        }
        return index;
    }

    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index,
                    size);
        }
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     *
     * @param index
     *        a user-supplied index identifying a position in an array, list or
     *        string
     * @param size
     *        the size of that array, list or string
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException
     *         if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException
     *         if {@code size} is negative
     */
    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     *
     * @param index
     *        a user-supplied index identifying a position in an array, list or
     *        string
     * @param size
     *        the size of that array, list or string
     * @param desc
     *        the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException
     *         if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException
     *         if {@code size} is negative
     */
    public static int checkPositionIndex(int index, int size, String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment
        // above)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    badPositionIndex(index, size, desc));
        }
        return index;
    }

    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)", desc,
                    index, size);
        }
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid
     * <i>positions</i> in an array, list or string of size {@code size}, and
     * are in order. A position index may range from zero to {@code size},
     * inclusive.
     *
     * @param start
     *        a user-supplied index identifying a starting position in an array,
     *        list or string
     * @param end
     *        a user-supplied index identifying a ending position in an array,
     *        list or string
     * @param size
     *        the size of that array, list or string
     * @throws IndexOutOfBoundsException
     *         if either index is negative or is greater than {@code size}, or
     *         if {@code end} is less than {@code start}
     * @throws IllegalArgumentException
     *         if {@code size} is negative
     */
    public static void checkPositionIndexes(int start, int end, int size) {
        // Carefully optimized for execution by hotspot (explanatory comment
        // above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(
                    badPositionIndexes(start, end, size));
        }
    }

    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)",
                end, start);
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position: the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments
     * will be appended to the end of the formatted message in square braces.
     *
     * @param template
     *        a non-null string containing 0 or more {@code %s} placeholders.
     * @param args
     *        the arguments to be substituted into the message template.
     *        Arguments are converted to strings using
     *        {@link String#valueOf(Object)}. Arguments can be null.
     */
    static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(
                template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    public static void check(YunHttpResponse<?> e) {
        if (!"200".equals(e.getStatus())) {
            throw new PnyYunException(e.getStatus(), e.getMessage(), e.getToast());
        }
    }

    public static void checkLogin(YunHttpResponse<?> e) {
        if (!"200".equals(e.getStatus())) {
            throw new PnyYunException(ErrorCodes.AUTHENTICATION, ErrorCodes.AUTHENTICATION_MSG,
                    e.getToast());
        }
    }

    public static void loginExpired(boolean expression) {
        if (!expression) {
            throw new PnyYunException(ErrorCodes.AUTHENTICATION, ErrorCodes.AUTHENTICATION_MSG,
                    "请重新登录！");
        }
    }

    public static void checkForbidden(boolean expression) {
        if (!expression) {
            throw new PnyYunException(ErrorCodes.FORBIDDEN, ErrorCodes.FORBIDDEN_MSG,
                    ErrorCodes.FORBIDDEN_MSG);
        }
    }

    /*public static void main(String[] args) {
        String a = null;
        String b = null;
        String c = "c";
        String name = null;
        String nickName = null;
        checkParamNotNull("a,b,c,name,nickName", a, b, c, name, nickName);
        // checkObjectNotNull("name", name);
    }*/

}
