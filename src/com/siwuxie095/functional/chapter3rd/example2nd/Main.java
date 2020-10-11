package com.siwuxie095.functional.chapter3rd.example2nd;

import com.siwuxie095.functional.common.Artist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-11 18:51:01
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 从外部迭代到内部迭代
     *
     * Java 程序员在使用集合类时，一个通用的模式是在集合上进行迭代，然后处理返回的每一个元素。比如，要计算从英国来的
     * 艺术家的人数。通常代码会写成这样，即 使用 for 循环计算来自英国的艺术家人数：
     *
     *         int count = 0;
     *         for (Artist artist : allArtists) {
     *             if (artist.isFrom("England")) {
     *                 count++;
     *             }
     *         }
     *
     * 尽管这样的操作可行，但存在几个问题。每次迭代集合类时，都需要写很多样板代码。将 for 循环改造成并行方式运行也很
     * 麻烦，需要修改每个 for 循环才能实现。
     *
     * 此外，上述代码无法流畅传达程序员的意图。for 循环的样板代码模糊了代码的本意，程序员必须阅读整个循环体才能理解。
     * 若是单一的 for 循环，倒也问题不大，但面对一个满是循环（尤其是嵌套循环）的庞大代码库时，负担就重了。
     *
     * 就其背后的原理来看，for 循环其实是一个封装了迭代的语法糖，它的工作原理是：首先调用 iterator 方法，产生一个新
     * 的 Iterator 对象，进而控制整个迭代过程，这就是外部迭代。迭代过程通过显式调用 Iterator 对象的 hasNext 和
     * next 方法完成迭代。展开后的代码如下所示，即 使用迭代器计算来自英国的艺术家人数：
     *
     *         int count = 0;
     *         Iterator<Artist> iterator = allArtists.iterator();
     *         while (iterator.hasNext()) {
     *             Artist artist = iterator.next();
     *             if (artist.isFrom("England")) {
     *                 count++;
     *             }
     *         }
     *
     * 然而，外部迭代也有问题。首先，它很难抽象出不同操作（如：过滤、映射、最大值、最小值、计数等）。其次，它从本质上
     * 来讲是一种串行化操作。总体来看，使用 for 循环会将行为和方法混为一谈。
     *
     * 另一种方法就是内部迭代，即 通过 Stream 来进行操作。如下是使用内部迭代计算来自英国的艺术家人数：
     *
     *         long count = allArtists.stream()
     *                 .filter(artist -> artist.isFrom("England"))
     *                 .count();
     *
     * 注意：stream() 方法的调用和上面的 iterator() 方法的作用一样。只不过该方法不是返回一个控制迭代的 Iterator
     * 对象，而是返回内部迭代中的相应接口：Stream。
     * 
     * PS：Stream 是用函数式编程方式在集合类上进行复杂操作的工具。
     *
     * 上面的操作可以分解为两步更简单的操作：
     * （1）找出所有来自英国的艺术家；
     * （2）计算他们的人数。
     *
     * 每种操作都对应 Stream 接口的一个方法。为了找出来自英国的艺术家，需要对 Stream 对象进行过滤：filter。过滤在
     * 这里是指 "只保留通过某项测试的对象"。测试由一个函数完成，根据艺术家是否来自英国，该函数返回 true 或者 false。
     * 由于 Stream API 的函数式编程风格，这里并没有改变集合的内容，而是描述出 Stream 里的内容。count() 方法计算出
     * 给定 Stream 里包含多少个对象。
     */
    public static void main(String[] args) {
        List<Artist> allArtists = new ArrayList<>();
        // ...
        countByFor(allArtists);
        countByIterator(allArtists);
        countByStream(allArtists);
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

    private static int countByIterator(List<Artist> allArtists) {
        int count = 0;
        Iterator<Artist> iterator = allArtists.iterator();
        while (iterator.hasNext()) {
            Artist artist = iterator.next();
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

}
