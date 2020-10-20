package com.siwuxie095.functional.chapter5th.example7th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-20 22:21:50
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：数据分组
     *
     * 数据分组是一种更自然的分割数据操作，与将数据分成 ture 和 false 两部分不同，可以使用任意值对数据分组。
     * 比如，现在有一个由专辑组成的流，可以按专辑当中的主唱对专辑分组。如下：
     *
     *     public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
     *         return albums.collect(Collectors.groupingBy(album -> album.getMainMusician()));
     *     }
     *
     * 通过调用流的 collect 方法，传入一个收集器。groupingBy 收集器接受一个分组函数，用来对数据分组，就像
     * partitioningBy 一样，接受一个 Predicate 对象将数据分成 ture 和 false 两部分。这里使用的分类器是
     * 一个 Function 对象，和 map 操作用到的一样。
     *
     * groupingBy 收集器和 SQL 中的 group by 操作类似，这里只不过是在 Stream 类库中实现了而已。
     *
     * PS：数据分组，也可以称为数据分类。分组函数，也可以称为分类函数。
     */
    public static void main(String[] args) {

    }

    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(album -> album.getMainMusician()));
    }

}
