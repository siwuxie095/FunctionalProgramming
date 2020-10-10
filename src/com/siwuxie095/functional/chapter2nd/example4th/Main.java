package com.siwuxie095.functional.chapter2nd.example4th;

/**
 * @author Jiajing Li
 * @date 2020-10-10 08:19:12
 */
public class Main {

    /**
     * 函数式接口
     *
     * 函数式接口是只有一个抽象方法的接口，用作 Lambda 表达式的类型。
     *
     * PS：函数式接口，也称为函数接口。
     *
     * 在 Java 里，所有方法参数都有固定的类型。假设将数字 3 作为参数传给一个方法，则参数的类型是 int。那么，Lambda 表达式
     * 的类型又是什么呢?
     *
     * 使用只有一个方法的接口来表示某特定方法并反复使用，是很早就有的习惯。Lambda 表达式也使用同样的技巧，并将这种接口称为
     * 函数式接口。如下是通过 Swing 编写用户界面时经常使用的函数式接口。
     *
     * public interface ActionListener extends EventListener {
     *
     *     public void actionPerformed(ActionEvent e);
     *
     * }
     *
     * ActionListener 只有一个抽象方法：actionPerformed，被用来表示行为：接受一个参数，返回空。由于 actionPerformed
     * 定义在一个接口里，因此 abstract 关键字不是必需的。另外，该接口也继承自一个不具有任何方法的父接口：EventListener。
     *
     * 这就是函数式接口，接口中单一方法的命名并不重要，只要方法签名和 Lambda 表达式的类型匹配即可。可在函数式接口中为参数起
     * 一个有意义的名字，增加代码易读性，便于更透彻地理解参数的用途。
     *
     * ActionListener 函数式接口接受一个 ActionEvent 类型的参数，返回空(void)，但函数式接口还可以有其他形式。例如，函数
     * 式接口可以接受两个参数，并返回一个值，还可以使用泛型，这完全取决于你要干什么。
     *
     * 使用 Java 编程，总会遇到很多函数式接口。在 Java 开发工具包(JDK)中已经提供了一组核心函数式接口，会被频繁地使用，如下：
     *
     * 接口                   参数              返回类型
     * Predicate<T>           T                 boolean
     * Consumer<T>            T                 void
     * Function<T,R>          T                 R
     * Supplier<T>           None               T
     * UnaryOperator<T>       T                 T
     * BinaryOperator<T>     (T, T)             T
     *
     * 注意：javac 可以根据上下文自动推断出参数类型和返回类型。当然，也可以手动声明参数类型。
     */
    public static void main(String[] args) {

    }

}
