package com.siwuxie095.functional.chapter7th.example7th;

import com.siwuxie095.functional.common.Album;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-25 15:35:02
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 解决方案：peek
     *
     * 幸运的是，流有一个方法让你能查看每个值，同时能继续操作流。这就是 peek 方法。
     *
     * 以找出专辑上每位艺术家来自哪个国家为例，代码如下：
     *
     *         Set<String> nationalities
     *                 = album.getMusicians()
     *                 .filter(artist -> artist.getName().startsWith("The"))
     *                 .map(artist -> artist.getNationality())
     *                 .peek(nation -> System.out.println("Found nationality: " + nation))
     *                 .collect(Collectors.<String>toSet());
     *
     * 既能输出流中的值，同时避免了重复的流操作。
     *
     * 使用 peek 方法还能以同样的方式，将输出定向到现有的日志系统中，比如 log4j、java. util.logging 或者 slf4j。
     */
    public static void main(String[] args) {

    }

    /**
     * 使用 peek 方法记录中间值
     */
    private static Set<String> findByForEach(Album album) {
        Set<String> nationalities
                = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .peek(nation -> System.out.println("Found nationality: " + nation))
                .collect(Collectors.<String>toSet());
        return nationalities;
    }

}
