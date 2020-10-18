package com.siwuxie095.functional.chapter4th.example8th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 18:36:40
 */
@SuppressWarnings("all")
public interface Carriage {

    default String rock() {
        return "... from side to side!";
    }
}
