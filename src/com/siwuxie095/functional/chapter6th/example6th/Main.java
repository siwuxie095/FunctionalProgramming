package com.siwuxie095.functional.chapter6th.example6th;

/**
 * @author Jiajing Li
 * @date 2020-10-24 15:15:28
 */
public class Main {

    /**
     * 限制
     *
     * 对于流来说，虽然只需一点改动，就能让已有代码并行化运行，但前提是代码写得符合约定。为了发挥并行流框架的优势，
     * 写代码时必须遵守一些规则和限制。
     *
     * 以 reduce 方法为例，初始值可以为任意值，为了让其在并行化时能工作正常，初值必须为组合函数的恒等值。拿恒等
     * 值和其他值做 reduce 操作时，其他值保持不变。
     *
     * 比如，使用 reduce 操作求和，组合函数为 (acc, element) -> acc + element，则其初值必须为 0，因为任何
     * 数字加 0，值不变。
     *
     * reduce 操作的另一个限制是组合操作必须符合结合律。这意味着只要序列的值不变，组合操作的顺序不重要。
     *
     * 以加法和乘法为例，如下：
     *
     * (4 + 2) + 1 = 4 + (2 + 1) = 7
     * (4 * 2) * 1 = 4 * (2 * 1) = 8
     *
     * 可以改变加法和乘法的顺序，但结果是一样的。
     *
     * 要避免的是持有锁。流框架会在需要时，自己处理同步操作，因此程序员没有必要为自己的数据结构加锁。如果你执意为
     * 流中要使用的数据结构加锁，比如操作的原始集合，那么有可能是自找麻烦。
     *
     * 使用 parallel 方法能轻易将流转换为并行流。但其实还有一个叫 sequential 的方法。在要对流求值时，不能同时
     * 处于两种模式，要么是并行的，要么是串行的。如果同时调用了 parallel 和 sequential 方法，最后调用的那个方
     * 法起效。
     */
    public static void main(String[] args) {

    }

}
