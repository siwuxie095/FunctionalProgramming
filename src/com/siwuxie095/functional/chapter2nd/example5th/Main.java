package com.siwuxie095.functional.chapter2nd.example5th;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * @author Jiajing Li
 * @date 2020-10-10 19:30:33
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 类型推断
     *
     * 某些情况下，用户需要手动指明类型，建议根据自己或项目组的习惯，采用让代码最便于阅读的方法。有时省略类型信息
     * 可以减少干扰，更易弄清状况；而有时却需要类型信息帮助理解代码。经验证发现，一开始类型信息是有用的，但随后可
     * 以只在真正需要时才加上类型信息。下面将介绍一些简单的规则，来帮助确认是否需要手动声明参数类型。
     *
     * Lambda 表达式中的类型推断，实际上是 Java 7 就引入的目标类型推断的扩展。众所周知，Java 7 中的菱形操作符，
     * 可使 javac 推断出泛型参数的类型。如下是使用菱形操作符，根据变量类型做推断：
     *
     *     private static void contrast() {
     *         Map<String, Integer> oldWordCounts = new HashMap<String, Integer>();
     *         Map<String, Integer> diamondWordCounts = new HashMap<>();
     *     }
     *
     * PS：菱形操作符，也叫做钻石操作符。
     *
     * 这里为变量 oldWordCounts 明确指定了泛型的类型，而变量 diamondWordCounts 则使用了菱形操作符。不用明确
     * 声明泛型类型，编译器就可以自己推断出来，这就是它的神奇之处。
     *
     * 当然，这并不是什么魔法，根据变量 diamondWordCounts 的类型可以推断出 HashMap 的泛型类型，但用户仍需要
     * 声明变量的泛型类型。
     *
     * 如果将构造函数直接传递给一个方法，也可根据方法签名来推断类型。如下是使用菱形操作符，根据方法签名做推断：
     *
     *     private static void construct() {
     *         useHashMap(new HashMap<>());
     *     }
     *
     *     private static void useHashMap(HashMap<String, String> map) {
     *     }
     *
     * 这里传入了 HashMap，根据方法签名已经可以推断出泛型的类型。
     *
     * Java 7 中程序员可省略构造函数的泛型类型，Java 8 更进一步，程序员可省略 Lambda 表达式中的所有参数类型。
     * 再强调一次，这并不是魔法，javac 根据 Lambda 表达式上下文信息就能推断出参数的正确类型。程序依然要经过类型
     * 检查来保证运行的安全性，但不用再显式声明类型罢了。这就是所谓的类型推断。
     *
     * 如下是使用 Lambda 表达式检测一个 Integer 是否大于 5。这实际上使用了 Predicate，一个用来判断真假的函数
     * 式接口：
     *
     *         Predicate<Integer> atLeast5 = x -> x > 5;
     *
     * Predicate 也是一个 Lambda 表达式，它返回一个值。其中，表达式 x > 5 是 Lambda 表达式的主体。这样的情况
     * 下，返回值就是 Lambda 表达式主体的值。
     *
     * Predicate 接口接受一个对象，返回一个布尔值，源码如下：
     *
     * @FunctionalInterface
     * public interface Predicate<T> {
     *
     *     boolean test(T t);
     *
     * }
     *
     * 不难看出，Predicate 只有一个泛型类型的参数，Integer 用于其中。Lambda 表达式实现了 Predicate 接口，因
     * 此它的单一参数被推断为 Integer 类型。javac 还可以检查 Lambda 表达式的返回值是不是 boolean，而这正是
     * Predicate 方法的返回类型。
     *
     * 如下示例使用了一个略显复杂的函数式接口：BinaryOperator。该接口接受两个参数，返回一个值，参数和值的类型均
     * 相同。示例中所用的类型是 Long：
     *
     *         BinaryOperator<Long> addLongs = (x, y) -> x + y;
     *
     * 类型推断系统相当智能，但若信息不够，类型推断系统也无能为力。类型系统不会漫无边 际地瞎猜，而会中止操作并报告
     * 编译错误，寻求帮助。
     *
     * 比如，如果删掉上例中的某些类型信息，就会得到如下代码：
     *
     * BinaryOperator add = (x, y) -> x + y;
     *
     * 编译器给出的报错信息如下：
     *
     * Operator '+' cannot be applied to 'java.lang.Object', 'java.lang.Object'
     *
     * 报错信息让人一头雾水，到底怎么回事？BinaryOperator 毕竟是一个具有泛型参数的函数式接口，该类型既是参数 x
     * 和 y 的类型，也是返回值的类型。而上例中并没有给出变量 add 的任何泛型信息，给出的正是原始类型的定义。因此，
     * 编译器认为参数和返回值都是 java.lang.Object 实例。
     */
    public static void main(String[] args) {
        contrast();
        construct();
        judge();
        addLongs();
        add();
    }

    private static void contrast() {
        Map<String, Integer> oldWordCounts = new HashMap<String, Integer>();
        Map<String, Integer> diamondWordCounts = new HashMap<>();
    }

    private static void construct() {
        useHashMap(new HashMap<>());
    }

    private static void useHashMap(HashMap<String, String> map) {
    }

    private static void judge() {
        Predicate<Integer> atLeast5 = x -> x > 5;
    }

    private static void addLongs() {
        BinaryOperator<Long> addLongs = (x, y) -> x + y;
    }

    private static void add() {
        //BinaryOperator add = (x, y) -> x + y;
    }

}
