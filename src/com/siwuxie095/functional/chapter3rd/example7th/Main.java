package com.siwuxie095.functional.chapter3rd.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-14 08:14:26
 */
public class Main {

    /**
     * 高阶函数
     *
     * 高阶函数是指接受另外一个函数作为参数，或返回一个函数的函数。高阶函数不难辨认：看函数签名就够了。如果函数
     * 的参数列表里包含函数式接口，或该函数返回一个函数式接口，那么该函数就是高阶函数。
     *
     * 在函数式编程中，有许多可以称为高阶函数的操作。
     *
     * map 是一个高阶函数，因为它的 mapper 参数是一个函数。事实上，Stream 接口中几乎所有的函数都是高阶函数。
     *
     * 而 Stream 接口以外的，比如排序时使用的 comparing 函数，它接受一个函数作为参数，获取相应的值，同时返回
     * 一个 Comparator。Comparator 可能会被误认为是一个对象，但它有且只有一个抽象方法，所以实际上是一个函数
     * 式接口。
     *
     * 事实上，可以大胆断言，Comparator 实际上应该是个函数，但是那时的 Java 只有对象，因此才造出了一个类，或
     * 一个匿名类。成为对象实属巧合，函数式接口向正确的方向迈出了一步。
     */
    public static void main(String[] args) {

    }

}
