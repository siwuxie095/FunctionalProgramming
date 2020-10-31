package com.siwuxie095.functional.chapter9th.example10th;

import java.util.function.Consumer;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:55:23
 */
@SuppressWarnings("all")
public interface ArtistAnalyzer {

    public void isLargerGroup(String artistName,
                              String otherArtistName,
                              Consumer<Boolean> handler);

}
