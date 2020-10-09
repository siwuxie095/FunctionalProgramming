package com.siwuxie095.functional.common;

/**
 * @author Jiajing Li
 * @date 2020-10-10 04:39:40
 */
@SuppressWarnings("all")
public class Track {

    private String name;

    private int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Track copy() {
        return new Track(name, length);
    }

}
