package com.siwuxie095.functional.chapter9th.example10th;

import com.siwuxie095.functional.common.Artist;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:57:53
 */
@SuppressWarnings("all")
public class CallbackArtistAnalyser implements ArtistAnalyzer {

    private final Function<String, Artist> artistLookupService;

    public CallbackArtistAnalyser(Function<String, Artist> artistLookupService) {
        this.artistLookupService = artistLookupService;
    }

    @Override
    public void isLargerGroup(String artistName, String otherArtistName, Consumer<Boolean> handler) {
        boolean isLarger = getNumberOfMembers(artistName) > getNumberOfMembers(otherArtistName);
        handler.accept(isLarger);
    }

    private long getNumberOfMembers(String artistName) {
        return artistLookupService.apply(artistName)
                .getMembers()
                .count();
    }

}

