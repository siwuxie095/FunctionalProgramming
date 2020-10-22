package com.siwuxie095.functional.chapter5th.example13th;

import com.siwuxie095.functional.common.Artist;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:08:26
 */
@SuppressWarnings("all")
public class LongestName {

    public static Artist byCollecting(List<Artist> artists) {
        return artists.stream()
                .collect(Collectors.maxBy(byNameLength))
                .orElseThrow(RuntimeException::new);
    }


    private static Comparator<Artist> byNameLength
            = Comparator.comparing(artist -> artist.getName().length());

    public static Artist byReduce(List<Artist> artists) {
        return artists.stream()
                .reduce((acc, artist) -> {
                    return (byNameLength.compare(acc, artist) >= 0) ? acc : artist;
                })
                .orElseThrow(RuntimeException::new);
    }

}
