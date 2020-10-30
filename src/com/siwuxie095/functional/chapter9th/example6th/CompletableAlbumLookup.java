package com.siwuxie095.functional.chapter9th.example6th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.Track;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiajing Li
 * @date 2020-10-30 07:52:27
 */
@SuppressWarnings("all")
public class CompletableAlbumLookup implements AlbumLookup {

    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(4);

    private final List<Track> tracks;
    private final List<Artist> artists;

    public CompletableAlbumLookup(List<Track> tracks, List<Artist> artists) {
        this.tracks = tracks;
        this.artists = artists;
    }

    @Override
    public Album lookupByName(String albumName) {
        CompletableFuture<List<Artist>> artistLookup
                = loginTo("artist")
                .thenCompose(artistLogin -> lookupArtists(albumName, artistLogin));  // <1>

        return loginTo("track")
                .thenCompose(trackLogin -> lookupTracks(albumName, trackLogin)) // <2>
                .thenCombine(artistLookup, (tracks, artists)
                        -> new Album(albumName, tracks, artists)) // <3>
                .join(); // <4>
    }

    // Variables Exists to satisfy code sample below
    private Track track;
    private Artist artist;

    CompletableFuture<Track> lookupTrack(String id) {
        return CompletableFuture.supplyAsync(() -> {
            // Some expensive work is done here <1>
            // ...
            return track; // <2>
        }, SERVICE); // <3>
    }

    CompletableFuture<Artist> createFuture(String id) {
        CompletableFuture<Artist> future = new CompletableFuture<>();
        startJob(future);
        return future;
    }

    private void startJob(CompletableFuture<Artist> future) {
        future.complete(artist);
    }

    private void processExceptionally(CompletableFuture<Album> future, String name) {
        future.completeExceptionally(new AlbumLookupException("Unable to find " + name));
    }

    // ----------------- FAKE LOOKUP METHODS -----------------
    //         Represent API lookup on external services

    private CompletableFuture<List<Artist>> lookupArtists(String albumName, Credentials credentials) {
        return CompletableFuture.completedFuture(artists);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<List<Track>> lookupTracks(String albumName, Credentials credentials) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return tracks;
        }, SERVICE);
    }

    private CompletableFuture<Credentials> loginTo(String serviceName) {
        return CompletableFuture.supplyAsync(() -> {
            if ("artist".equals(serviceName)) {
                sleep(1000);
            }
            return new Credentials();
        }, SERVICE);
    }

}

