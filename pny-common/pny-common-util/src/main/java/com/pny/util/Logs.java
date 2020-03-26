package com.pny.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging utilities.
 *
 */
public final class Logs {

    private Logs() {
    }

    public static Logger getLogger() {
        Throwable t = new Throwable();

        /* get the calling class name */
        StackTraceElement methodCaller = t.getStackTrace()[1];
        Logger logger = LoggerFactory.getLogger(methodCaller.getClassName());

        return logger;
    }

}
