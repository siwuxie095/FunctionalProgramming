package com.siwuxie095.functional.chapter5th.example13th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:39:32
 */
@SuppressWarnings("all")
public class FibonacciTest {

    @Test
    public void fibonacciMatchesOpeningSequence() {
        List<Long> fibonacciSequence = Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L);

        IntStream.range(0, fibonacciSequence.size())
                .forEach(x -> {
                    Fibonacci fibonacci = new Fibonacci();
                    long result = fibonacci.fibonacci(x);
                    long expectedResult = fibonacciSequence.get(x);
                    Assert.assertEquals(expectedResult, result);
                });
    }

}
