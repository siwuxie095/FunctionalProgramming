package com.siwuxie095.functional.chapter3rd.example5th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Track;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-13 08:30:25
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 重构遗留代码
     *
     * 为了进一步阐释如何重构遗留代码，这里将举例说明如何将一段使用循环进行集合操作的代码，重构成基于 Stream 的操作。
     * 重构过程中的每一步都能确保代码通过单元测试证。
     *
     * 假定选定一组专辑，找出其中所有长度大于 1 分钟的曲目名称。如下是遗留代码：
     *
     *     private static Set<String> findLongTracks(List<Album> albums) {
     *         Set<String> trackNames = new HashSet<>();
     *         for (Album album : albums) {
     *             for (Track track : album.getTrackList()) {
     *                 if (track.getLength() > 60) {
     *                     String name = track.getName();
     *                     trackNames.add(name);
     *                 }
     *             }
     *         }
     *         return trackNames;
     *     }
     *
     * 首先 初始化一个 Set 对象，用来保存找到的曲目名称。然后使用 for 循环遍历所有专辑，每次循环中再使用一个 for 循
     * 环遍历每张专辑上的每首曲目，检查其长度是否大于 60 秒，如果是，则将该曲目名称加入 Set 对象。
     *
     * 不难发现，上面的代码中存在好几组嵌套的循环。仅通过阅读这段代码很难看出它的编写目的，那就来重构一下。
     *
     * 使用流来重构这段代码的方式很多，下面介绍的只是其中一种。事实上，对 Stream API 越熟悉，就越不需要细分步骤。之所
     * 以在示例中一步一步地重构，完全为了弄清楚重构的过程，在工作中无需这样做。
     *
     * 第一步要修改的是 for 循环。首先使用 Stream 的 forEach 方法替换掉 for 循环，但还是暂时保留原来循环体中的代码，
     * 这是在重构时非常方便的一个技巧。调用 stream 方法从专辑列表中生成第一个 Stream，注意，getTracks 方法本身就返
     * 回一个 Stream 对象。如下：
     *
     *     private static Set<String> findLongTracks1st(List<Album> albums) {
     *         Set<String> trackNames = new HashSet<>();
     *         albums.stream().
     *                 forEach(album -> {
     *                     album.getTracks()
     *                             .forEach(track -> {
     *                                 if (track.getLength() > 60) {
     *                                     String name = track.getName();
     *                                     trackNames.add(name);
     *                                 }
     *                             });
     *                 });
     *         return trackNames;
     *     }
     *
     * 在重构的第一步中，虽然使用了流，但是并没有充分发挥它的作用。事实上，重构后的代码还不如原来的代码好。因此，下一步
     * 就应该引入一些更符合流风格的代码了，最内层的 forEach 方法正是主要突破口。
     *
     * 最内层的 forEach 方法有三个功用：
     * （1）找出长度大于 1 分钟的曲目；
     * （2）得到符合条件的曲目名称；
     * （3）将曲目名称加入集合 Set。
     *
     * 这就意味着需要三项 Stream 操作：
     * （1）找出满足某种条件的曲 目是 filter 的功能；
     * （2）得到曲目名称则可用 map 达成；
     * （3）终结操作可使用 forEach 方法将曲目名称加入一个集合。
     *
     * 用以上三项 Stream 操作可以将内部的 forEach 方法拆分。如下：
     *
     *     private static Set<String> findLongTracks2nd(List<Album> albums) {
     *         Set<String> trackNames = new HashSet<>();
     *         albums.stream().
     *                 forEach(album -> {
     *                     album.getTracks()
     *                             .filter(track -> track.getLength() > 60)
     *                             .map(track -> track.getName())
     *                             .forEach(name -> {
     *                                 trackNames.add(name);
     *                             });
     *                 });
     *         return trackNames;
     *     }
     *
     * 现在用更符合流风格的操作替换了内层的循环，但代码看起来还是冗长繁琐。将各种流嵌套起来并不理想，最好还是用干净整洁
     * 的顺序调用一些方法。
     *
     * 理想的操作莫过于找到一种方法，将专辑转化成一个曲目的 Stream。众所周知，任何时候想转化或替代代码，都该使用 map
     * 操作。这里将使用比 map 更复杂的 flatMap 操作，把多个 Stream 合并成一个 Stream 并返回。
     *
     * 所以应该将 forEach 方法替换成 flatMap，如下：
     *
     *     private static Set<String> findLongTracks3rd(List<Album> albums) {
     *         Set<String> trackNames = new HashSet<>();
     *         albums.stream()
     *                 .flatMap(album -> album.getTracks())
     *                 .filter(track -> track.getLength() > 60)
     *                 .map(track -> track.getName())
     *                 .forEach(name -> {
     *                     trackNames.add(name);
     *                 });
     *         return trackNames;
     *     }
     *
     * 上面的代码中使用一组简洁的方法调用替换掉两个嵌套的 for 循环，看起来清晰很多。然而至此并未结束，仍需手动创建一个
     * Set 对象并将元素加入其中，但这里希望看到的是整个计算任务由一连串的 Stream 操作完成。
     *
     * 实际上，Stream 中已经有类似的操作。就像使用 collect(Collectors. toList()) 可以将 Stream 中的值转换成一个
     * 列表，使用 collect(Collectors.toSet()) 可以将 Stream 中的值转换成一个集合。
     *
     * 因此，将最后的 forEach 方法替换为 collect，并删掉变量 trackNames，如下：
     *
     *     private static Set<String> findLongTracks4th(List<Album> albums) {
     *         return albums.stream()
     *                 .flatMap(album -> album.getTracks())
     *                 .filter(track -> track.getLength() > 60)
     *                 .map(track -> track.getName())
     *                 .collect(Collectors.toSet());
     *     }
     *
     * 简而言之，选取一段遗留代码进行重构，转换成使用流风格的代码。最初只是简单地使用流，但没有引入任何有用的流操作。随
     * 后通过一系列重构，最终使代码更符合使用流的风格。
     */
    public static void main(String[] args) {
        List<Album> albums = new ArrayList<>();
        // ...
        findLongTracks(albums);
        findLongTracks1st(albums);
        findLongTracks2nd(albums);
        findLongTracks3rd(albums);
        findLongTracks4th(albums);
    }

    private static Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    private static Set<String> findLongTracks1st(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream().
                forEach(album -> {
                    album.getTracks()
                            .forEach(track -> {
                                if (track.getLength() > 60) {
                                    String name = track.getName();
                                    trackNames.add(name);
                                }
                            });
                });
        return trackNames;
    }

    private static Set<String> findLongTracks2nd(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream().
                forEach(album -> {
                    album.getTracks()
                            .filter(track -> track.getLength() > 60)
                            .map(track -> track.getName())
                            .forEach(name -> {
                                trackNames.add(name);
                            });
                });
        return trackNames;
    }

    private static Set<String> findLongTracks3rd(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream()
                .flatMap(album -> album.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .forEach(name -> {
                    trackNames.add(name);
                });
        return trackNames;
    }

    private static Set<String> findLongTracks4th(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(Collectors.toSet());
    }

}
