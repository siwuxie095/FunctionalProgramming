package com.siwuxie095.functional.chapter4th.example3rd;

import com.siwuxie095.functional.common.Album;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;

/**
 * @author Jiajing Li
 * @date 2020-10-17 16:01:06
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 基本类型
     *
     * 在 Java 中，有一些相伴的类型，比如 int 和 Integer，前者是基本类型，后者是装箱类型。基本类型内建在语言和运行环境中，
     * 是基本的程序构建模块；而装箱类型属于普通的 Java 类，只不过是对基本类型的一种封装。
     *
     * PS：基本类型 即 基本数据类型，装箱类型，也称 包装类型，即 引用数据类型。
     *
     * Java 的泛型是基于对泛型参数类型的擦除。换句话说，假设它是 Object 对象的实例，因此只有装箱类型才能作为泛型参数。这就
     * 解释了为什么在 Java 中想要一个包含整型值的列表 List<int>，实际上得到的却是一个包含整型对象的列表 List<Integer>。
     *
     * 麻烦的是，由于装箱类型是对象，因此在内存中存在额外开销。比如，整型在内存中占用 4 字节，整型对象却要占用 16 字节。这一
     * 情况在数组上更加严重，因为整型数组中的每个元素只占用基本类型的内存，而整型对象数组中，每个元素都是内存中的一个指针，指
     * 向 Java 堆中的某个对象。在最坏的情况下，同样大小的数组，Integer[] 要比 int[] 多占用 6 倍内存。
     *
     * 将基本类型转换为装箱类型，称为装箱，反之则称为拆箱，两者都需要额外的计算开销。对于需要大量数值运算的算法来说，装箱和拆
     * 箱的计算开销，以及装箱类型占用的额外内存，会明显减缓程序的运行速度。
     *
     * 为了减小这些性能开销，Stream 类的某些方法对基本类型和装箱类型做了区分。比如高阶函数 mapToLong 和其他类似函数即为该
     * 方面的一个尝试。在 Java 8 中，仅对整型（int）、 长整型（long）和双浮点型（double）做了特殊处理，因为它们在数值计算
     * 中用得最多，特殊处理后的系统性能提升效果最明显。
     *
     * PS：高阶函数 mapToLong 中使用 ToLongFunction 函数式接口作为参数。
     *
     * 对基本类型做特殊处理的函数式接口在命名上有明确的规范。
     * （1）如果方法返回类型为基本类型，则在基本类型前加 To。比如 ToLongFunction。
     * （2）如果参数是基本类型，则不加前缀只需类型名即可，比如 LongFunction。
     *
     * 如果高阶函数使用基本类型，则在操作后加后缀 To 再加基本类型，如 mapToLong。
     *
     * 这些基本类型都有与之对应的 Stream，以基本类型名为前缀，如 LongStream。事实上， mapToLong 方法返回的不是一个一般的
     * Stream，而是一个特殊处理的 Stream，即 LongStream。
     *
     * 在 LongStream 中，map 方法的实现方式也不同，它接受一个 LongUnaryOperator 函数式接口作为参数，将一个长整型值映射
     * 成另一个长整型值。通过一些高阶函数装箱方法，如 mapToObj，也可以从一个基本类型的 Stream 得到一个装箱后的 Stream，
     * 如 Stream<Long>。
     *
     * PS：特殊的 Stream 有三种：IntStream、LongStream、DoubleStream，分别对应三种基本类型：int、long、double。
     *
     * 如有可能，应尽可能多地使用对基本类型做过特殊处理的方法，进而改善性能。这些特殊的 Stream 还提供额外的方法，避免重复实
     * 现一些通用的方法，让代码更能体现出数值计算的意图。
     *
     * 如下代码展示了如何使用这些方法：
     *
     *     private static void printTrackLengthStatistics(Album album) {
     *         IntSummaryStatistics trackLengthStatistics
     *                 = album.getTracks()
     *                 .mapToInt(track -> track.getLength())
     *                 .summaryStatistics();
     *         System.out.printf("Max: %d, Min: %d, Avg: %f, Sum: %d",
     *                 trackLengthStatistics.getMax(),
     *                 trackLengthStatistics.getMin(),
     *                 trackLengthStatistics.getAverage(),
     *                 trackLengthStatistics.getSum());
     *     }
     *
     * 这里使用 summaryStatistics 方法统计曲目长度，并向控制台输出曲目长度的一系列统计信息。无需手动计算这些信息，这里使用
     * 对基本类型进行特殊处理的方法 mapToInt，将每首曲目映射为曲目长度。
     *
     * 因为 mapToInt 方法返回一个 IntStream 对象，它包含一个 summaryStatistics 方法，这个方法能计算出各种各样的统计值，
     * 如 IntStream 对象内所有元素中的最小值、最大值、平均值以及数值总和。
     *
     * 这些统计值在所有特殊的 Stream 中都可以得出。如无需全部的统计值，也可分别调用 min、max、average 或 sum 方法获得单个
     * 的统计值，同样，三种基本类型对应的特殊 Stream 也都包含这些方法。
     */
    public static void main(String[] args) {
        Album album = new Album("Test", new ArrayList<>(), new ArrayList<>());
        printTrackLengthStatistics(album);
    }

    private static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStatistics
                = album.getTracks()
                .mapToInt(track -> track.getLength())
                .summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Avg: %f, Sum: %d",
                trackLengthStatistics.getMax(),
                trackLengthStatistics.getMin(),
                trackLengthStatistics.getAverage(),
                trackLengthStatistics.getSum());
    }

}
