package com.siwuxie095.functional.chapter8th.example7th;


/**
 * @author Jiajing Li
 * @date 2020-10-25 20:45:09
 */
@SuppressWarnings("all")
public final class Lets {

    public static void describe(String name, Suite behavior) {
        Description description = new Description(name);
        behavior.specifySuite(description);
    }

}
