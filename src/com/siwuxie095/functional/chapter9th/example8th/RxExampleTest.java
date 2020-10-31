package com.siwuxie095.functional.chapter9th.example8th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:22:25
 */
@SuppressWarnings("all")
public class RxExampleTest {

    @Test
    public void filtersAlbums() throws InterruptedException {
        RxExample examples = new RxExample(SampleData.getThreeArtists());
        Artist artist = examples.search("John", "UK", 5)
                .toBlocking()
                .single();
        Assert.assertEquals(SampleData.johnLennon, artist);
    }

}
