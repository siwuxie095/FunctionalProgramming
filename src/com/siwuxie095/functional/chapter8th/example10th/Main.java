package com.siwuxie095.functional.chapter8th.example10th;

/**
 * @author Jiajing Li
 * @date 2020-10-26 21:31:11
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 开闭原则
     *
     * 软件应该对扩展开放，对修改闭合。
     *
     * PS：开闭原则，也叫 开放封闭原则。
     *
     * 开闭原则的首要目标和单一功能原则类似：让软件易于修改。一个新增功能或一处改动，会影响整个代码，容易引入新的缺陷。
     * 开闭原则保证已有的类在不修改内部实现的基础上可扩展，这样就努力避免了上述问题。
     *
     * 第一次听说开闭原则时，感觉有点痴人说梦。不改变实现怎么能扩展一个类的功能呢？答案是借助于抽象，可插入新的功能。
     *
     * 来看一个具体的例子。现在要写一个程序用来衡量系统性能，并且把得到的结果绘制成图形。比如有描述计算机花在用户空间、
     * 内核空间和输入输出上的时间散点图。将负责显示这些指标的类叫作 MetricDataGraph。
     *
     * 设计 MetricDataGraph 类的方法之一是将代理收集到的各项指标放入该类，该类的公开 API 如下：
     *
     * public class MetricDataGraph {
     *
     *     public void updateUserTime(int value) {}
     *
     *     public void updateSystemTime(int value) {}
     *
     *     public void updateIoTime(int value) {}
     *
     * }
     *
     * 但这样的设计意味着每次想往散点图中添加新的时间点，都要修改 MetricDataGraph 类。通过引入抽象可以解决这个问题，
     * 即 使用一个新类 TimeSeries 来表示各种时间点。这时，MetricDataGraph 类的公开 API 就得以简化，不必再依赖于
     * 某项具体指标。如下：
     *
     * public class SimplifiedMetricDataGraph {
     *
     *     public void addTimeSeries(TimeSeries values) {}
     *
     * }
     *
     * 每项具体指标现在可以实现 TimeSeries 接口，在需要时能直接插入。比如，可能会有这些实现类：
     * （1）UserTimeSeries；
     * （2）SystemTimeSeries；
     * （3）IoTimeSeries。
     * 如果要添加新的，比如由于虚拟化所浪费的 CPU 时间，就可以增加一个新的实现类：StealTimeSeries。这样，就扩展了
     * MetricDataGraph 类，但并没有修改它。
     *
     *
     * 高阶函数也展示出了同样的特性：对扩展开放，对修改闭合。ThreadLocal 类就是一个很好的例子。ThreadLocal 有一个
     * 特殊的变量，每个线程都有一个该变量的副本并与之交互。该类的静态方法 withInitial 是一个高阶函数，传入一个负责
     * 生成初始值的 Lambda 表达式。
     *
     * 这符合开闭原则，因为不用修改 ThreadLocal 类，就能得到新的行为。给 withInitial 方法传入不同的工厂方法，就能
     * 得到拥有不同行为的 ThreadLocal 实例。比如，可以使用 ThreadLocal 生成一个 DateFormatter 实例，该实例是线
     * 程安全的。如下：
     *
     *         // 实现
     *         ThreadLocal<DateFormat> localFormatter
     *                 = ThreadLocal.withInitial(() -> new SimpleDateFormat());
     *
     *         // 使用
     *         DateFormat formatter = localFormatter.get();
     *
     * 通过传入不同的 Lambda 表达式，可以得到完全不同的行为。比如，可以为每个 Java 线程创建了唯一的、有序的标识符。
     * 如下：
     *
     *         // 或者这样实现
     *         AtomicInteger threadId = new AtomicInteger();
     *         ThreadLocal<Integer> localId
     *                 = ThreadLocal.withInitial(() -> threadId.getAndIncrement());
     *
     *         // 使用
     *         int idForThisThread = localId.get();
     *
     * 对开闭原则的另外一种理解和传统的思维不同，那就是使用不可变对象实现开闭原则。不可变对象是指一经创建就不能改变的
     * 对象。
     *
     * "不可变性" 一词有两种解释：观测不可变性和实现不可变性。观测不可变性是指在其他对象看来，该类是不可变的；实现不
     * 可变性是指对象本身不可变。实现不可变性意味着观测不可变性，反之则不一定成立。
     *
     * java.lang.String 宣称是不可变的，但事实上只是观测不可变，因为它在第一次调用 hashCode 方法时缓存了生成的散
     * 列值。在其他类看来，这是完全安全的，它们看不出散列值是每次在构造函数中计算出来的，还是从缓存中返回的。
     *
     * 之所以在讲解 Lambda 表达式时谈及不可变对象，是因为它们都是函数式编程中耳熟能详的概念，也是 Lambda 表达式的
     * 发源地。
     *
     * 之所以说不可变对象实现了开闭原则，是因为它们的内部状态无法改变，可以安全地为其增加新的方法。新增加的方法无法改
     * 变对象的内部状态，因此对修改是闭合的；但它们又增加了新的行为，因此对扩展是开放的。当然，还需留意不要改变程序其
     * 他部分的状态。
     *
     * 因其天生线程安全的特性，不可变对象引起了人们的格外注意。它们没有内部状态可变，因此可以安全地在不同线程之间共享。
     *
     * 如果回顾这几种方式，会发现已经偏离了传统的开闭原则。事实上，在 Bertrand Meyer 第一次引入这个原则时，原意是一
     * 旦实现后，类就不允许改动了。在现代敏捷开发环境中，完成一个类的说法很明显已经过时了。业务需求和使用方法的变化可
     * 能会让一个类的功能和当初设计的不同。当然这不是忽视这一原则的理由，只是说明了所谓的原则只应作为指导，而不应教条
     * 地全盘接受，走向极端。
     *
     * 另外还有一点值得思考，在 Java 8 中，使用抽象插入多个类，或者使用高阶函数来实现开闭原则其实是一样的。因为抽象
     * 需要使用一个接口或抽象类来定义方法，这其实就是一种多态的使用方式。
     *
     * 在 Java 8 中，任何传入高阶函数的 Lambda 表达式都由一个函数式接口表示，高阶函数负责调用其唯一的方法，根据传入
     * Lambda 表达式的不同，行为也不同。这其实也是在用多态来实现开闭原则。
     */
    public static void main(String[] args) {

    }

}
