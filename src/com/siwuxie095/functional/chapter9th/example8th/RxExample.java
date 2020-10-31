package com.siwuxie095.functional.chapter9th.example8th;

import com.siwuxie095.functional.common.Artist;
import rx.Observable;
import rx.Observer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-31 08:25:34
 */
@SuppressWarnings("all")
public class RxExample {

    private final List<Artist> savedArtists;
    private final List<String> savedArtistNames;

    public RxExample(List<Artist> savedArtists) {
        this.savedArtists = savedArtists;
        savedArtistNames = savedArtists.stream()
                .map(Artist::getName)
                .collect(Collectors.toList());
    }

    public Observable<Artist> search(String searchedName,
                                     String searchedNationality,
                                     int maxResults) {
        return getSavedArtistNames()  // <1>
                .filter(name -> name.contains(searchedName)) // <2>
                .flatMap(this::lookupArtist) // <3>
                .filter(artist -> artist.getNationality() // <4>
                        .contains(searchedNationality))
                .take(maxResults); // <5>
    }

    //  ------------------ FAKE LOOKUP CODE ------------------
    //          Again, imaginary external web services

    private Observable<String> getSavedArtistNames() {
        return Observable.from(savedArtistNames);
    }

    private Observable<Artist> lookupArtist(String name) {
        Artist required = savedArtists.stream()
                .filter(artist -> artist.getName().equals(name))
                .findFirst()
                .get();
        return Observable.from(new Artist[]{required});
    }

    // Purely for imported code sample
    public void creationCodeSample() {
        Observer<String> observer = null;

        // 给 Observable 对象传值，并且完成它
        // BEGIN completing_observable
        observer.onNext("a");
        observer.onNext("b");
        observer.onNext("c");
        observer.onCompleted();
        // END completing_observable

        // 通知 Observable 对象有错误发生
        // BEGIN error_observable
        observer.onError(new Exception());
        // END error_observable
    }

}
