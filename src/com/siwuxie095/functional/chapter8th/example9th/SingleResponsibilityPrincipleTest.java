package com.siwuxie095.functional.chapter8th.example9th;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Jiajing Li
 * @date 2020-10-26 21:08:00
 */
@SuppressWarnings("all")
@RunWith(Parameterized.class)
public class SingleResponsibilityPrincipleTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{   {new SingleResponsibilityPrinciple.ImperativeRefactoredPrimeCounter()},
                                            {new SingleResponsibilityPrinciple.ImperativeSingleMethodPrimeCounter()},
                                            {new SingleResponsibilityPrinciple.FunctionalPrimeCounter()},
                                            {new SingleResponsibilityPrinciple.ParallelFunctionalPrimeCounter()}};
        return Arrays.asList(data);
    }

    private final SingleResponsibilityPrinciple.PrimeCounter primeCounter;

    public SingleResponsibilityPrincipleTest(SingleResponsibilityPrinciple.PrimeCounter primeCounter) {
        this.primeCounter = primeCounter;
    }

    @Test
    public void countsPrimesTo10() {
        Assert.assertEquals(5, primeCounter.countPrimes(10));
        Assert.assertEquals(9, primeCounter.countPrimes(20));
    }

    @Test
    public void countsPrimesTo20() {
        Assert.assertEquals(9, primeCounter.countPrimes(20));
    }

    @Test
    public void countsPrimesTo30() {
        Assert.assertEquals(11, primeCounter.countPrimes(30));
    }

}
