package com.siwuxie095.functional.chapter4th.example12th;

import com.siwuxie095.functional.common.Artist;

import java.util.List;
import java.util.Optional;

/**
 * @author Jiajing Li
 * @date 2020-10-18 21:08:22
 */
@SuppressWarnings("all")
public class ArtistsFixed {

    private List<Artist> artists;

    public ArtistsFixed(List<Artist> artists) {
        this.artists = artists;
    }

    public Optional<Artist> getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            return Optional.empty();
        }
        return Optional.of(artists.get(index));
    }

    public String getArtistName(int index) {
        Optional<Artist> artist = getArtist(index);
        return artist.map(Artist::getName)
                .orElse("unknown");
    }

}
