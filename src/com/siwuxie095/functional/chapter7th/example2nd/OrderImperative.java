package com.siwuxie095.functional.chapter7th.example2nd;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Track;

import java.util.List;

/**
 * Order 类的命令式实现
 *
 * @author Jiajing Li
 * @date 2020-10-25 11:03:35
 */
@SuppressWarnings("all")
public class OrderImperative extends Order {

    public OrderImperative(List<Album> albums) {
        super(albums);
    }

    @Override
    public long countRunningTime() {
        long count = 0;
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                count += track.getLength();
            }
        }
        return count;
    }

    @Override
    public long countMusicians() {
        long count = 0;
        for (Album album : albums) {
            count += album.getMusicianList().size();
        }
        return count;
    }

    @Override
    public long countTracks() {
        long count = 0;
        for (Album album : albums) {
            count += album.getTrackList().size();
        }
        return count;
    }

}
