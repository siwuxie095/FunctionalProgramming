package com.siwuxie095.functional.chapter4th.example12th;

import com.siwuxie095.functional.common.Artist;

import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:04:35
 */
@SuppressWarnings("all")
public class Artists {

    private List<Artist> artists;

    public Artists(List<Artist> artists) {
        this.artists = artists;
    }

    public Artist getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            indexException(index);
        }
        return artists.get(index);
    }

    private void indexException(int index) {
        throw new IllegalArgumentException(index + " doesn't correspond to an Artist");
    }

    public String getArtistName(int index) {
        try {
            Artist artist = getArtist(index);
            return artist.getName();
        } catch (IllegalArgumentException e) {
            return "unknown";
        }
    }

}
