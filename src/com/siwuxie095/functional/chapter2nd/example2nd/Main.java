package com.siwuxie095.functional.chapter2nd.example2nd;

import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * @author Jiajing Li
 * @date 2020-10-10 07:34:16
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 如何辨别 Lambda 表达式
     *
     * Lambda 表达式除了基本的形式之外，还有几种变体，如下是编写 Lambda 表达式的不同形式：
     *
     * （1）
     *         Runnable noArguments = () -> System.out.println("Hello World");
     *
     * （2）
     *         ActionListener oneArgument = event -> System.out.println("button clicked");
     *
     * （3）
     *         Runnable multiStatement = () -> {
     *             System.out.print("Hello");
     *             System.out.println(" World");
     *         };
     *
     * （4）
     *         BinaryOperator<Long> add = (x, y) -> x + y;
     *
     * （5）
     *         BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
     *
     *
     * 解释如下：
     * （1）Lambda 表达式不包含参数，使用空括号 () 表示没有参数。该 Lambda 表达式 实现了 Runnable 接口，该接口也
     * 只有一个 run 方法，没有参数，且返回类型为 void。
     *
     * （2）Lambda 表达式包含且只包含一个参数，可省略参数的括号。
     *
     * （3）Lambda 表达式的主体不仅可以是一个表达式，而且也可以是一段代码块，使用大括号 ({})将代码块括起来。该代码块
     * 和普通方法遵循的规则别无二致，可以用返回或抛出异常来退出。只有一行代码的 Lambda 表达式也可使用大括号，用以明确
     * Lambda 表达式从何处开始、到哪里结束。
     *
     * （4）Lambda 表达式也可以表示包含多个参数的方法。这时就有必要思考怎样去阅读该 Lambda 表达式。这行代码并不是将
     * 两个数字相加，而是创建了一个函数，用来计算两个数字相加的结果。变量 add 的类型是 BinaryOperator<Long>，它不
     * 是两个数字的和，而是将两个数字相加的那行代码。
     *
     * （5）以上所有 Lambda 表达式中的参数类型都是由编译器推断得出的。这当然不错，但有时最好也可以显式声明参数类型，
     * 此时就需要使用小括号将参数括起来。单个参数、多个参数都是如此。
     *
     * PS：目标类型是指 Lambda 表达式所在上下文环境的类型。比如，将 Lambda 表达式赋值给一个局部变量，或者传递给一个
     * 方法作为参数，局部变量或方法参数的类型就是 Lambda 表达式的目标类型。
     *
     * 上述例子还隐含了另外一层意思：
     * Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。目标类型也不是一个全新的概念。如下：
     *
     *         final String[] array = { "hello", "world" };
     *
     * Java 中初始化数组时，数组的类型就是根据上下文推断出来的。
     * （等号右边的代码并没有声明类型，系统根据上下文推断出类型信息）
     *
     * 另一个常见的例子是 null，只有将 null 赋值给一个变量，才能知道它的类型。
     */
    public static void main(String[] args) {
        diffLambda();
        initStrArr();
    }

    private static void diffLambda() {
        // 1. 无参
        Runnable noArguments = () -> System.out.println("Hello World");
        // 2. 一个参数
        ActionListener oneArgument = event -> System.out.println("button clicked");
        // 3. 多个语句
        Runnable multiStatement = () -> {
            System.out.print("Hello");
            System.out.println(" World");
        };
        // 4. 多个参数
        BinaryOperator<Long> add = (x, y) -> x + y;
        // 5. 明确指定参数类型
        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
    }

    private static void initStrArr() {
        final String[] array = { "hello", "world" };
    }


}
