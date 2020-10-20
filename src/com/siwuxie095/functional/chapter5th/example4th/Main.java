package com.siwuxie095.functional.chapter5th.example4th;

/**
 * @author Jiajing Li
 * @date 2020-10-20 21:04:46
 */
public class Main {

    /**
     * 使用收集器：转换成其他集合
     *
     * 使用 collect(toList())，可以在流中生成列表。显然，List 是能想到的从流中生成的最自然的数据结构，但有时还希望
     * 从流生成其他值，比如 Map 或 Set，或者希望定制一个类，将想要的东西抽象出来。
     *
     * 已知，仅凭流上方法的签名，就能判断出这是否是一个及早求值的操作。reduce 操作就是一个很好的例子，但有时人们希望
     * 能做得更多。
     *
     * 此时，收集器就闪亮登场了，它是一种通用的、从流生成复杂值的结构。只要将它传给 collect 方法，所有的流就都可以使
     * 用它了。
     *
     * 标准类库已经提供了一些有用的收集器，这里的收集器都是从 java.util.stream.Collectors 类中静态导入的。
     *
     * PS：collect 方法不是收集器，传到该方法中的参数才是收集器 Collector，也就是 Collectors 中的静态方法的返回
     * 值。简单言之，Collectors 中的静态方法都是收集器。
     *
     *
     *
     * 有一些收集器可以生成其他集合。比如 toList，生成了 java.util.List 类的实例。还有 toSet 和 toCollection，
     * 分别生成 Set 和 Collection 类的实例。
     *
     * 已知，流上可以有很多链式操作，但总有一些时候，需要最终生成一个集合。比如：
     * （1）已有代码是为集合编写的，因此需要将流转换成集合传入；
     * （2）在集合上进行一系列链式操作后，最终希望生成一个值；
     * （3）写单元测试时，需要对某个具体的集合做断言。
     *
     * 通常情况下，创建集合时需要调用适当的构造函数指明集合的具体类型。如下：
     *
     * List<Artist> artists = new ArrayList<>();
     *
     * 但是调用 toList 或者 toSet 方法时，不需要指定具体的类型。Stream 类库在背后自动挑选出了合适的类型。比如，使
     * 用 Stream 类库并行处理数据，收集并行操作的结果需要的 Set，和对线程安全没有要求的 Set 类是完全不同的。（这个
     * 例子该如何理解？？？）
     *
     * 可能还会有这样的情况，希望使用一个特定的集合收集值，而且可以稍后指定该集合的类型。比如，可能希望使用 TreeSet，
     * 而不是由框架在背后自动为你指定一种类型的 Set。此时就可以使用 toCollection，它接受一个函数作为参数，来创建集
     * 合。如下：
     *
     * stream.collect(toCollection(TreeSet::new));
     *
     * 即 使用 toCollection，用定制的集合收集元素。
     */
    public static void main(String[] args) {

    }

}
