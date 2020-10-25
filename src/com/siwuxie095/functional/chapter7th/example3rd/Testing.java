package com.siwuxie095.functional.chapter7th.example3rd;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-25 11:39:58
 */
@SuppressWarnings("all")
public class Testing {

    /**
     * 将字符串转换为大写形式
     */
    public static List<String> allToUpperCase(List<String> words) {
        return words.stream()
                .map(string -> string.toUpperCase())
                .collect(Collectors.<String>toList());
    }

    /**
     * 将列表中元素的第一个字母转换成大写
     */
    public static List<String> elementFirstToUpperCaseLambdas(List<String> words) {
        return words.stream()
                .map(value -> {
                    char firstChar = Character.toUpperCase(value.charAt(0));
                    return firstChar + value.substring(1);
                })
                .collect(Collectors.<String>toList());
    }

    /**
     * 将首字母转换为大写，应用到所有列表元素
     */
    public static List<String> elementFirstToUppercase(List<String> words) {
        return words.stream()
                .map(Testing::firstToUppercase)
                .collect(Collectors.<String>toList());
    }

    public static String firstToUppercase(String value) {
        char firstChar = Character.toUpperCase(value.charAt(0));
        return firstChar + value.substring(1);
    }


}
