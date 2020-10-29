package com.siwuxie095.functional.chapter9th.example6th;

/**
 * @author Jiajing Li
 * @date 2020-10-30 07:16:20
 */
@SuppressWarnings("all")
public class AlbumLookupException extends RuntimeException {

    public AlbumLookupException(Throwable cause) {
        super(cause);
    }

    public AlbumLookupException(String message) {
        super(message);
    }

}
