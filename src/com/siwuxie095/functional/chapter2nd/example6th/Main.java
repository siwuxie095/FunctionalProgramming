package com.siwuxie095.functional.chapter2nd.example6th;

import java.util.function.Predicate;

/**
 * @author Jiajing Li
 * @date 2020-10-10 20:19:18
 */
public class Main {

    /**
     * 要点回顾与巩固练习
     *
     * 1、要点回顾
     *
     * （1）Lambda 表达式是一个匿名方法，将行为像数据一样进行传递。
     * （2）函数式接口指仅具有单个抽象方法的接口，用来表示 Lambda 表达式的类型。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）ThreadLocal Lambda 表达式。Java 有一个 ThreadLocal 类，作为容器保存了当前线程里局部变量
     * 的值。Java 8 为该类新加了一个工厂方法，接受一个 Lambda 表达式，并产生一个新的 ThreadLocal 对象，
     * 而不用使用继承，语法上更加简洁。
     *
     *
     * 问：
     * 在 Javadoc 或集成开发环境(IDE)里找出该方法。
     * 答：
     * 该工厂方法是 ThreadLocal.withInitial 方法。
     *
     *
     * 问：
     * DateFormatter 类是非线程安全的。使用构造函数创建一个线程安全的 DateFormatter 对象，并输出日期，
     * 如 "01-Jan-1970"。
     * 答：
     * public final static ThreadLocal<DateFormatter> formatter
     *         = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));
     *
     *
     * （2）类型推断规则。下面是将 Lambda 表达式作为参数传递给函数的一些例子。javac 能正确推断出 Lambda
     * 表达式中参数的类型吗？换句话说，程序能编译吗？
     *
     * 问：
     * 以如下方式重载 check 方法后，还能正确推断出 check(x -> x > 5) 的类型吗？
     *
     * interface IntPred {
     * boolean test(Integer value);
     * }
     * boolean check(Predicate<Integer> predicate);
     * boolean check(IntPred predicate);
     *
     * 答：不能。javac 不能推断 x -> x > 5 到底该用 Predicate<Integer> 还是 IntPred。
     *
     * PS：建议不要使用函数式接口进行重载。
     */
    public static void main(String[] args) {
        //check(x -> x > 5);
    }

    interface IntPred {
        boolean test(Integer value);
    }

    private static void check(IntPred predicate) {
    }

    private static void check(Predicate<Integer> predicate) {
    }

}
