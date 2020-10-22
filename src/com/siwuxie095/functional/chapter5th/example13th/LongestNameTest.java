package com.siwuxie095.functional.chapter5th.example13th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:13:01
 */
@SuppressWarnings("all")
public class LongestNameTest {

    @Test
    public void findsLongestNameByCollecting() {
        Artist artist = LongestName.byCollecting(SampleData.getThreeArtists());
        Assert.assertEquals(SampleData.johnColtrane, artist);
    }

    @Test
    public void findsLongestNameByReduce() {
        Artist artist = LongestName.byReduce(SampleData.getThreeArtists());
        Assert.assertEquals(SampleData.johnColtrane, artist);
    }

}
