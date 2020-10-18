package com.siwuxie095.functional.chapter3rd.example9th;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:43:43
 */
@SuppressWarnings("all")
public class FilterUsingReduce {

    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial, (List<I> acc, I ele) -> {
            if (predicate.test(ele)) {
                List<I> newAcc = new ArrayList<>(acc);
                newAcc.add(ele);
                return newAcc;
            } else {
                return acc;
            }
        }, (left, right) -> combineLists(left, right));
    }

    private static <I> List<I> combineLists(List<I> left, List<I> right) {
        List<I> newLeft = new ArrayList<>(left);
        newLeft.addAll(right);
        return newLeft;
    }

}
