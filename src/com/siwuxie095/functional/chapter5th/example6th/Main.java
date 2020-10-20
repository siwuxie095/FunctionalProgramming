package com.siwuxie095.functional.chapter5th.example6th;

import com.siwuxie095.functional.common.Artist;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-20 22:02:47
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：数据分块
     *
     * 一个常用的流操作是将其分解成两个集合。假设有一个艺术家组成的流，希望将其分成两个部分，一部分是独唱歌手，另一部分
     * 是由多人组成的乐队。可以使用两次过滤操作，分别过滤出上述两种艺术家。
     *
     * 但是这样操作起来有问题。首先，为了执行两次过滤操作，需要有两个流。其次，如果过滤操作复杂，每个流上都要执行这样的
     * 操作，代码也会变得冗余。
     *
     * 幸好有这样一个收集器 partitioningBy，它接受一个流，并将其分成两部分。它使用 Predicate 对象判断一个元素应该
     * 属于哪个部分，并根据布尔值返回一个列表到 Map。因此，对于 true List 中的元素，Predicate 返回 true；对其他
     * List 中的元素，Predicate 返回 false。
     *
     * 使用它，我们就可以将乐队（有多个成员）和独唱歌手分开了。在本例中，分块函数指明艺术家是否为独唱歌手。如下：
     *
     *     public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
     *         return artists.collect(Collectors.partitioningBy(artist -> artist.isSolo()));
     *     }
     *
     * 也可以使用方法引用代替 Lambda 表达式，如下：
     *
     *     public Map<Boolean, List<Artist>> bandsAndSoloRef(Stream<Artist> artists) {
     *         return artists.collect(Collectors.partitioningBy(Artist::isSolo));
     *     }
     *
     * PS：数据分块，也可以称为数据分区。分块函数，也可以称为分区函数。
     */
    public static void main(String[] args) {

    }

    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
        return artists.collect(Collectors.partitioningBy(artist -> artist.isSolo()));
    }

    public Map<Boolean, List<Artist>> bandsAndSoloRef(Stream<Artist> artists) {
        return artists.collect(Collectors.partitioningBy(Artist::isSolo));
    }

}
