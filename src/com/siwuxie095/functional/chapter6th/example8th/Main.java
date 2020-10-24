package com.siwuxie095.functional.chapter6th.example8th;

import org.junit.Assert;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Jiajing Li
 * @date 2020-10-24 22:59:34
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 并行化数组操作
     *
     * Java 8 还引入了一些针对数组的并行操作，脱离流框架也可以使用 Lambda 表达式。像流框架上的操作一样，这些操作
     * 也都是针对数据的并行化操作。下面来看看如何使用这些操作解决那些使用流框架难以解决的问题。
     *
     * 这些操作都在工具类 Arrays 中，该类还包括 Java 以前版本中提供的和数组相关的有用方法。
     *
     * 如下是新增的并行化操作：
     * parallelPrefix               任意给定一个函数，计算数组的 "和"
     * parallelSetAll               使用 Lambda 表达式更新数组元素
     * parallelSort                 并行化对数组元素排序
     *
     * 在 Java 8 以前，可能以前写过如下代码，即 使用一个 for 循环初始化数组。这里，用数组下标初始化数组中的每个
     * 元素。
     *
     *     private static double[] imperativeInitilize(int size) {
     *         double[] values = new double[size];
     *         for(int i = 0; i < values.length; i++) {
     *             values[i] = i;
     *         }
     *         return values;
     *     }
     *
     * 使用 parallelSetAll 方法能轻松地并行化该过程，代码如下所示。首先提供了一个用于操作的数组，然后传入一个
     * Lambda 表达式，根据数组下标计算元素的值。在该例中，数组下标和元素的值是一样的。使用这些方法有一点要小心：
     * 它们改变了传入的数组，而没有创建一个新的数组。
     *
     *     private static double[] parallelInitialize(int size) {
     *         double[] values = new double[size];
     *         Arrays.parallelSetAll(values, i -> i);
     *         return values;
     *     }
     *
     * 而 parallelPrefix 操作擅长对时间序列数据做累加，它会更新一个数组，将每一个元素替换为当前元素和其前驱元素
     * 的和，这里的 "和" 是一个宽泛的概念，它不必是加法，可以是任意一个 BinaryOperator。
     *
     * 使用该方法能计算的例子之一是一个简单的滑动平均数。在时间序列上增加一个滑动窗口，计算出窗口中的平均值。如果
     * 输入数据为 0、1、2、3、4、3.5，滑动窗口的大小为 3，则简单滑动平均数为 1、2、3、3.5。如下代码展示了如何
     * 计算滑动平均数。
     *
     *     private static double[] simpleMovingAverage(double[] values, int n) {
     *         double[] sums = Arrays.copyOf(values, values.length);
     *         Arrays.parallelPrefix(sums, Double::sum);
     *         int start = n - 1;
     *         return IntStream.range(start, sums.length)
     *                 .mapToDouble(i -> {
     *                     double prefix = (i == start) ? 0 : sums[i - n];
     *                     return (sums[i] - prefix) / n;
     *                 })
     *                 .toArray();
     *     }
     *
     * 这段代码有点复杂，下面分步介绍它是如何工作的。参数 n 是时间窗口的大小，据此计算滑动平均值。由于要使用的并行
     * 操作会改变数组内容，为了不修改原有数据，先复制了一份输入数据。
     *
     * 然后通过 parallelPrefix 执行并行操作，将数组的元素相加。现在 sums 变量中保存了求和结果。比如输入 0、1、
     * 2、3、4、3.5，则计算后的值为 0.0、1.0、3.0、6.0、10.0、13.5。
     *
     * 现在有了和，就能计算出时间窗口中的和了，减去窗口起始位置的元素即可，除以 n 即得到平均值。可以使用已有的流中
     * 的方法计算该值，这里使用 Intstream.range 得到包含所需元素下标的流。
     *
     * 在 mapToDouble 中，使用总和减去窗口起始值，然后再除以 n 得到平均值。最后通过 toArray 将流转换为数组。
     */
    public static void main(String[] args) {
        double[] values = {0, 1, 2, 3, 4, 3.5};
        int n = 3;
        double[] res = simpleMovingAverage(values, n);
        double[] expected = {1, 2, 3, 3.5};
        Assert.assertArrayEquals(expected, res, 0.0D);
    }

    /**
     * 使用 for 循环初始化数组
     */
    private static double[] imperativeInitilize(int size) {
        double[] values = new double[size];
        for(int i = 0; i < values.length; i++) {
            values[i] = i;
        }
        return values;
    }

    /**
     * 使用并行化数组操作初始化数组
     */
    private static double[] parallelInitialize(int size) {
        double[] values = new double[size];
        Arrays.parallelSetAll(values, i -> i);
        return values;
    }

    /**
     * 计算简单滑动平均数
     */
    private static double[] simpleMovingAverage(double[] values, int n) {
        double[] sums = Arrays.copyOf(values, values.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = n - 1;
        return IntStream.range(start, sums.length)
                .mapToDouble(i -> {
                    double prefix = (i == start) ? 0 : sums[i - n];
                    return (sums[i] - prefix) / n;
                })
                .toArray();
    }




}
