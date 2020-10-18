package com.siwuxie095.functional.chapter4th.example12th;

import com.siwuxie095.functional.common.Artist;

import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-18 20:31:26
 */
@SuppressWarnings("all")
public interface Performance {

    public String getName();

    public Stream<Artist> getMusicians();

}
