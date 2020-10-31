package com.siwuxie095.functional.chapter9th.example10th;

import com.siwuxie095.functional.common.Artist;

import java.util.function.Function;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:46:45
 */
@SuppressWarnings("all")
public class BlockingArtistAnalyzer {

    private final Function<String, Artist> artistLookupService;

    public BlockingArtistAnalyzer(Function<String, Artist> artistLookupService) {
        this.artistLookupService = artistLookupService;
    }

    public boolean isLargerGroup(String artistName, String otherArtistName) {
        return getNumberOfMembers(artistName) > getNumberOfMembers(otherArtistName);
    }

    private long getNumberOfMembers(String artistName) {
        return artistLookupService.apply(artistName)
                .getMembers()
                .count();
    }

}
