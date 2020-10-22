package com.siwuxie095.functional.chapter5th.example13th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:00:18
 */
@SuppressWarnings("all")
public class CountUsingReduceTest {

    @Test
    public void emptyList() {
        assertCount(Collections.<Object>emptyList(), Collections.<Object>emptyList());
    }

    @Test
    public void notEmptyList() {
        assertCount(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3));
    }

    private <I> void assertCount(List<I> input, List<I> expectedOutput) {
        int count = CountUsingReduce.count(input.stream());
        Assert.assertEquals(expectedOutput.size(), count);

        int parallelCount = CountUsingReduce.count(input.parallelStream());
        Assert.assertEquals(expectedOutput.size(), parallelCount);
    }

}
