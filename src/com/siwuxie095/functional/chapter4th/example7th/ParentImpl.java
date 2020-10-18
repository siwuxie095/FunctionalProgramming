package com.siwuxie095.functional.chapter4th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:47:40
 */
@SuppressWarnings("all")
public class ParentImpl implements Parent {

    private String body;

    @Override
    public void message(String body) {
        this.body = body;
    }

    @Override
    public String getLastMessage() {
        return body;
    }
}
