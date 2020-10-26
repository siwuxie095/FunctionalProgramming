package com.siwuxie095.functional.chapter8th.example9th;

import java.util.stream.IntStream;

/**
 * @author Jiajing Li
 * @date 2020-10-26 21:06:09
 */
@SuppressWarnings("all")
public class SingleResponsibilityPrinciple {

    public static interface PrimeCounter {
        long countPrimes(int upTo);
    }


    public static class ImperativeSingleMethodPrimeCounter implements PrimeCounter {
        /**
         * 计算质数个数，一个方法里塞进了多重职责
         */
        @Override
        public long countPrimes(int upTo) {
            long tally = 0;
            for (int i = 1; i < upTo; i++) {
                boolean isPrime = true;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                    }
                }
                if (isPrime) {
                    tally++;
                }
            }
            return tally;
        }
    }


    public static class ImperativeRefactoredPrimeCounter implements PrimeCounter {
        /**
         * 将 isPrime 重构成另外一个方法后，计算质数个数的方法
         */
        @Override
        public long countPrimes(int upTo) {
            long tally = 0;
            for (int i = 1; i < upTo; i++) {
                if (isPrime(i)) {
                    tally++;
                }
            }
            return tally;
        }

        private boolean isPrime(int number) {
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }


    public static class FunctionalPrimeCounter implements PrimeCounter {
        /**
         * 使用集合流重构质数计数程序
         */
        @Override
        public long countPrimes(int upTo) {
            return IntStream.range(1, upTo)
                    .filter(this::isPrime)
                    .count();
        }

        private boolean isPrime(int number) {
            return IntStream.range(2, number)
                    .allMatch(x -> (number % x) != 0);
        }
    }


    public static class ParallelFunctionalPrimeCounter implements PrimeCounter {
        /**
         * 并行运行基于集合流的质数计数程序
         */
        @Override
        public long countPrimes(int upTo) {
            return IntStream.range(1, upTo)
                    .parallel()
                    .filter(this::isPrime)
                    .count();
        }

        private boolean isPrime(int number) {
            return IntStream.range(2, number)
                    .allMatch(x -> (number % x) != 0);
        }
    }


}
