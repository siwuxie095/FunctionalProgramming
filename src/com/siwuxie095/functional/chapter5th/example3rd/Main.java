package com.siwuxie095.functional.chapter5th.example3rd;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-19 21:08:48
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 元素顺序
     *
     * 已知，一些集合类型中的元素是按顺序排列的，比如 List；而另一些则是无序的，比如 HashSet。增加了流操作后，顺序问题
     * 变得更加复杂。那么流中的元素以何种顺序排列的呢？
     *
     * 直观上看，流是有序的，因为流中的元素都是按顺序处理的。这种顺序称为出现顺序。出现顺序的定义依赖于数据源和对流的操作。
     *
     * 在一个有序集合中创建一个流时，流中的元素就按出现顺序排列。因此，如下代码总是可以通过：
     *
     *     private static void ordered() {
     *         List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
     *         List<Integer> sameOrder = numbers.stream()
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(numbers, sameOrder);
     *     }
     *
     * 如果集合本身就是无序的，由此生成的流也是无序的。HashSet 就是一种无序的集合，因此不能保证如下代码每次都能通过：
     *
     *     private static void unordered() {
     *         Set<Integer> numbers = new HashSet<>(Arrays.asList(4, 3, 2, 1));
     *         List<Integer> sameOrder = numbers.stream()
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList(4, 3, 2, 1), sameOrder);
     *     }
     *
     * 流的目的不仅是在集合类之间做转换，而且同时提供了一组处理数据的通用操作。有些集合本身是无序的，但这些操作有时会产生
     * 顺序。试看如下代码：
     *
     *     private static void produceOrder() {
     *         Set<Integer> numbers = new HashSet<>(Arrays.asList(4, 3, 2, 1));
     *         List<Integer> sameOrder = numbers.stream()
     *                 .sorted()
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList(1, 2, 3, 4), sameOrder);
     *     }
     *
     * 一些中间操作会产生顺序，如果进来的流是有序的，那么出去的流也是有序的。如果进来的流是无序的，那么出去的流也是无序的。
     * 比如对值做映射时，进来的流有序，映射后的值是有序的，这种顺序就会保留下来。
     *
     *     private static void midOperation() {
     *         List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
     *         List<Integer> stillOrdered = numbers.stream() .map(x -> x + 1)
     *                 .collect(Collectors.toList());
     *         // 顺序得到了保留
     *         Assert.assertEquals(Arrays.asList(2, 3, 4, 5), stillOrdered);
     *
     *         Set<Integer> unordered = new HashSet<>(numbers);
     *         List<Integer> stillUnordered = unordered.stream() .map(x -> x + 1)
     *                 .collect(Collectors.toList());
     *         // 顺序得不到保证
     *         MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(2));
     *         MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(3));
     *         MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(4));
     *         MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(5));
     *     }
     *
     * 一些操作在有序的流上开销更大，调用 unordered 方法消除这种顺序就能解决该问题。但大多数操作都是在有序流上效率更高，
     * 比如 filter、map 和 reduce 等。
     *
     * 消除顺序后会带来一些意想不到的结果，比如使用并行流时，forEach 方法不能保证元素是按顺序处理的。如果需要保证按顺序
     * 处理，就应该使用 forEachOrdered 方法。
     */
    public static void main(String[] args) {
        ordered();
        unordered();
        produceOrder();
        midOperation();
    }

    /**
     * 顺序测试永远通过
     */
    private static void ordered() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> sameOrder = numbers.stream()
                .collect(Collectors.toList());
        Assert.assertEquals(numbers, sameOrder);
    }

    /**
     * 顺序测试不能保证每次通过
     *
     * 其中的断言有时会失败
     */
    private static void unordered() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(4, 3, 2, 1));
        List<Integer> sameOrder = numbers.stream()
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(4, 3, 2, 1), sameOrder);
    }

    /**
     * 生成出现顺序
     */
    private static void produceOrder() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(4, 3, 2, 1));
        List<Integer> sameOrder = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4), sameOrder);
    }

    /**
     * 本例中关于顺序的假设永远是正确的
     */
    private static void midOperation() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> stillOrdered = numbers.stream() .map(x -> x + 1)
                .collect(Collectors.toList());
        // 顺序得到了保留
        Assert.assertEquals(Arrays.asList(2, 3, 4, 5), stillOrdered);

        Set<Integer> unordered = new HashSet<>(numbers);
        List<Integer> stillUnordered = unordered.stream() .map(x -> x + 1)
                .collect(Collectors.toList());
        // 顺序得不到保证
        MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(2));
        MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(3));
        MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(4));
        MatcherAssert.assertThat(stillUnordered, CoreMatchers.hasItem(5));
    }

}
