package com.siwuxie095.functional.chapter8th.example9th;

/**
 * @author Jiajing Li
 * @date 2020-10-26 20:49:02
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 单一功能原则
     *
     * 程序中的类或方法只能有一个改变的理由。
     *
     * PS：单一功能原则，也叫 单一职责原则。
     *
     * 软件开发中不可避免的情况是需求的改变。这可能是需要增加新功能，也可能是你对问题的理解或者客户发生变化了，或者你想变得更快，
     * 总之，软件会随着时间不断演进。
     *
     * 当软件的需求发生变化，实现这些功能的类和方法也需要变化。如果你的类有多个功能，一个功能引发的代码变化会影响该类的其他功能。
     * 这可能会引入缺陷，还会影响代码演进的能力。
     *
     * 看这样一个简单的例子，现在要从资产列表生成 BalanceSheet 表格，然后输出成一份 PDF 格式的报告。如果实现时将制表和输出功
     * 能都放进同一个类，那么该类就有两个变化的理由。可能想改变输出功能，输出不同的格式，比如 HTML，可能还想改变 BalanceSheet
     * 的细节。这为将问题分解成两个类提供了很好的理由：一个负责将 BalanceSheet 生成表格，一个负责输出。
     *
     * 单一功能原则不止于此：一个类不仅要功能单一，而且还需将功能封装好。换句话说，如果想改变输出格式，那么只需改动负责输出的类，
     * 而不必关心负责制表的类。
     *
     * 这是强内聚性设计的一部分。说一个类是内聚的，是指它的方法和属性需要统一对待，因为它们紧密相关。如果试着将一个内聚的类拆分，
     * 可能会得到刚才创建的那两个类。
     *
     * 既然已经知道了什么是单一功能原则，那么问题来了：这和 Lambda 表达式有什么关系？
     *
     * Lambda 表达式在方法级别能更容易实现单一功能原则。来看一个例子，如下：
     *
     *         public long countPrimes(int upTo) {
     *             long tally = 0;
     *             for (int i = 1; i < upTo; i++) {
     *                 boolean isPrime = true;
     *                 for (int j = 2; j < i; j++) {
     *                     if (i % j == 0) {
     *                         isPrime = false;
     *                     }
     *                 }
     *                 if (isPrime) {
     *                     tally++;
     *                 }
     *             }
     *             return tally;
     *         }
     *
     * 这段代码能得出一定范围内有多少个质数。很显然，其中同时干了两件事：计数和判断一个数是否是质数。
     *
     * 通过简单重构，将两个功能一分为二。如下：
     *
     *         public long countPrimes(int upTo) {
     *             long tally = 0;
     *             for (int i = 1; i < upTo; i++) {
     *                 if (isPrime(i)) {
     *                     tally++;
     *                 }
     *             }
     *             return tally;
     *         }
     *
     *         private boolean isPrime(int number) {
     *             for (int i = 2; i < number; i++) {
     *                 if (number % i == 0) {
     *                     return false;
     *                 }
     *             }
     *             return true;
     *         }
     *
     * 但这段代码还是有两个功能。代码中的大部分都在对数字循环，如果遵守单一功能原则，那么迭代过程应该封装起来。改进代码还有一个
     * 现实的原因，如果需要对一个很大的 upTo 计数，希望可以并行操作。没错，线程模型也是代码的职责之一。
     *
     * 可以使用 Java 8 的集合流重构上述代码，将循环操作交给类库本身处理。这里使用 range 方法从 0 至 upTo 计数，然后 filter
     * 出质数，最后对结果做 count。如下：
     *
     *         public long countPrimes(int upTo) {
     *             return IntStream.range(1, upTo)
     *                     .filter(this::isPrime)
     *                     .count();
     *         }
     *
     *         private boolean isPrime(int number) {
     *             return IntStream.range(2, number)
     *                     .allMatch(x -> (number % x) != 0);
     *         }
     *
     * 如果想利用更多 CPU 加速计数操作，可使用 parallelStream 方法，而不需要修改任何其他代码，如下：
     *
     *         public long countPrimes(int upTo) {
     *             return IntStream.range(1, upTo)
     *                     .parallel()
     *                     .filter(this::isPrime)
     *                     .count();
     *         }
     *
     *         private boolean isPrime(int number) {
     *             return IntStream.range(2, number)
     *                     .allMatch(x -> (number % x) != 0);
     *         }
     *
     * 因此，利用高阶函数，可以轻松帮助程序员实现单一功能原则。
     */
    public static void main(String[] args) {

    }

}
