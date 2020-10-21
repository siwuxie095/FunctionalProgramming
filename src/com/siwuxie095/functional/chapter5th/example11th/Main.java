package com.siwuxie095.functional.chapter5th.example11th;

import com.siwuxie095.functional.chapter5th.example10th.StringCombiner;
import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-22 07:38:30
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：对收集器的归一化处理
     *
     * 如果想为自己领域内的类定制一个收集器，那么定制收集器是个不错的选择，但也不妨考虑一下其他替代方案。最容易想到的替代方案
     * 是构建若干个集合对象，作为参数传给领域内类的构造函数。如果领域内的类包含多种集合，这种方式又简单又适用。
     *
     * 当然，如果领域内的类没有这些集合，需要在已有数据上计算，那这种方法就不合适了。但即使如此，也不见得需要定制一个收集器。
     * 还可以使用 reducing 收集器，它为流上的归一操作提供了统一实现。
     *
     * PS：归一操作，也称为 归约操作。
     *
     * 如下代码展示了如何使用 reducing 收集器编写字符串处理程序：
     *
     *         String result =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .collect(Collectors.reducing(
     *                                 new StringCombiner(", ", "[", "]"),
     *                                 name -> new StringCombiner(", ", "[", "]").add(name),
     *                                 StringCombiner::merge))
     *                         .toString();
     *
     * 这和基于 reduce 操作的实现很像，从方法名中就能看出，如下：
     *
     *         String result =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .reduce(new StringCombiner(", ", "[", "]"),
     *                                 StringCombiner::add,
     *                                 StringCombiner::merge)
     *                         .toString();
     *
     * 区别在于 Collectors.reducing 的第二个参数，为流中每个元素创建了唯一的 StringCombiner。显然，这种方式是非常低效的，
     * 这也是要定制收集器的原因之一。
     */
    public static void main(String[] args) {
        formatByReducing(SampleData.membersOfTheBeatles);
        formatByReduce(SampleData.membersOfTheBeatles);
    }

    private static void formatByReducing(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .collect(Collectors.reducing(
                                new StringCombiner(", ", "[", "]"),
                                name -> new StringCombiner(", ", "[", "]").add(name),
                                StringCombiner::merge))
                        .toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    private static void formatByReduce(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringCombiner(", ", "[", "]"),
                                StringCombiner::add,
                                StringCombiner::merge)
                        .toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

}
