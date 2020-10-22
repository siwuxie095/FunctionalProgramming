package com.siwuxie095.functional.chapter5th.example13th;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-22 22:17:42
 */
@SuppressWarnings("all")
public class WordCount {

    public static Map<String, Long> countWords(Stream<String> names) {
        return names.collect(Collectors.groupingBy(name -> name, Collectors.counting()));
    }

}
