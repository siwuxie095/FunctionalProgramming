package com.siwuxie095.functional.chapter6th.example5th;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jiajing Li
 * @date 2020-10-23 21:45:17
 */
public class Main {

    /**
     * 模拟系统
     *
     * 并行化流操作的用武之地是使用简单操作处理大量数据，比如模拟系统。这里将会搭建一个简易的模拟系统来理解摇骰子，
     * 但其中的原理对于大型、真实的系统也适用。
     *
     * 这里使用的是蒙特卡罗模拟法。蒙特卡罗模拟法会重复相同的模拟很多次，每次模拟都使用随机生成的种子。每次模拟的
     * 结果都被记录下来，汇总得到一个对系统的全面模拟。蒙特卡罗模拟法被大量用在工程、金融和科学计算领域。
     *
     * PS：蒙特卡罗模拟法，有时也作蒙特卡洛模拟法。
     *
     * 如果公平地掷两次骰子，然后将赢的一面上的点数相加，就会得到一个 2~12 的数字。
     * （1）点数的和至少是 2，因为骰子六个面上最小的点数是 1，而这里将骰子掷了两次；
     * （2）点数的和最大超不过 12，因为骰子点数最多的一面也不过 6 点，而这里将骰子掷了两次。
     *
     * 这里想要得出点数落在 2~12 之间每个值的概率。
     *
     * 解决该问题的方法之一是求出掷骰子的所有组合，比如，得到 2 点的方式是第一次掷得 1 点，第二次也掷得 1 点。总
     * 共有 36 种可能的组合，因此，掷得 2 点的概率就是 1/36。
     *
     * 另外一种解法是使用 1 到 6 的随机数模拟掷骰子事件，然后用得到每个点数的次数除以总的投掷次数。这就是一个简单
     * 的蒙特卡罗模拟。模拟投掷骰子的次数越多，得到的结果越准确，因此，希望尽可能多地增加模拟次数。
     *
     * 如下代码展示了如何使用流实现蒙特卡罗模拟法：
     *
     *     private static Map<Integer, Double> parallelDiceRolls(int N) {
     *         double fraction = 1.0 / N;
     *         return IntStream.range(0, N)
     *                 .parallel()
     *                 .mapToObj(x -> twoDiceThrows())
     *                 .collect(Collectors.groupingBy(side -> side,
     *                         Collectors.summingDouble(n -> fraction)));
     *     }
     *
     *     private static int twoDiceThrows() {
     *         ThreadLocalRandom random = ThreadLocalRandom.current();
     *         int firstThrow = random.nextInt(1, 7);
     *         int secondThrow = random.nextInt(1, 7);
     *         return firstThrow + secondThrow;
     *     }
     *
     * N 代表模拟次数。首先使用 IntStream 的 range 方法创建大小为 N 的流，然后调用 parallel 方法使用流的并行
     * 化操作，而 twoDiceThrows 函数模拟了连续掷两次骰子事件，返回值是两次点数之和。使用 mapToObj 方法以便在流
     * 上使用 twoDiceThrows 函数。然后得到了需要合并的所有结果的流，使用 groupingBy 收集器将点数一样的结果合并。
     * 这里要计算每个点数的出现次数，然后除以总的模拟次数 N。在流框架中，将数字映射为 1/N 并且相加很简单，这和前
     * 面说的计算方法是等价的。使用 summingDouble 方法即可完成这一步。最终的返回值类型是 Map<Integer, Double>，
     * 是点数之和到它们的概率的映射。
     *
     * 显然，这段代码不算儿戏，但使用短短几行代码即能实现蒙特卡罗模拟法还是很精巧的。重要的是模拟的次数越多，得到的
     * 结果越准确，因此运行多次模拟的动机就会更加强烈。这是一个很好的并行化案列，并行化能带来速度的提升。
     *
     * 为了对比，ManualDiceRolls 类给出了手动实现并行化蒙特卡罗模拟法的代码。可以看到，大多数代码都在处理调度和
     * 等待线程池中的某项任务完成。而使用并行化的流时，这些都不用程序员手动管理。
     */
    public static void main(String[] args) {
        Map<Integer, Double> map = parallelDiceRolls(100_000_000);
        System.out.println(map);
    }

    /**
     * 使用蒙特卡罗模拟法并行化模拟掷骰子事件
     */
    private static Map<Integer, Double> parallelDiceRolls(int N) {
        double fraction = 1.0 / N;
        return IntStream.range(0, N)
                .parallel()
                .mapToObj(x -> twoDiceThrows())
                .collect(Collectors.groupingBy(side -> side,
                        Collectors.summingDouble(n -> fraction)));
    }

    private static int twoDiceThrows() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int firstThrow = random.nextInt(1, 7);
        int secondThrow = random.nextInt(1, 7);
        return firstThrow + secondThrow;
    }

}
