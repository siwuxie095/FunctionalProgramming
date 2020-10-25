package com.siwuxie095.functional.chapter7th.example2nd;

import com.siwuxie095.functional.common.Album;

import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-25 11:02:34
 */
@SuppressWarnings("all")
public abstract class Order {

    protected final List<Album> albums;

    public Order(List<Album> albums) {
        this.albums = albums;
    }

    public abstract long countRunningTime();

    public abstract long countMusicians();

    public abstract long countTracks();

}
