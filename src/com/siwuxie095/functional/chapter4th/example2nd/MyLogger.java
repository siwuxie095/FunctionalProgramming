package com.siwuxie095.functional.chapter4th.example2nd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Jiajing Li
 * @date 2020-10-16 08:20:56
 */
@SuppressWarnings("all")
public class MyLogger {

    private static final Logger logger = LoggerFactory.getLogger("MyLogger");

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(Supplier<String> supplier) {
        if (isDebugEnabled()) {
            debug(supplier.get());
        }
    }

}
