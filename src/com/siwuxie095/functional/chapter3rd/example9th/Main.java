package com.siwuxie095.functional.chapter3rd.example9th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-14 21:55:14
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 要点回顾与巩固练习
     *
     * 1、要点回顾
     *
     * （1）内部迭代将更多控制权交给了集合类。
     * （2）和 Iterator 类似，Stream 是一种内部迭代方式。
     * （3）将 Lambda 表达式和 Stream 上的方法结合起来，可以完成很多常见的集合操作。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）常用流操作。实现如下函数：
     *
     * 问：
     * 编写一个求和函数，计算流中所有数之和。例如，int addUp(Stream<Integer> numbers)；
     * 答：
     *     private static int addUp(Stream<Integer> numbers) {
     *         return numbers.reduce(0, (acc, ele) -> acc + ele);
     *     }
     *
     * 问：
     * 编写一个函数，接受艺术家列表作为参数，返回一个字符串列表，其中包含艺术家的姓名和国籍；
     * 答：
     *     private static List<String> getNamesAndOrigins(List<Artist> artists) {
     *         return artists.stream()
     *                 .flatMap(artist -> Stream.of(artist.getName(), artist.getNationality()))
     *                 .collect(Collectors.toList());
     *     }
     *
     * 问：
     * 编写一个函数，接受专辑列表作为参数，返回一个由最多包含 3 首歌曲的专辑组成的列表。
     * 答：
     *     private static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> albums) {
     *         return albums.stream()
     *                 .filter(album -> album.getTrackList().size() <= 3)
     *                 .collect(Collectors.toList());
     *     }
     *
     *
     * （2）迭代。
     *     private static int iterate(List<Artist> artists) {
     *         int totalMembers = 0;
     *         for (Artist artist : artists) {
     *             Stream<Artist> members = artist.getMembers();
     *             totalMembers += members.count();
     *         }
     *         return totalMembers;
     *     }
     *
     * 问：
     * 修改如上代码，将外部迭代转换成内部迭代。
     * 答：
     * 法一：
     *     private static int convertToInternalIteration1st(List<Artist> artists) {
     *         return (int) artists.stream()
     *                 .flatMap(artist -> artist.getMembers())
     *                 .count();
     *     }
     *
     * 法二：
     *     private static int convertToInternalIteration2nd(List<Artist> artists) {
     *         return artists.stream()
     *                 .map(artist -> artist.getMembers().count())
     *                 .reduce(0L, Long::sum)
     *                 .intValue();
     *     }
     *
     *
     * （3）求值。
     *
     * 问：
     * 根据 Stream 方法的签名，判断其是惰性求值还是及早求值。
     * a. boolean anyMatch(Predicate<? super T> predicate);
     * b. Stream<T> limit(long maxSize);
     * 答：
     * a 是及早求值，b 是惰性求值。
     *
     *
     * （4）高阶函数。
     *
     * 问：
     * 下面的 Stream 函数是高阶函数吗？为什么？
     * a. boolean anyMatch(Predicate<? super T> predicate);
     * b. Stream<T> limit(long maxSize);
     * 答：
     * a 是，b 不是。区别在于参数或者返回值的类型中是否有函数式接口。
     *
     *
     * （5）纯函数。
     *
     * 问：
     * 下面的 Lambda 表达式有无副作用，或者说它们是否更改了程序状态？
     *
     * x -> x + 1
     *
     * 示例代码如下所示：
     *
     *         AtomicInteger count = new AtomicInteger(0);
     *         album.getMusicians()
     *                 .forEach(musician -> count.incrementAndGet());
     *
     * 注意：问的是传入 forEach 方法中的 Lambda 表达式。
     * 答：
     * 传入 forEach 方法中的 Lambda 表达式是无副作用的。
     *
     *
     * （6）
     * 问：
     * 计算一个字符串中小写字母的个数。
     * 答：
     *     private static int countLowercaseLetters(String string) {
     *         return (int) string.chars()
     *                 .filter(Character::isLowerCase)
     *                 .count();
     *     }
     *
     *
     * （7）
     * 问：
     * 在一个字符串列表中，找出包含最多小写字母的字符串。对于空列表，返回 Optional<String> 对象。
     * 答：
     *     private static Optional<String> mostLowercaseString(List<String> strings) {
     *         return strings.stream()
     *                 .max(Comparator.comparing(string -> countLowercaseLetters(string)));
     *     }
     *
     *
     * （8）
     * 问：
     * 只用 reduce 和 Lambda 表达式写出实现 Stream 上的 map 操作的代码，如果不想返回 Stream，可以返回一个 List。
     * 答：
     *     private static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
     *         return stream.reduce(new ArrayList<O>(), (acc, ele) -> {
     *             List<O> newAcc = new ArrayList<>();
     *             newAcc.add(mapper.apply(ele));
     *             return newAcc;
     *         }, (List<O> left, List<O> right) -> {
     *             List<O> newLeft = new ArrayList<>(left);
     *             newLeft.addAll(right);
     *             return newLeft;
     *         });
     *     }
     *
     *
     * （9）
     * 问：
     * 只用 reduce 和 Lambda 表达式写出实现 Stream 上的 filter 操作的代码，如果不想返回 Stream，可以返回一个 List。
     * 答：
     *     private static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
     *         List<I> initial = new ArrayList<>();
     *         return stream.reduce(initial, (List<I> acc, I ele) -> {
     *             if (predicate.test(ele)) {
     *                 List<I> newAcc = new ArrayList<>(acc);
     *                 newAcc.add(ele);
     *                 return newAcc;
     *             } else {
     *                 return acc;
     *             }
     *         }, (left, right) -> combineLists(left, right));
     *     }
     *
     *     private static <I> List<I> combineLists(List<I> left, List<I> right) {
     *         List<I> newLeft = new ArrayList<>(left);
     *         newLeft.addAll(right);
     *         return newLeft;
     *     }
     *
     */
    public static void main(String[] args) {
        testAddUpV1();
        testAddUpV2();
        testGetNamesAndOriginsV1();
        testGetAlbumsWithAtMostThreeTracksV1();

        List<Artist> artists = new ArrayList<>();
        // ...
        iterate(artists);
        convertToInternalIteration1st(artists);

        pureFunction();

        countLowercaseLetters("");
        mostLowercaseString(new ArrayList<>());

        map(Stream.empty(), x -> x);
        filter(Stream.empty(), x -> true);
    }

    private static void testAddUpV1() {
        int res = addUp(Stream.empty());
        Assert.assertEquals(0, res);
    }

    private static void testAddUpV2() {
        int res = addUp(Stream.of(1, 3, -2));
        Assert.assertEquals(2, res);
    }

    private static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (acc, ele) -> acc + ele);
    }

    private static void testGetNamesAndOriginsV1() {
        List<String> namesAndOrigins = getNamesAndOrigins(SampleData.getThreeArtists());
        Assert.assertEquals(Arrays.asList("John Coltrane", "US", "John Lennon", "UK", "The Beatles", "UK"), namesAndOrigins);
    }

    private static List<String> getNamesAndOrigins(List<Artist> artists) {
        return artists.stream()
                .flatMap(artist -> Stream.of(artist.getName(), artist.getNationality()))
                .collect(Collectors.toList());
    }

    private static void testGetAlbumsWithAtMostThreeTracksV1() {
        List<Album> input = Arrays.asList(SampleData.manyTrackAlbum, SampleData.sampleShortAlbum, SampleData.aLoveSupreme);
        List<Album> result = getAlbumsWithAtMostThreeTracks(input);
        Assert.assertEquals(Arrays.asList(SampleData.sampleShortAlbum, SampleData.aLoveSupreme), result);
    }

    private static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> albums) {
        return albums.stream()
                .filter(album -> album.getTrackList().size() <= 3)
                .collect(Collectors.toList());
    }

    private static int iterate(List<Artist> artists) {
        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();
        }
        return totalMembers;
    }

    private static int convertToInternalIteration1st(List<Artist> artists) {
        return (int) artists.stream()
                .flatMap(artist -> artist.getMembers())
                .count();
    }

    private static int convertToInternalIteration2nd(List<Artist> artists) {
        return artists.stream()
                .map(artist -> artist.getMembers().count())
                .reduce(0L, Long::sum)
                .intValue();
    }

    private static void pureFunction() {
        Album album = new Album("Test", new ArrayList<>(), new ArrayList<>());
        AtomicInteger count = new AtomicInteger(0);
        album.getMusicians()
                .forEach(musician -> count.incrementAndGet());
    }

    private static int countLowercaseLetters(String string) {
        return (int) string.chars()
                .filter(Character::isLowerCase)
                .count();
    }

    private static Optional<String> mostLowercaseString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparing(string -> countLowercaseLetters(string)));
    }

    private static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (acc, ele) -> {
            List<O> newAcc = new ArrayList<>();
            newAcc.add(mapper.apply(ele));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

    private static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial, (List<I> acc, I ele) -> {
            if (predicate.test(ele)) {
                List<I> newAcc = new ArrayList<>(acc);
                newAcc.add(ele);
                return newAcc;
            } else {
                return acc;
            }
        }, (left, right) -> combineLists(left, right));
    }

    private static <I> List<I> combineLists(List<I> left, List<I> right) {
        List<I> newLeft = new ArrayList<>(left);
        newLeft.addAll(right);
        return newLeft;
    }

}
