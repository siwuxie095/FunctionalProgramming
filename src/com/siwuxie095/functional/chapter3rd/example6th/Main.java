package com.siwuxie095.functional.chapter3rd.example6th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-13 22:41:16
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 多次调用流操作
     *
     * 程序员也可以选择每一步强制对函数求值，而不是将所有的方法调用链接在一起，但是，最好不要如此操作。
     *
     * 如下代码展示了如何用如上述不建议的编码风格来找出专辑上所有演出乐队的国籍，即 分开调用:
     *
     *         List<Artist> musicians = album.getMusicians()
     *                 .collect(Collectors.toList());
     *
     *         List<Artist> bands = musicians.stream()
     *                 .filter(artist -> album.getName().startsWith("The"))
     *                 .collect(Collectors.toList());
     *
     *         Set<String> nationalities = bands.stream()
     *                 .map(artist -> artist.getNationality())
     *                 .collect(Collectors.toSet());
     *
     * PS：默认乐队的名称以 The 开头。
     *
     * 作为对比，如下代码是符合 Stream 使用习惯的链式调用：
     *
     *         album.getMusicians()
     *                 .filter(artist -> album.getName().startsWith("The"))
     *                 .map(artist -> artist.getNationality())
     *                 .collect(Collectors.toSet());
     *
     * 第一段代码和第二段代码相比有如下缺点：
     * 􏰀（1）代码可读性差，样板代码太多，隐藏了真正的业务逻辑；
     * 􏰀（2） 效率差，每一步都要对流及早求值，生成新的集合；
     * 􏰀（3）代码充斥一堆垃圾变量，它们只用来保存中间结果，除此之外毫无用处；
     * （4）难于自动并行化处理。
     *
     * 当然，刚开始写基于流的程序时，这样的情况在所难免。但是如果发现经常写出这样的代码，就要反思能否
     * 将代码重构得更加简洁易读。
     *
     * PS：要慢慢习惯 Stream API 的链式操作。不能以不习惯为由，随意拆开链式操作。
     */
    public static void main(String[] args) {
        Album album = new Album("Test", new ArrayList<>(), new ArrayList<>());
        notSuggest(album);
        suggest(album);
    }

    /**
     * 流的分开调用（不建议）
     */
    private static void notSuggest(Album album) {
        List<Artist> musicians = album.getMusicians()
                .collect(Collectors.toList());

        List<Artist> bands = musicians.stream()
                .filter(artist -> album.getName().startsWith("The"))
                .collect(Collectors.toList());

        Set<String> nationalities = bands.stream()
                .map(artist -> artist.getNationality())
                .collect(Collectors.toSet());
    }

    /**
     * 流的链式调用（建议）
     */
    private static void suggest(Album album) {
        album.getMusicians()
                .filter(artist -> album.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(Collectors.toSet());
    }

}
