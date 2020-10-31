package com.siwuxie095.functional.chapter9th.example10th;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:47:08
 */
@SuppressWarnings("all")
public class BlockingArtistAnalyzerTest {

    private final BlockingArtistAnalyzer analyser = new BlockingArtistAnalyzer(new FakeLookupService()::lookupArtistName);

    @Test
    public void largerGroupsAreLarger() {
        Assert.assertTrue(analyser.isLargerGroup("The Beatles", "John Coltrane"));
    }

    @Test
    public void smallerGroupsArentLarger() {
        Assert.assertFalse(analyser.isLargerGroup("John Coltrane", "The Beatles"));
    }

}
