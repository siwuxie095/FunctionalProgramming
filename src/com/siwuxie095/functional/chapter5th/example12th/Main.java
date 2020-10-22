package com.siwuxie095.functional.chapter5th.example12th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiajing Li
 * @date 2020-10-22 07:55:56
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 一些细节
     *
     * Lambda 表达式的引入也推动了一些新方法被加入集合类。不妨来看看 Map 类的一些变化。
     *
     * 构建 Map 时，为给定键计算值是常用的操作之一，一个经典的例子就是实现一个缓存。传统的处理方式是先试着从 Map 中取值，
     * 如果没有取到，创建一个新值并返回。
     *
     * 假设使用 Map<String, Artist> artistCache 定义缓存，且需要使用费时的数据库操作查询艺术家信息，代码可能如下：
     *
     *     public Artist getArtist1st(String name) {
     *         Artist artist = artistCache.get(name);
     *         if (null == artist) {
     *             artist = readArtistFromDB(name);
     *             artistCache.put(name, artist);
     *         }
     *         return artist;
     *     }
     *
     * Java 8 引入了一个新方法 computeIfAbsent，该方法接受一个 Lambda 表达式，当值不存在时使用该 Lambda 表达式计算
     * 新值。使用该方法，可将上述代码重写为如下形式：
     *
     *     public Artist getArtist2nd(String name) {
     *         return artistCache.computeIfAbsent(name, this::readArtistFromDB);
     *     }
     *
     * 还有另外两种情况：
     * （1）希望在值存在时才计算，那么新增的 computeIfPresent 能处理该情况；
     * （2）希望无论值是否存在都计算，那么新增的 compute 能处理该情况。
     *
     * 在工作中，可能尝试过在 Map 上迭代。过去的做法是使用 values 方法返回一个值的集合，然后在集合上迭代。这样的代码不
     * 易读。比如统计每个艺术家专辑的数量，如下：
     *
     *         Map<Artist, Integer> countOfAlbums = new HashMap<>();
     *         for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
     *             Artist artist = entry.getKey();
     *             List<Album> albums = entry.getValue();
     *             countOfAlbums.put(artist, albums.size());
     *         }
     *
     * 谢天谢地，Java 8 为 Map 接口新增了一个 forEach 方法，该方法接受一个 BiConsumer 对象为参数（该对象接受两个参数，
     * 返回空），通过内部迭代编写出易于阅读的代码，如下：
     *
     *         Map<Artist, Integer> countOfAlbums = new HashMap<>();
     *         albumsByArtist.forEach((artist, albums) -> {
     *             countOfAlbums.put(artist, albums.size());
     *         });
     */
    public static void main(String[] args) {

    }

    public static final Map<String, Artist> artistCache = new HashMap<>();

    /**
     * 使用显式判断空值的方式缓存
     */
    public Artist getArtist1st(String name) {
        Artist artist = artistCache.get(name);
        if (null == artist) {
            artist = readArtistFromDB(name);
            artistCache.put(name, artist);
        }
        return artist;
    }

    /**
     * 费时的数据库操作
     */
    private Artist readArtistFromDB(String name) {
        // ...
        return new Artist(name, "");
    }

    /**
     * 使用 computeIfAbsent 缓存
     */
    public Artist getArtist2nd(String name) {
        return artistCache.computeIfAbsent(name, this::readArtistFromDB);
    }

    /**
     * 一种丑陋的迭代 Map 的方式
     */
    public void countByFor(Map<Artist, List<Album>> albumsByArtist) {
        Map<Artist, Integer> countOfAlbums = new HashMap<>();
        for(Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            Artist artist = entry.getKey();
            List<Album> albums = entry.getValue();
            countOfAlbums.put(artist, albums.size());
        }
    }

    /**
     * 使用内部迭代遍历 Map 里的值
     */
    public void countByForEach(Map<Artist, List<Album>> albumsByArtist) {
        Map<Artist, Integer> countOfAlbums = new HashMap<>();
        albumsByArtist.forEach((artist, albums) -> {
            countOfAlbums.put(artist, albums.size());
        });
    }

}
