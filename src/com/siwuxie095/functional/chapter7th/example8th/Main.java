package com.siwuxie095.functional.chapter7th.example8th;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiajing Li
 * @date 2020-10-25 15:42:19
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 在流中间设置断点
     *
     * 记录日志这是 peek 方法的用途之一。为了像调试循环那样一步一步跟踪，可在 peek 方法中加入断点，这样就能逐个
     * 调试流中的元素了。
     *
     * 显然，peek 方法是可以只包含一个空的方法体的，但有一些调试器不允许在空的方法体中设置断点，此时，将值简单地
     * 消费一下（比如调用 toString 或者打印出来），这样就有地方设置断点了，虽然这样做不够完美，但只要能工作就行。
     */
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B", "C");
        list.stream()
                .map(x -> x.toLowerCase())
                .peek(x -> {
                    x.toString();
                })
                .collect(Collectors.toList());

    }

}
