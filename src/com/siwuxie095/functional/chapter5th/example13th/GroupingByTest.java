package com.siwuxie095.functional.chapter5th.example13th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:33:51
 */
@SuppressWarnings("all")
public class GroupingByTest {

    @Test
    public void stringsByLength() {
        GroupingBy<String, Integer> stringIntegerGroupingBy = new GroupingBy<>(String::length);
        Map<Integer,List<String>> results = Stream.of("a", "b", "cc", "dd")
                .collect(stringIntegerGroupingBy);

        System.out.println(results);

        Assert.assertEquals(2, results.size());
        Assert.assertEquals(Arrays.asList("a", "b"), results.get(1));
        Assert.assertEquals(Arrays.asList("cc", "dd"), results.get(2));
    }

}
