package com.siwuxie095.functional.chapter8th.example7th.expectation;

import org.junit.Assert;

import java.util.Collection;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:50:06
 */
@SuppressWarnings("all")
public class CollectionExpectation extends BoundExpectation {

    private final Collection<?> objectUnderTest;

    public CollectionExpectation(Collection<?> objectUnderTest) {
        super(objectUnderTest);
        this.objectUnderTest = objectUnderTest;
    }

    public void isEmpty() {
        Assert.assertTrue(objectUnderTest.isEmpty());
    }

}
