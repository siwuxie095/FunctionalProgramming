package com.siwuxie095.functional.chapter9th.example6th;

import com.siwuxie095.functional.common.Album;

/**
 * @author Jiajing Li
 * @date 2020-10-30 07:15:20
 */
@SuppressWarnings("all")
public interface AlbumLookup {

    Album lookupByName(String albumName);

}
