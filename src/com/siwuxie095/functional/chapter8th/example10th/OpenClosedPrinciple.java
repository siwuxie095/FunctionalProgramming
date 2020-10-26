package com.siwuxie095.functional.chapter8th.example10th;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jiajing Li
 * @date 2020-10-26 21:56:25
 */
@SuppressWarnings("all")
public class OpenClosedPrinciple {

    public void asHigherOrderFunctions() {
        // One implementation
        ThreadLocal<DateFormat> localFormatter
                = ThreadLocal.withInitial(() -> new SimpleDateFormat());

        // Usage
        DateFormat formatter = localFormatter.get();

        // Or...
        AtomicInteger threadId = new AtomicInteger();
        ThreadLocal<Integer> localId
                = ThreadLocal.withInitial(() -> threadId.getAndIncrement());

        // Usage
        int idForThisThread = localId.get();
    }



}
