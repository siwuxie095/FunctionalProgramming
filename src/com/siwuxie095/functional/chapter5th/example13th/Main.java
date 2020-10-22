package com.siwuxie095.functional.chapter5th.example13th;

/**
 * @author Jiajing Li
 * @date 2020-10-22 08:33:18
 */
public class Main {

    /**
     * 要点回顾与巩固练习
     *
     * 1、要点回顾
     *
     * （1）方法引用是一种引用方法的轻量级语法，形如：ClassName::methodName。
     * （2）收集器可用来计算流的最终值，是 reduce 方法的模拟。
     * （3）Java 8 提供了收集多种容器类型的方式，同时允许程序员自定义收集器。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）方法引用。
     * 问：
     * 使用 reduce 实现 count 方法。
     * 答：
     *     public static <I> int count(Stream<I> stream) {
     *         return stream.reduce(0, (acc, ele) -> {
     *             ++acc;
     *             return acc;
     *         }, (left, right) -> left + right);
     *     }
     *
     *
     * （2）收集器。
     *
     * 问：
     * 找出名字最长的艺术家。分别使用收集器和 reduce 高阶函数实现。
     * 答：
     * 通过收集器实现：
     *
     *     public static Artist byCollecting(List<Artist> artists) {
     *         return artists.stream()
     *                 .collect(Collectors.maxBy(byNameLength))
     *                 .orElseThrow(RuntimeException::new);
     *     }
     *
     * 通过 reduce 实现：
     *
     *     private static Comparator<Artist> byNameLength
     *             = Comparator.comparing(artist -> artist.getName().length());
     *
     *     public static Artist byReduce(List<Artist> artists) {
     *         return artists.stream()
     *                 .reduce((acc, artist) -> {
     *                     return (byNameLength.compare(acc, artist) >= 0) ? acc : artist;
     *                 })
     *                 .orElseThrow(RuntimeException::new);
     *     }
     *
     * 问：
     * 假设一个元素为单词的流，计算每个单词出现的次数。假设输入如下：
     * Stream<String> names = Stream.of("John", "Paul", "George", "John", "Paul", "John");
     * 答：
     *     public static Map<String, Long> countWords(Stream<String> names) {
     *         return names.collect(Collectors.groupingBy(name -> name, Collectors.counting()));
     *     }
     *
     * 问：
     * 用一个定制的收集器实现 Collectors.groupingBy 方法，不需要提供一个下游收集器，只需实现一个最简单的即可。
     * 别看 JDK 的源码，这是作弊！提示：可从下面这行代码开始。
     * public class GroupingBy<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>>
     * 答：
     * 具体见 GroupingBy 类。
     *
     *
     * （3）改进Map。
     * 问：
     * 使用 Map 的 computeIfAbsent 方法高效计算斐波那契数列。这里的 "高效" 是指避免将那些较小的序列重复计算多次。
     * 答：
     * 具体见 Fibonacci 类。
     */
    public static void main(String[] args) {

    }

}
