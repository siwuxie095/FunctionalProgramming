package com.siwuxie095.functional.chapter3rd.example9th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:44:53
 */
@SuppressWarnings("all")
public class MapUsingReduceTest {


    @Test
    public void emptyList() {
        assertMapped(Function.<Object>identity(), Collections.<Object>emptyList(), Collections.<Object>emptyList());
    }

    @Test
    public void identityMapsToItself() {
        assertMapped((Integer x) -> x, Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3));
    }

    @Test
    public void incrementingNumbers() {
        assertMapped((Integer x) -> x + 2, Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5));
    }

    private <I, O> void assertMapped(Function<I, O> mapper, List<I> input, List<O> expectedOutput) {
        List<O> output = MapUsingReduce.map(input.stream(), mapper);
        Assert.assertEquals(expectedOutput, output);

        List<O> parallelOutput = MapUsingReduce.map(input.parallelStream(), mapper);
        Assert.assertEquals(expectedOutput, parallelOutput);
    }



}
