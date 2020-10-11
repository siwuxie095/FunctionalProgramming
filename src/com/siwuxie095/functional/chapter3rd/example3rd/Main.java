package com.siwuxie095.functional.chapter3rd.example3rd;

import com.siwuxie095.functional.common.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-11 21:11:28
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 流的实现机制
     *
     * 下面将以如下两个示例来探讨流 Stream 的实现机制。
     *
     * （1）如下是使用 for 循环（外部迭代）计算来自英国的艺术家人数：
     *
     *         int count = 0;
     *         for (Artist artist : allArtists) {
     *             if (artist.isFrom("England")) {
     *                 count++;
     *             }
     *         }
     *
     * （2）如下是使用内部迭代计算来自英国的艺术家人数：
     *
     *         long count = allArtists.stream()
     *                 .filter(artist -> artist.isFrom("England"))
     *                 .count();
     *
     * 在示例（2）中整个过程被分解为两种更简单的操作：过滤和计数，看似有化简为繁之嫌。而在示例（1）中只含
     * 一个 for 循环。那么两种操作是否意味着需要两次循环？事实上，Stream 的类库设计很精妙，只需对艺术家
     * 列表迭代一次即可。
     *
     * 通常，在 Java 中调用一个方法，计算机会随即执行操作。比如：
     *
     * System.out.println("Hello World");
     *
     * 会立即在终端（或者控制台）上输出一条信息。
     *
     * Stream 里的一些方法却略有不同，它们虽是普通的 Java 方法，但返回的 Stream 对象却不是一个新集合，
     * 而是创建新集合的配方。
     *
     * 那所谓的配方是什么呢？看如下代码，即 只过滤，不计数：
     *
     *         allArtists.stream()
     *                 .filter(artist -> artist.isFrom("England"));
     *
     * 这行代码并未做什么实际性的工作，filter 只刻画出了 Stream，但没有产生新的集合。像 filter 这样只
     * 描述 Stream，最终不产生新集合的方法叫作惰性求值方法；而像 count 这样最终会从 Stream 产生值的方
     * 法叫作及早求值方法。
     *
     * 关于惰性求值方法和及早求值方法：
     * （1）惰性求值方法，也即 中间操作（Intermediate Operation）
     * （2）及早求值方法，也即 终结操作（Terminal Operation）
     *
     * （另外：终结操作有时也被称为最终操作、终止操作、终端操作）
     *
     * 如果在过滤器中加入一条 println 语句，来输出艺术家的名字，就能轻而易举地看出其中的不同。如下：
     *
     *         allArtists.stream()
     *                 .filter(artist -> {
     *                     System.out.println(artist.getName());
     *                     return artist.isFrom("England");
     *                 });
     *
     * 由于使用了惰性求值，运行这段代码，程序不会输出任何信息！
     *
     * 但如果将同样的输出语句加入一个拥有终结操作的流，如下：
     *
     *         long count = allArtists.stream()
     *                 .filter(artist -> {
     *                     System.out.println(artist.getName());
     *                     return artist.isFrom("England");
     *                 })
     *                 .count();
     *
     * 此时，艺术家的名字就会被输出。
     *
     * 判断一个操作是惰性求值还是及早求值很简单：只需看它的返回值。
     * （1）如果返回值是 Stream，那么这个操作就是惰性求值；
     * （2）如果返回值是另一个值或为空，那么这个操作就是及早求值。
     *
     * 使用这些操作的理想方式就是形成一个惰性求值的链，最后用一个及早求值的操作返回想要的结果，这正是它的
     * 合理之处。上面计数的示例也是这样运行的，但这只是最简单的情况：只含两步操作。
     *
     * 整个过程和建造者模式（也称生成器模式）有共通之处。建造者模式使用一系列操作设置属性和配置，最后调用
     * 一个 build 方法。这时，对象才被真正创建。
     *
     * 有人可能会问为什么要区分惰性求值和及早求值呢？因为只有在对需要什么样的结果和操作，有了更多了解之后，
     * 才能更有效率地进行计算。比如，如果要找出大于 10 的第一个数字，那么并不需要和所有元素去做比较，只要
     * 找出第一个匹配的元素就够了。这也意味着可以在集合类上级联多种操作（即 同时使用若干中间操作加一个终结
     * 操作），但迭代只需一次。
     *
     * PS：中间操作和终结操作列举：
     * （1）中间操作包含这些：map(mapToInt, flatMap 等)、filter、distinct、sorted、peek、limit、
     * skip、parallel、sequential、unordered。
     *
     * （2）终结操作包含这些：forEach、forEachOrdered、toArray、reduce、collect、min、max、count、
     * anyMatch、allMatch、noneMatch、findFirst、findAny、iterator。
     *
     * 另外，终结操作中的一部分可以细分为短路操作（Short-circuiting Operation），包含这些：anyMatch、
     * allMatch、noneMatch、findFirst、findAny、limit。
     */
    public static void main(String[] args) {
        List<Artist> allArtists = new ArrayList<>();
        Artist artist = new Artist("Jack", "England");
        allArtists.add(artist);
        // ...
        countByFor(allArtists);
        countByStream(allArtists);
        onlyFilter(allArtists);
        printAndFilter(allArtists);
        printAndCount(allArtists);
    }

    private static int countByFor(List<Artist> allArtists) {
        int count = 0;
        for (Artist artist : allArtists) {
            if (artist.isFrom("England")) {
                count++;
            }
        }
        return count;
    }

    private static void countByStream(List<Artist> allArtists) {
        long count = allArtists.stream()
                .filter(artist -> artist.isFrom("England"))
                .count();
    }

    private static void onlyFilter(List<Artist> allArtists) {
        allArtists.stream()
                .filter(artist -> artist.isFrom("England"));
    }

    private static void printAndFilter(List<Artist> allArtists) {
        allArtists.stream()
                .filter(artist -> {
                    System.out.println(artist.getName());
                    return artist.isFrom("England");
                });
    }

    private static void printAndCount(List<Artist> allArtists) {
        long count = allArtists.stream()
                .filter(artist -> {
                    System.out.println(artist.getName());
                    return artist.isFrom("England");
                })
                .count();
    }

}
