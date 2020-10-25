package com.siwuxie095.functional.chapter8th.example3rd;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-25 18:34:31
 */
public class MockEditor implements Editor {

    private final List<String> actions = new ArrayList<>();

    @Override
    public void save() {
        actions.add("save");
    }

    @Override
    public void open() {
        actions.add("open");
    }

    @Override
    public void close() {
        actions.add("close");
    }

    public void check() {
        Assert.assertEquals("open", actions.get(0));
        Assert.assertEquals("save", actions.get(1));
        Assert.assertEquals("close", actions.get(2));
    }

}
