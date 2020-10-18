package com.siwuxie095.functional.chapter4th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:46:24
 */
@SuppressWarnings("all")
public interface Parent {

    void message(String body);

    default void welcome() {
        message("Parent: Hi!");
    }

    String getLastMessage();

}
