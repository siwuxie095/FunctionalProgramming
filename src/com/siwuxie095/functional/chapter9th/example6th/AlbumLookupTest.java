package com.siwuxie095.functional.chapter9th.example6th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import com.siwuxie095.functional.common.Track;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-30 08:30:02
 */
@SuppressWarnings("all")
public class AlbumLookupTest {

    interface AlbumLookupFactory extends BiFunction<List<Track>, List<Artist>, AlbumLookup> {

    }

    @Test
    public void albumLookedUp() {
        Album album = SampleData.aLoveSupreme;
        List<Track> trackList = album.getTrackList();
        List<Artist> musicianList = album.getMusicianList();

        AlbumLookupFactory completable = CompletableAlbumLookup::new;
        AlbumLookupFactory future = FutureAlbumLookup::new;

        Stream.of(completable, future)
                .forEach(factory -> {
                    AlbumLookup lookup = factory.apply(trackList, musicianList);
                    System.out.println("Testing: " + lookup.getClass().getSimpleName());
                    Album result = lookup.lookupByName(album.getName());

                    Assert.assertEquals(trackList, result.getTrackList());
                    Assert.assertEquals(musicianList, result.getMusicianList());
                });
    }

}
