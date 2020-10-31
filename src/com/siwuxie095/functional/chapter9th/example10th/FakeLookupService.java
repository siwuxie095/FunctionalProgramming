package com.siwuxie095.functional.chapter9th.example10th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:52:28
 */
@SuppressWarnings("all")
public class FakeLookupService {

    public Artist lookupArtistName(String name) {
        sleepToSimulateLookupLatency();
        switch (name) {
            case "The Beatles":
                return SampleData.theBeatles;
            case "John Coltrane":
                return SampleData.johnColtrane;
            default:
                throw new IllegalArgumentException("Unknown artist: " + name);
        }
    }

    private void sleepToSimulateLookupLatency() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
