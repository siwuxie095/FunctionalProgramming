package com.siwuxie095.functional.chapter8th.example7th;

import com.siwuxie095.functional.chapter8th.example7th.expectation.Expect;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:45:45
 */
@SuppressWarnings("all")
public final class Description {

    private final String suite;

    Description(String suite) {
        this.suite = suite;
    }

    public void should(String description, Specification specification) {
        try {
            Expect expect = new Expect();
            specification.specifyBehaviour(expect);
            Runner.current.recordSuccess(suite, description);
        } catch (AssertionError cause) {
            Runner.current.recordFailure(suite, description, cause);
        } catch (Throwable cause) {
            Runner.current.recordError(suite, description, cause);
        }
    }

}
