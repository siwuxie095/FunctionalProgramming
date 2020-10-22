package com.siwuxie095.functional.chapter5th.example13th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:18:48
 */
@SuppressWarnings("all")
public class WordCountTest {

    @Test
    public void test() {
        Stream<String> names = Stream.of("John", "Paul", "George", "John", "Paul", "John");
        Map<String, Long> counts = WordCount.countWords(names);

        Assert.assertEquals(3, counts.size());
        Assert.assertEquals(Long.valueOf(3), counts.get("John"));
        Assert.assertEquals(Long.valueOf(2), counts.get("Paul"));
        Assert.assertEquals(Long.valueOf(1), counts.get("George"));
    }

}
