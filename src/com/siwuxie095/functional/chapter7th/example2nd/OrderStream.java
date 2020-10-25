package com.siwuxie095.functional.chapter7th.example2nd;

import com.siwuxie095.functional.common.Album;

import java.util.List;

/**
 * 使用流重构命令式的 Order 类
 *
 * @author Jiajing Li
 * @date 2020-10-25 11:08:15
 */
@SuppressWarnings("all")
public class OrderStream extends Order {

    public OrderStream(List<Album> albums) {
        super(albums);
    }

    @Override
    public long countRunningTime() {
        return albums.stream()
                .mapToLong(album -> album.getTracks()
                        .mapToLong(track -> track.getLength())
                        .sum())
                .sum();
    }

    @Override
    public long countMusicians() {
        return albums.stream()
                .mapToLong(album -> album.getMusicians().count())
                .sum();
    }

    @Override
    public long countTracks() {
        return albums.stream()
                .mapToLong(album -> album.getTracks().count())
                .sum();
    }

}
