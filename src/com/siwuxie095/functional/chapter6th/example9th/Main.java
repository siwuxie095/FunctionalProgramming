package com.siwuxie095.functional.chapter6th.example9th;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Jiajing Li
 * @date 2020-10-24 23:40:17
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 要点回顾与巩固练习
     *
     * 1、要点回顾
     *
     * （1）数据并行化是把工作拆分，同时在多核 CPU 上执行的方式。
     * （2）如果使用流编写代码，可通过调用 parallel 或者 parallelStream 方法实现数据并行化操作。
     * （3）影响性能的五要素是：数据大小、源数据结构、值是否装箱、可用的 CPU 核数量，以及处理每个元素所花的时间。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）
     * 问：
     * 如下代码中是顺序求流中元素的平方和，将其改为并行处理。
     *
     *     private static int sequentialSumOfSquares(IntStream range) {
     *         return range.map(x -> x * x)
     *                 .sum();
     *     }
     *
     * 答：
     *     private static int sumOfSquares(IntStream range) {
     *         return range.parallel()
     *                 .map(x -> x * x)
     *                 .sum();
     *     }
     *
     *
     * （2）
     * 问：
     * 如下代码是把列表中的数字相乘，然后再将所得结果乘以 5。顺序执行这段程序没有问题，但并行执行时有一个缺陷，
     * 使用流并行化执行该段代码，并修复缺陷。
     *
     *     private static int multiplyThrough(List<Integer> linkedListOfNumbers) {
     *         return linkedListOfNumbers.stream()
     *             .reduce(5, (acc, x) -> x * acc);
     *     }
     *
     * 答：
     *     private static int fixedMultiplyThrough(List<Integer> numbers) {
     *         return 5 * numbers.parallelStream()
     *                 .reduce(1, (acc, x) -> x * acc);
     *     }
     *
     *
     * （3）
     * 问：
     * 如下代码是计算列表中数字的平方和。尝试改进代码性能，但不得牺牲代码质量。只需要一些简单的改动即可。
     *
     *     private static int slowSumOfSquares(List<Integer> linkedListOfNumbers) {
     *         return linkedListOfNumbers.parallelStream()
     *                 .map(x -> x * x)
     *                 .reduce(0, (acc, x) -> acc + x);
     *     }
     *
     * 答：
     *
     *     private static int fastSumOfSquares(List<Integer> arrayListOfNumbers) {
     *         return arrayListOfNumbers.parallelStream()
     *                 .mapToInt(x -> x * x)
     *                 .sum();
     *     }
     *
     * 或
     *
     *     private static int serialFastSumOfSquares(List<Integer> arrayListOfNumbers) {
     *         return arrayListOfNumbers.stream()
     *                 .mapToInt(x -> x * x)
     *                 .sum();
     *     }
     */
    public static void main(String[] args) {

    }

    /**
     * 顺序求列表中数字的平方和
     */
    private static int sequentialSumOfSquares(IntStream range) {
        return range.map(x -> x * x)
                .sum();
    }

    /**
     * 并行求列表中数字的平方和
     */
    private static int sumOfSquares(IntStream range) {
        return range.parallel()
                .map(x -> x * x)
                .sum();
    }

    /**
     * 把列表中的数字相乘，然后再将所得结果乘以 5，该实现有一个缺陷
     */
    private static int multiplyThrough(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.stream()
            .reduce(5, (acc, x) -> x * acc);
    }

    /**
     * 修复缺陷
     */
    private static int fixedMultiplyThrough(List<Integer> numbers) {
        return 5 * numbers.parallelStream()
                .reduce(1, (acc, x) -> x * acc);
    }

    @Test
    public void testFixedMultiplyThrough() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int result = fixedMultiplyThrough(numbers);
        Assert.assertEquals(30, result);
    }

    /**
     * 求列表元素的平方和，该实现方式性能不高
     */
    private static int slowSumOfSquares(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.parallelStream()
                .map(x -> x * x)
                .reduce(0, (acc, x) -> acc + x);
    }

    private static int serialSlowSumOfSquares(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.stream()
                .map(x -> x * x)
                .reduce(0, (acc, x) -> acc + x);
    }

    private static int intermediateSumOfSquares(List<Integer> arrayListOfNumbers) {
        return arrayListOfNumbers.parallelStream()
                .map(x -> x * x)
                .reduce(0, (acc, x) -> acc + x);
    }

    private static int serialIntermediateSumOfSquares(List<Integer> arrayListOfNumbers) {
        return arrayListOfNumbers.stream()
                .map(x -> x * x)
                .reduce(0, (acc, x) -> acc + x);
    }

    private static int fastSumOfSquares(List<Integer> arrayListOfNumbers) {
        return arrayListOfNumbers.parallelStream()
                .mapToInt(x -> x * x)
                .sum();
    }

    private static int serialFastSumOfSquares(List<Integer> arrayListOfNumbers) {
        return arrayListOfNumbers.stream()
                .mapToInt(x -> x * x)
                .sum();
    }

}
