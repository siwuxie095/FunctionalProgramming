package com.siwuxie095.functional.chapter8th.example7th.expectation;

import java.util.Collection;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:48:56
 */
@SuppressWarnings("all")
public final class Expect {

    public BoundExpectation that(Object value) {
        return new BoundExpectation(value);
    }


    public CollectionExpectation that(Collection<?> collection) {
        return new CollectionExpectation(collection);
    }

}
