package com.siwuxie095.functional.chapter4th.example4th;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * @author Jiajing Li
 * @date 2020-10-17 22:30:49
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 重载解析
     *
     * 在 Java 中可以重载方法，造成多个方法有相同的方法名，但签名确不一样。这在推断参数类型时会带来问题，因为系统
     * 可能会推断出多种类型。这时，javac 会挑出最具体的类型。
     *
     * 在如下代码中，方法调用 overloadedMethod("abc") 在选择重载方法时，会输出 String，而不是 Object。
     *
     *     private static void overloadedMethod(Object obj) {
     *         System.out.println("Object");
     *     }
     *
     *     private static void overloadedMethod(String str) {
     *         System.out.println("String");
     *     }
     *
     * BinaryOperator 是一种特殊的 BiFunction 类型，参数的类型和返回值的类型相同。比如，两个整数相加就是一个
     * BinaryOperator。
     *
     * Lambda 表达式的类型就是对应的函数式接口类型，因此，将 Lambda 表达式作为参数传递时，情况也依然如此。操作时
     * 可以重载一个方法，分别接受 BinaryOperator 和该接口的一个子类作为参数。那么调用这些方法时，Java 推导出的
     * Lambda 表达式的类型正是最具体的函数式接口的类型。
     *
     * 比如，可以写一个 BinaryOperator 的子类 IntegerBinaryOperator（仍然是接口）。如下：
     *
     *     public interface IntegerBinaryOperator extends BinaryOperator<Integer> {
     *     }
     *
     * 在如下代码中，方法调用 overloadedMethod((x, y) -> x + y) 最终输出的是 IntegerBinaryOperator。
     *
     *     private static void overloadedMethod(BinaryOperator<Integer> lambda) {
     *         System.out.println("BinaryOperator");
     *     }
     *
     *     private static void overloadedMethod(IntegerBinaryOperator lambda) {
     *         System.out.println("IntegerBinaryOperator");
     *     }
     *
     * 当然，同时存在多个重载方法时，哪个是 "最具体的类型" 可能并不明确。
     *
     * 比如，实现一个 IntegerPredicate，那么此时方法调用 overloadedMethod((x) -> true) 就会显示编译错误，
     * 如下：
     *
     *     public interface IntegerPredicate {
     *         public boolean test(int value);
     *     }
     *
     *     private static void overloadedMethod(Predicate<Integer> predicate) {
     *         System.out.println("Predicate");
     *     }
     *     private static void overloadedMethod(IntegerPredicate predicate) {
     *         System.out.println("IntegerPredicate");
     *     }
     *
     * 传入 overloadedMethod 方法的 Lambda 表达式和两个函数式接口 Predicate、IntegerPredicate 在类型上都
     * 是匹配的。在这段代码块中，两种情况都定义了相应的重载方法，这时，javac 就无法编译，在错误报告中显示 Lambda
     * 表达式被模糊调用。IntegerPredicate 没有继承 Predicate，因此编译器无法推断出哪个类型更具体。
     *
     * 编译报错为：
     *
     * Ambiguous method call. Both
     * overloadedMethod (Predicate<Integer>) in Main and
     * overloadedMethod (IntegerPredicate) in Main match
     *
     * 将 Lambda 表达式强制转换为 IntegerPredicate 或 Predicate<Integer> 类型可以解决这个问题，至于转换为哪
     * 种类型则取决于要调用哪个函数式接口。当然，如果以前曾自行设计过类库，就可以将其视为 "代码异味"，不应该再重载，
     * 而应该开始重新命名重载方法。
     *
     * 总而言之，Lambda 表达式作为参数时，其类型由它的目标类型推导得出，推导过程遵循如下规则：
     * （1）如果只有一个可能的目标类型，由相应函数式接口里的参数类型推导得出；
     * （2）如果有多个可能的目标类型，由最具体的类型推导得出；
     * （3）如果有多个可能的目标类型且最具体的类型不明确，则需人为指定类型。
     */
    public static void main(String[] args) {
        overloadedMethod(123);
        overloadedMethod("abc");
        overloadedMethod((x, y) -> x + y);
        overloadedMethod((IntegerPredicate) (x) -> true);
    }

    private static void overloadedMethod(Object obj) {
        System.out.println("Object");
    }

    private static void overloadedMethod(String str) {
        System.out.println("String");
    }

    /**
     * 只要接口中保证只有一个抽象方法，即是函数式接口，加不加 @FunctionalInterface 注解均可。
     * 
     * 这里不加
     */
    public interface IntegerBinaryOperator extends BinaryOperator<Integer> {
    }

    private static void overloadedMethod(BinaryOperator<Integer> lambda) {
        System.out.println("BinaryOperator");
    }

    private static void overloadedMethod(IntegerBinaryOperator lambda) {
        System.out.println("IntegerBinaryOperator");
    }

    
    /**
     * 只要接口中保证只有一个抽象方法，即是函数式接口，加不加 @FunctionalInterface 注解均可。
     *
     * 这里不加
     */
    public interface IntegerPredicate { 
        public boolean test(Integer value);
    }

    private static void overloadedMethod(Predicate<Integer> predicate) {
        System.out.println("Predicate");
    }
    private static void overloadedMethod(IntegerPredicate predicate) {
        System.out.println("IntegerPredicate");
    }

}
