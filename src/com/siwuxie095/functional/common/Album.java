package com.siwuxie095.functional.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Jiajing Li
 * @date 2020-10-10 04:43:38
 */
@SuppressWarnings("all")
public class Album {

    private String name;

    private List<Track> tracks;

    private List<Artist> musicians;

    public Album(String name, List<Track> tracks, List<Artist> musicians) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(tracks);
        Objects.requireNonNull(musicians);
        this.name = name;
        this.tracks = new ArrayList<>(tracks);
        this.musicians = new ArrayList<>(musicians);
    }

    public String getName() {
        return name;
    }

    public Stream<Track> getTracks() {
        return tracks.stream();
    }

    public Stream<Artist> getMusicians() {
        return musicians.stream();
    }

    /**
     * 用于命令式代码中遍历 List
     */
    public List<Track> getTrackList() {
        return Collections.unmodifiableList(tracks);
    }

    /**
     * 用于命令式代码中遍历 List
     */
    public List<Artist> getMusicianList() {
        return Collections.unmodifiableList(musicians);
    }

    public Artist getMainMusician() {
        return musicians.get(0);
    }

    public Album copy() {
        List<Track> tracks = getTracks().map(Track::copy).collect(toList());
        List<Artist> musicians = getMusicians().map(Artist::copy).collect(toList());
        return new Album(name, tracks, musicians);
    }

}
