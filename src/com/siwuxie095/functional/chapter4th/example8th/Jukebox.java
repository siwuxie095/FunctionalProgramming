package com.siwuxie095.functional.chapter4th.example8th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 18:35:40
 */
@SuppressWarnings("all")
public interface Jukebox {

    default String rock() {
        return "... all over the world!";
    }
}
