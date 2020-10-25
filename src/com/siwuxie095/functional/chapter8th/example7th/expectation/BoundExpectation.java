package com.siwuxie095.functional.chapter8th.example7th.expectation;

import org.junit.Assert;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:49:28
 */
@SuppressWarnings("all")
public class BoundExpectation {

    private final Object objectUnderTest;

    public BoundExpectation(Object value) {
        this.objectUnderTest = value;
    }

    public void isEqualTo(Object expected) {
        Assert.assertEquals(expected, objectUnderTest);
    }

}
