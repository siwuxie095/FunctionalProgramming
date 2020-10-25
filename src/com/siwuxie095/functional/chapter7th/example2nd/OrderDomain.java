package com.siwuxie095.functional.chapter7th.example2nd;

import com.siwuxie095.functional.common.Album;

import java.util.List;
import java.util.function.ToLongFunction;

/**
 * 使用领域方法重构 Order 类
 *
 * @author Jiajing Li
 * @date 2020-10-25 11:24:17
 */
@SuppressWarnings("all")
public class OrderDomain extends Order {

    public OrderDomain(List<Album> albums) {
        super(albums);
    }

    public long countFeature(ToLongFunction<Album> function) {
        return albums.stream()
                .mapToLong(function)
                .sum();
    }

    @Override
    public long countRunningTime() {
        return countFeature(album -> album.getTracks()
                .mapToLong(track -> track.getLength())
                .sum());
    }

    @Override
    public long countMusicians() {
        return countFeature(album -> album.getMusicians().count());
    }

    @Override
    public long countTracks() {
        return countFeature(album -> album.getTracks().count());
    }

}
