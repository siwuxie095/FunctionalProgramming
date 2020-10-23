package com.siwuxie095.functional.chapter6th.example2nd;

/**
 * @author Jiajing Li
 * @date 2020-10-23 08:00:06
 */
public class Main {

    /**
     * 并行和并发
     *
     * 并行和并发是两个不同的概念，它们的作用也不一样。
     *
     * 并行是两个任务在同一时间发生，比如运行在多核 CPU 上。并发则是两个任务共享时间段，比如一个程序要运行两个任务，
     * 并且只有一个 CPU 给它们分配不同的时间片，那么这就是并发，而不是并行。
     *
     * 并行化是指为缩短任务执行时间，将一个任务分解成几部分，然后并行执行。这和顺序执行的任务量是一样的，区别就像用
     * 更多的马来拉车，花费的时间自然减少了。实际上，和顺序执行相比，并行化执行任务时，CPU 承载的工作量更大。
     *
     * 这里会讨论一种特殊形式的并行化：数据并行化。数据并行化是指将数据分成块，为每块数据分配单独的处理单元。还是拿
     * 马拉车那个例子打比方，就像从车里取出一些货物，放到另一辆车上，两辆马车都沿着同样的路径到达目的地。
     *
     * 当需要在大量数据上执行同样的操作时，数据并行化很管用。它将问题分解为可在多块数据上求解的形式，然后对每块数据
     * 执行运算，最后将各数据块上得到的结果汇总，从而获得最终答案。
     *
     * 人们经常拿任务并行化和数据并行化做比较，在任务并行化中，线程不同，工作各异。最常遇到的 Java EE 应用容器便是
     * 任务并行化的例子之一，每个线程不光可以为不同用户服务，还可以为同一个用户执行不同的任务，比如，登录或者往购物
     * 车添加商品。
     */
    public static void main(String[] args) {

    }

}
