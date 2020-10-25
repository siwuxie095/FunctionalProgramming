package com.siwuxie095.functional.chapter7th.example6th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-25 15:22:29
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 日志和打印消息
     *
     * 假设要在集合上进行大量操作，现在要调试代码，希望看到每一步操作的结果是什么。可以在每一步打印出集合中的值，
     * 这在流中很难做到，因为一些中间步骤是惰性求值的。
     *
     * 以找出专辑上每位艺术家来自哪个国家为例，命令式的写法如下：
     *
     *         Set<String> nationalities = new HashSet<>();
     *         for (Artist artist : album.getMusicianList()) {
     *             if (artist.getName().startsWith("The")) {
     *                 String nationality = artist.getNationality();
     *                 System.out.println("Found nationality: " + nationality);
     *                 nationalities.add(nationality);
     *             }
     *         }
     *
     * 其中，将找到的国家信息记录到日志中。
     *
     * 现在可以使用 forEach 方法打印出流中的值，这同时会触发求值过程。但是这样的操作有个缺点：无法再继续操作流
     * 了，流只能使用一次。如果还想继续，必须重新创建流。函数式的写法如下：
     *
     *         album.getMusicians()
     *                 .filter(artist -> artist.getName().startsWith("The"))
     *                 .map(artist -> artist.getNationality())
     *                 .forEach(nationality -> System.out.println("Found nationality: " + nationality));
     *         Set<String> nationalities
     *                 = album.getMusicians()
     *                 .filter(artist -> artist.getName().startsWith("The"))
     *                 .map(artist -> artist.getNationality())
     *                 .collect(Collectors.<String>toSet());
     *
     * 显然，这样的代码非常难看。
     */
    public static void main(String[] args) {

    }

    /**
     * 记录中间值，以便调试 for 循环
     */
    private static Set<String> findByFor(Album album) {
        Set<String> nationalities = new HashSet<>();
        for (Artist artist : album.getMusicianList()) {
            if (artist.getName().startsWith("The")) {
                String nationality = artist.getNationality();
                System.out.println("Found nationality: " + nationality);
                nationalities.add(nationality);
            }
        }
        return nationalities;
    }

    /**
     * 使用 forEach 记录中间值，这种方式有点幼稚
     */
    private static Set<String> findByStream(Album album) {
        album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .forEach(nationality -> System.out.println("Found nationality: " + nationality));
        Set<String> nationalities
                = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(Collectors.<String>toSet());
        return nationalities;
    }

}
