package com.siwuxie095.functional.chapter9th.example10th;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jiajing Li
 * @date 2020-10-31 10:00:15
 */
@SuppressWarnings("all")
@RunWith(Parameterized.class)
public class ArtistAnalyzerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        FakeLookupService lookupService = new FakeLookupService();
        Object[][] data = new Object[][] {
                { new CallbackArtistAnalyser(lookupService::lookupArtistName) },
                { new CompletableFutureArtistAnalyser(lookupService::lookupArtistName) },
        };
        return Arrays.asList(data);
    }

    private final ArtistAnalyzer analyser;

    public ArtistAnalyzerTest(ArtistAnalyzer analyser) {
        this.analyser = analyser;
    }

    @Test
    public void largerGroupsAreLarger() {
        assertLargerGroup(true, "The Beatles", "John Coltrane");
    }

    @Test
    public void smallerGroupsArentLarger() {
        assertLargerGroup(false, "John Coltrane", "The Beatles");
    }

    private void assertLargerGroup(boolean expected, String artistName, String otherArtistName) {
        AtomicBoolean isLarger = new AtomicBoolean(!expected);
        analyser.isLargerGroup(artistName, otherArtistName, isLarger::set);
        Assert.assertEquals(expected, isLarger.get());
    }

}

