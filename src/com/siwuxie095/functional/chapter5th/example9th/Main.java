package com.siwuxie095.functional.chapter5th.example9th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Jiajing Li
 * @date 2020-10-21 21:04:11
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：组合收集器
     *
     * Collectors 中的静态方法所代表的各种收集器已经很强大了，但如果将它们组合起来，会变得更强大。
     *
     * 以计算每个艺术家的专辑数量为例，简单的方案是对专辑先分组后计数，如下：
     *
     *     private static Map<Artist, Integer> numberOfAlbumsDumb(Stream<Album> albums) {
     *         Map<Artist, List<Album>> albumsByArtist
     *                 = albums.collect(Collectors.groupingBy(album -> album.getMainMusician()));
     *         Map<Artist, Integer> numberOfAlbums = new HashMap<>();
     *         for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
     *             numberOfAlbums.put(entry.getKey(), entry.getValue().size());
     *         }
     *         return numberOfAlbums;
     *     }
     *
     * 这种方式看起来简单，但却有点杂乱无章。这段代码也是命令式的代码，不能自动适应并行化操作。
     *
     * 这里实际上需要另外一个收集器，告诉 groupingBy 不用为每一个艺术家生成一个专辑列表，只需要对专辑计数就可以了。
     * 幸好，核心类库已经提供了一个这样的收集器: counting。如下：
     *
     *     private static Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
     *         return albums.collect(Collectors.groupingBy(album -> album.getMainMusician(),
     *                 Collectors.counting()));
     *     }
     *
     * groupingBy 先将元素分成块，每块都与分类函数 getMainMusician 提供的键值相关联，然后使用下游的另一个收集器
     * 收集每块中的元素个数，最后将结果映射为一个 Map。
     *
     * 再看一个例子，这次希望得到每个艺术家的专辑名称。简单的方案是将专辑分组，然后再调整生成的 Map 中的值，如下：
     *
     *     private static Map<Artist, List<String>> nameOfAlbumsDumb(Stream<Album> albums) {
     *         Map<Artist, List<Album>> albumsByArtist =
     *                 albums.collect(Collectors.groupingBy(album ->album.getMainMusician()));
     *
     *         Map<Artist, List<String>>  nameOfAlbums = new HashMap<>();
     *         for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
     *             nameOfAlbums.put(entry.getKey(), entry.getValue()
     *                     .stream()
     *                     .map(Album::getName)
     *                     .collect(Collectors.toList()));
     *         }
     *         return nameOfAlbums;
     *     }
     *
     * 同理，可以再次使用一个收集器，编写出更好、更快、更容易并行处理的代码。已知，可以使用 groupingBy 将专辑按主唱
     * 分组，但是其输出为一个 Map<Artist, List<Album>> 对象，它将每个艺术家和他的专辑列表关联起来，但这不是想要的，
     * 这里想要的是一个包含专辑名的字符串列表。
     *
     * 此时，真正想做的是将专辑列表映射为专辑名列表，这里不能直接使用流的 map 操作，因为列表是由 groupingBy 生成的。
     * 所以需要有一种方法，可以告诉 groupingBy 将它的值做映射，生成最终结果。
     *
     * 每个收集器都是生成最终值的一剂良方。这里需要两剂配方，一个传给另一个。谢天谢地，Oracle 公司的研究员们已经考虑
     * 到这种情况，提供了 mapping 收集器。
     *
     * mapping 允许在收集器的容器上执行类似 map 的操作。但需要指明使用什么样的集合类存储结果，比如 toList。这些收集
     * 器就像乌龟叠罗汉，龟龟相驮以至无穷。
     *
     * mapping 收集器和 map 方法一样，接受一个 Function 对象作为参数。这样，就可以重构上述代码，如下：
     *
     *     private static Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
     *         return albums.collect(Collectors.groupingBy(album ->album.getMainMusician(),
     *                 Collectors.mapping(Album::getName, Collectors.toList())));
     *     }
     *
     * 这两个例子中都用到了第二个收集器，用以收集最终结果的一个子集。这些收集器叫作下游收集器。收集器是生成最终结果的
     * 一剂配方，下游收集器则是生成部分结果的配方，主收集器中会用到下游收集器。
     *
     * 这种组合使用收集器的方式，使得它们在 Stream 类库中的作用更加强大。
     *
     * 那些为基本类型特殊定制的函数，如 averagingInt、summarizingLong 等，事实上和调用特殊 Stream 上的方法是等价
     * 的，加上它们是为了将它们当作下游收集器来使用的。
     */
    public static void main(String[] args) {
        numberOfAlbumsDumb(SampleData.albums);
        numberOfAlbums(SampleData.albums);
        nameOfAlbumsDumb(SampleData.albums);
        nameOfAlbums(SampleData.albums);
    }

    private static Map<Artist, Integer> numberOfAlbumsDumb(Stream<Album> albums) {
        Map<Artist, List<Album>> albumsByArtist
                = albums.collect(Collectors.groupingBy(album -> album.getMainMusician()));
        Map<Artist, Integer> numberOfAlbums = new HashMap<>();
        for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            numberOfAlbums.put(entry.getKey(), entry.getValue().size());
        }
        return numberOfAlbums;
    }

    private static Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(album -> album.getMainMusician(),
                Collectors.counting()));
    }

    private static Map<Artist, List<String>> nameOfAlbumsDumb(Stream<Album> albums) {
        Map<Artist, List<Album>> albumsByArtist =
                albums.collect(Collectors.groupingBy(album ->album.getMainMusician()));

        Map<Artist, List<String>>  nameOfAlbums = new HashMap<>();
        for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            nameOfAlbums.put(entry.getKey(), entry.getValue()
                    .stream()
                    .map(Album::getName)
                    .collect(Collectors.toList()));
        }
        return nameOfAlbums;
    }

    private static Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(album ->album.getMainMusician(),
                Collectors.mapping(Album::getName, Collectors.toList())));
    }


}
