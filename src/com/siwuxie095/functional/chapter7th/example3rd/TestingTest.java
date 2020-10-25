package com.siwuxie095.functional.chapter7th.example3rd;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-25 11:44:47
 */
@SuppressWarnings("all")
public class TestingTest {

    /**
     * 测试大写转换
     */
    @Test
    public void multipleWordsToUppercase() {
        List<String> input = Arrays.asList("a", "b", "hello");
        List<String> result = Testing.allToUpperCase(input);
        Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), result);
    }

    /**
     * 测试字符串包含两个字符的情况，第一个字母被转换为大写
     */
    @Test
    public void twoLetterStringConvertedToUppercaseLambdas() {
        List<String> input = Arrays.asList("ab");
        List<String> result = Testing.elementFirstToUpperCaseLambdas(input);
        Assert.assertEquals(Arrays.asList("Ab"), result);
    }

    /**
     * 测试单独的方法
     */
    @Test
    public void twoLetterStringConvertedToUppercase() {
        String input = "ab";
        String result = Testing.firstToUppercase(input);
        Assert.assertEquals("Ab", result);
    }

}
