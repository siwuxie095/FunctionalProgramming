package com.siwuxie095.functional.chapter4th.example8th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 18:31:34
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 多继承
     *
     * 接口允许多继承，因此有可能碰到两个接口包含签名相同的默认方法的情况。
     *
     * PS：接口的多继承，也可以称为多实现。
     *
     * 以接口 Carriage 和 Jukebox 为例，二者都有一个默认方法 rock，虽然各有各的用途。而类 MusicalCarriage
     * 同时实现了接口 Carriage 和 Jukebox，那么它到底继承了哪个接口的 rock 方法呢？
     *
     * public interface Carriage {
     *
     *     default String rock() {
     *         return "... from side to side!";
     *     }
     * }
     *
     *
     * public interface Jukebox {
     *
     *     default String rock() {
     *         return "... all over the world!";
     *     }
     * }
     *
     *
     * public class MusicalCarriage implements Carriage, Jukebox {
     * }
     *
     * 此时，javac 并不明确应该继承哪个接口中的方法，因此编译器会报错，如下：
     *
     * MusicalCarriage inherits unrelated defaults for rock() from types Carriage and Jukebox
     *
     * 当然，在类中实现 rock 方法就能解决这个问题。如下：
     *
     * public class MusicalCarriage implements Carriage, Jukebox {
     *
     *     @Override
     *     public String rock() {
     *         return Carriage.super.rock();
     *     }
     * }
     *
     * 这里使用了增强的 super 语法，用来指明使用接口 Carriage 中定义的默认方法。此前使用 super 关键字是指向
     * 父类，现在使用类似 InterfaceName.super 这样的语法指的是继承自父接口的方法。
     *
     *
     *
     * 三定律
     *
     * 如果对默认方法的工作原理，特别是在多继承下的行为还没有把握，如下三条简单的定律可以帮助大家。
     * （1）类胜于接口。如果在继承链中有方法体或抽象的方法声明，那么就可以忽略接口中定义的方法。
     * （2）子类胜于父类。如果一个接口继承了另一个接口，且两个接口都定义了一个默认方法，那么子类中定义的方法胜出。
     * （3）没有规则三。如果上面两条规则不适用，子类要么需要实现该方法，要么将该方法声明为抽象方法。
     *
     * 其中第一条规则是为了让代码向后兼容。
     */
    public static void main(String[] args) {

    }

}
