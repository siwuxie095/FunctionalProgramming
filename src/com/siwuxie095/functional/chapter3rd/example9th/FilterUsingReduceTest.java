package com.siwuxie095.functional.chapter3rd.example9th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:50:03
 */
@SuppressWarnings("all")
public class FilterUsingReduceTest {

    @Test
    public void emptyList() {
        assertFiltered(x -> false, Collections.<Object>emptyList(), Collections.<Object>emptyList());
    }

    @Test
    public void trueReturnsEverything() {
        assertFiltered((Integer x) -> true, Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3));
    }

    @Test
    public void falseRemovesEverything() {
        assertFiltered((Integer x) -> false, Arrays.asList(1, 2, 3), Arrays.asList());
    }

    @Test
    public void filterPartOfList() {
        assertFiltered((Integer x) -> x > 2, Arrays.asList(1, 2, 3), Arrays.asList(3));
    }

    private <I> void assertFiltered(Predicate<I> predicate, List<I> input, List<I> expectedOutput) {
        List<I> output = FilterUsingReduce.filter(input.stream(), predicate);
        Assert.assertEquals(expectedOutput, output);

        List<I> parallelOutput = FilterUsingReduce.filter(input.parallelStream(), predicate);
        Assert.assertEquals(expectedOutput, parallelOutput);
    }

}
