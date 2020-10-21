package com.siwuxie095.functional.chapter5th.example8th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-21 08:18:26
 */
public class Main {

    /**
     * 使用收集器：字符串
     *
     * 很多时候，收集流中的数据都是为了在最后生成一个字符串。
     *
     * 假设想将参与制作一张专辑的所有艺术家的名字输出为一个格式化好的列表，以专辑 Let It Be 为例，期望的输出为：
     *
     * [John Lennon, Paul McCartney, George Harrison, Ringo Starr]
     *
     * 在Java 8还未发布前，可能会使用这种方式实现：通过不断迭代列表，使用一个 StringBuilder 对象来记录结果。
     * 每一步都取出一个艺术家的名字，追加到 StringBuilder 对象。如下：
     *
     *         StringBuilder builder = new StringBuilder("[");
     *         for (Artist artist : artists) {
     *             if (builder.length() > 1) {
     *                 builder.append(", ");
     *             }
     *             String name = artist.getName();
     *             builder.append(name);
     *         }
     *         builder.append("]");
     *         String result = builder.toString();
     *
     * 显然，这段代码不是非常好。如果不一步步跟踪，很难看出这段代码是干什么的。使用 Java 8 提供的流和收集器就能
     * 写出更清晰的代码，如下：
     *
     *         String result =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .collect(Collectors.joining(", ", "[", "]"));
     *
     * 这里使用 map 操作提取出艺术家的姓名，然后使用 Collectors.joining 收集流中的值，该方法可以方便地从一个
     * 流得到一个字符串，允许程序员提供分隔符（用以分隔元素）、前缀和后缀。
     */
    public static void main(String[] args) {
        formatByFor(SampleData.membersOfTheBeatles);
        formatByStream(SampleData.membersOfTheBeatles);
    }

    private static void formatByFor(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        for (Artist artist : artists) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            String name = artist.getName();
            builder.append(name);
        }
        builder.append("]");
        String result = builder.toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    private static void formatByStream(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .collect(Collectors.joining(", ", "[", "]"));
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

}
