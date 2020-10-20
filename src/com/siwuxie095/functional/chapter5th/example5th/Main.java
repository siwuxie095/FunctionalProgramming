package com.siwuxie095.functional.chapter5th.example5th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-20 21:32:23
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：转换成值
     *
     * 利用收集器可以让流生成一个值。比如 maxBy 和 minBy 允许程序员按某种特定的顺序生成一个值（最大值或最小值）。
     *
     * 比如，找出成员最多的乐队，如下：
     *
     *     public Optional<Artist> biggestGroup(Stream<Artist> artists) {
     *         Function<Artist, Long> getCount = artist -> artist.getMembers().count();
     *         return artists.collect(Collectors.maxBy(Comparator.comparing(getCount)));
     *     }
     *
     * 它使用一个 Lambda 表达式，将乐队映射为成员数量，然后定义了一个比较器，并将比较器传入 maxBy 收集器。
     *
     * 而 minBy 就如它的方法名，是用来找出最小值的。
     *
     * 还有些收集器实现了一些常用的数值运算。比如，计算专辑中曲目的平均数，如下：
     *
     *     public double averageNumberOfTracks(List<Album> albums) {
     *         return albums.stream()
     *             .collect(Collectors.averagingInt(album -> album.getTrackList().size()));
     *     }
     *
     * 和之前一样，通过调用 stream 方法让集合生成流，然后调用 collect 方法收集结果。 averagingInt 方法接受一个
     * Lambda 表达式作参数，将流中的元素转换成一个整数，然后再计算平均数。另外，还有和 long 以及 double 类型对应
     * 的方法，帮助程序员将元素转换成相应类型的值。如：averagingLong 和 averagingDouble。
     *
     * 在一些特殊的流中，如 IntStream，为数值运算定义了一些额外的方法。事实上，Java 8 也提供了能够完成类似功能的
     * 收集器，如 averagingInt 求平均值、summingInt 求和。另外 SummaryStatistics 也可以使用 summarizingInt
     * 进行收集（当然，也都有对应的 long、double 方法）。
     */
    public static void main(String[] args) {

    }

    public Optional<Artist> biggestGroup(Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.collect(Collectors.maxBy(Comparator.comparing(getCount)));
    }

    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream()
            .collect(Collectors.summingInt(album -> album.getTrackList().size()));
    }

}
