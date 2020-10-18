package com.siwuxie095.functional.chapter4th.example8th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 18:37:25
 */
@SuppressWarnings("all")
public class MusicalCarriage implements Carriage, Jukebox {

    @Override
    public String rock() {
        return Carriage.super.rock();
    }
}
