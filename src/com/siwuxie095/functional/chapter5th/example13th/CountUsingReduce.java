package com.siwuxie095.functional.chapter5th.example13th;

import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-22 21:53:24
 */
@SuppressWarnings("all")
public class CountUsingReduce {

    public static <I> int count(Stream<I> stream) {
        return stream.reduce(0, (acc, ele) -> {
            ++acc;
            return acc;
        }, (left, right) -> left + right);
    }

}
