package com.siwuxie095.functional.chapter3rd.example9th;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:43:01
 */
@SuppressWarnings("all")
public class MapUsingReduce {

    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (acc, ele) -> {
            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(ele));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

}
