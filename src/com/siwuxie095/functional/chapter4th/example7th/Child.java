package com.siwuxie095.functional.chapter4th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:51:43
 */
@SuppressWarnings("all")
public interface Child extends Parent {

    @Override
    default void welcome() {
        message("Child: Hi!");
    }
}
