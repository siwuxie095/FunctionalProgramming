package com.siwuxie095.functional.chapter5th.example13th;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:37:37
 */
@SuppressWarnings("all")
public class Fibonacci {

    private final Map<Integer,Long> cache;

    public Fibonacci() {
        cache = new HashMap<>();
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public long fibonacci(int x) {
        return cache.computeIfAbsent(x, n -> fibonacci(n - 1) + fibonacci(n - 2));
    }

}
