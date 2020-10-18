package com.siwuxie095.functional.chapter4th.example12th;

import com.siwuxie095.functional.common.Artist;

import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-18 20:34:37
 */
@SuppressWarnings("all")
public interface PerformanceFixed {

    public String getName();

    public Stream<Artist> getMusicians();

    public default Stream<Artist> getAllMusicians() {
        return getMusicians()
                .flatMap(artist -> Stream.concat(Stream.of(artist), artist.getMembers()));
    }

}
