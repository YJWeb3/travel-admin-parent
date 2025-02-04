package com.zheng.travel.admin.commons.utils.fn.asserts;


import com.zheng.travel.admin.commons.utils.fn.inter.*;

import java.util.Collection;

public class Vsserts {

    /**
     * 如果参数为true抛出异常
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isTure(boolean b, X exceptionSupplier) throws X {
        if (b) {
            throw exceptionSupplier;
        }
    }


    /**
     * 如果参数为true抛出异常
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isTure(boolean b, VSupplier<? extends X> exceptionSupplier) throws X {
        if (b) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 通过b的条件来决定
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isTure(VSupplier<? extends Boolean> b, VSupplier<? extends X> exceptionSupplier) throws X {
        if (b.get()) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果参数为false抛出异常
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isFalse(boolean b, X exceptionSupplier) throws X {
        if (!b) {
            throw exceptionSupplier;
        }
    }

    /**
     * 如果参数为false抛出异常
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isFalse(boolean b, VSupplier<? extends X> exceptionSupplier) throws X {
        if (!b) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 通过b的条件来决定
     *
     * @param b
     * @return
     **/
    public static <X extends Throwable> void isFalse(VSupplier<? extends Boolean> b, VSupplier<? extends X> exceptionSupplier) throws X {
        if (!b.get()) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果字符串为空抛出异常
     *
     * @param value
     * @return
     **/
    public static <X extends Throwable> void isEmptyEx(String value, X exceptionSupplier) throws X {
        if (null == value || "" .equals(value)) {
            throw exceptionSupplier;
        }
    }

    /**
     * 如果字符串为空抛出异常
     *
     * @param value
     * @return
     **/
    public static <X extends Throwable> void isEmptyEx(String value, VSupplier<? extends X> exceptionSupplier) throws X {
        if (null == value || "" .equals(value)) {
            throw exceptionSupplier.get();
        }
    }


    /**
     * 如果字符串给与默认值
     *
     * @param value
     * @return
     **/
    public static <X extends String> String isEmptyDefault(String value, VSupplier<? extends X> defaultValueSupplier) {
        if (null == value || "" .equals(value)) {
            return defaultValueSupplier.get();
        }
        return value;
    }


    /**
     * 如果字符串给与默认值
     *
     * @param value
     * @return
     **/
    public static <X extends String> String isEmptyDefault(String value, String defaultValue) {
        if (null == value || "" .equals(value)) {
            return defaultValue.trim();
        }
        return value.trim();
    }


    /**
     * 如果字符串是null或者""返回true
     *
     * @param value
     * @return
     **/
    public static boolean isEmpty(String value) {
        return null == value || "" .equals(value);
    }

    /**
     * 如果不是null或者""返回true
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }


    /**
     * 正则验证,如果false抛出异常
     *
     * @param v
     * @return
     **/
    public static <X extends Throwable> void isRegexEx(VSupplier<? extends Boolean> v, VSupplier<? extends X> exceptionSupplier) throws X {
        if (!v.get()) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 正则验证,如果false抛出异常
     *
     * @param v
     * @return
     **/
    public static <X extends Throwable> void isRegexEx(VSupplier<? extends Boolean> v, X exceptionSupplier) throws X {
        if (!v.get()) {
            throw exceptionSupplier;
        }
    }

    /**
     * 正则验证,如果false抛出异常
     *
     * @param flag
     * @return
     **/
    public static <X extends Throwable> void isRegexEx(boolean flag, VSupplier<? extends X> exceptionSupplier) throws X {
        if (!flag) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 正则验证,如果false抛出异常
     *
     * @param flag
     * @return
     **/
    public static <X extends Throwable> void isRegexEx(boolean flag, X exceptionSupplier) throws X {
        if (!flag) {
            throw exceptionSupplier;
        }
    }


    /**
     * 判断一个对象是否是null,是null抛出异常
     *
     * @param value
     * @return
     **/
    public static <X extends Throwable, T extends Object> void isNullEx(T value, VSupplier<? extends X> exceptionSupplier) throws X {
        if (null == value) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 判断一个对象是否是null,是null抛出异常
     *
     * @param value
     * @return
     **/
    public static <X extends Throwable, T extends Object> void isNullEx(T value, X exceptionSupplier) throws X {
        if (null == value) {
            throw exceptionSupplier;
        }
    }

    /**
     * 如果对象是null返回指定对象
     *
     * @param value
     * @return
     **/
    public static <T extends Object> T isNullDefault(T value, T defaultValue) {
        if (null == value) {
            return defaultValue;
        }

        return value;
    }

    /**
     * 如果对象是null返回指定对象
     *
     * @param value
     * @return
     **/
    public static <T extends Object> T isNullDefault(T value, VSupplier<T> defaultValueSupplier) {
        if (null == value) {
            value = defaultValueSupplier.get();
            return value;
        }
        return value;
    }


    /**
     * 如果对象是null返回true
     *
     * @return
     **/
    public static boolean isNull(Object value) {
        return null == value;
    }

    /**
     * 如果对象是null返回true
     *
     * @return
     **/
    public static boolean isNullArray(Object[] value) {
        return null == value || (null != value || value.length == 0);
    }

    /**
     * 如果对象不是null返回true
     *
     * @return
     **/
    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }


    /**
     * 如果集合为空抛出异常
     *
     * @param collection
     * @return
     **/
    public static <X extends Throwable> void isEmptyListEx(Collection<?> collection, VSupplier<? extends X> exceptionSupplier) throws X {
        if (collection == null || collection.isEmpty()) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果集合为空给予一个默认的
     *
     * @param collection
     * @return
     **/
    public static <T extends Collection<?>> T isEmptyCollectDefault(T collection, VSupplier<T> exceptionSupplier) {
        if (collection == null || collection.isEmpty()) {
            return exceptionSupplier.get();
        }
        return collection;
    }

    /**
     * 如果集合为空给予一个默认的
     *
     * @param collection
     * @return
     **/
    public static <T extends Collection<?>> T isEmptyCollectDf(T collection, T exceptionSupplier) {
        if (collection == null || collection.isEmpty()) {
            return exceptionSupplier;
        }
        return collection;
    }


    public static BooleanHandler isTrueOrFlase(boolean flag) {
        return (trueHandler, falseHandler) -> {
            if (flag) {
                trueHandler.handler();
            } else {
                falseHandler.handler();
            }
        };
    }

    public static BooleanHandler isTrueOrFlase(VSupplier<Boolean> flag) {
        return (trueHandler, falseHandler) -> {
            if (flag.get()) {
                trueHandler.handler();
            } else {
                falseHandler.handler();
            }
        };
    }


    public static <T> T isTrueOrFlaseCall(boolean flag, TrueCallbackHandler<T> trueBooleanCallbackHandler
            , FlaseCallbackHandler<T> flaseBooleanCallbackHandler) {
        if (flag) {
            return trueBooleanCallbackHandler.handler();
        } else {
            return flaseBooleanCallbackHandler.handler();
        }
    }

    public static <T> T isTrueOrFlaseCall(VSupplier<Boolean> flag, TrueCallbackHandler<T> trueBooleanCallbackHandler
            , FlaseCallbackHandler<T> flaseBooleanCallbackHandler) {
        if (flag.get()) {
            return trueBooleanCallbackHandler.handler();
        } else {
            return flaseBooleanCallbackHandler.handler();
        }
    }


    public static void isTrueOrFlaseVoid(boolean flag, TrueHandler trueHandler
            , FlaseHandler flaseHandler) {
        if (flag) {
            trueHandler.handler();
        } else {
            flaseHandler.handler();
        }
    }

    public static void isTrueOrFlaseVoid(VSupplier<Boolean> flag, TrueHandler trueHandler
            , FlaseHandler flaseHandler) {
        if (flag.get()) {
            trueHandler.handler();
        } else {
            flaseHandler.handler();
        }
    }

    @Deprecated
    public static <X extends Throwable> void tryCatch(TryHandler tryHandler, CatchHandler<X> catchHandler) throws X {
        try {
            tryHandler.handler();
        } catch (Exception ex) {
            throw catchHandler.handler();
        }
    }


    @Deprecated
    public static <X extends Throwable> void tryCatchex(TryHandler tryHandler, X ex) throws X {
        try {
            tryHandler.handler();
        } catch (Throwable exception) {
            throw ex;
        }
    }


}
