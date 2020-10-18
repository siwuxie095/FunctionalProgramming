package com.siwuxie095.functional.chapter4th.example5th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 10:10:36
 */
@SuppressWarnings("all")
public class Main {

    /**
     * @FunctionalInterface 注解
     *
     * 函数式接口是只有一个抽象方法的接口，用作 Lambda 表达式的类型。每个用作函数式接口的接口都应该添加
     * @FunctionalInterface 注解。
     *
     * PS：只要接口中只有一个抽象方法，就可以认为是函数式接口，@FunctionalInterface 注解仅用于标识，
     * 可加可不加。
     *
     * 这究竟是什么意思呢？Java 中有一些接口，虽然只含一个方法，但并不是为了使用 Lambda 表达式来实现的。
     * 比如，有些对象内部可能保存着某种状态，使用带有一个方法的接口可能纯属巧合。java.lang.Comparable
     * 和 java.io.Closeable 就属于这样的情况。
     *
     * 先看 Comparable，如果一个类是可比较的，就意味着在该类的实例之间存在某种顺序，比如字符串中的字母
     * 顺序。人们通常不会认为函数是可比较的，如果一个东西既没有属性也没有状态，拿什么比较呢？
     *
     * 再看 Closeable，一个可关闭的对象必须持有某种打开的资源，比如一个需要关闭的文件句柄。同样，该接口
     * 也不能是一个纯函数，因为关闭资源是更改状态的另一种形式。
     *
     * 和 Closeable 和 Comparable 接口不同，为了提高 Stream 对象可操作性而引入的各种新接口，都需要有
     * Lambda 表达式可以实现它。它们存在的意义在于将代码块作为数据打包起来。
     *
     * PS：这里的新接口指的就是函数式接口。
     *
     * 因此，这些新接口都添加了 @FunctionalInterface 注解。该注解会强制 javac 检查一个接口是否符合函
     * 数式接口的标准。如果该注解添加给一个枚举类型、类或另一个注解，或者接口包含不止一个抽象方法，javac
     * 就会报错。重构代码时，使用它能很容易发现问题。
     */
    public static void main(String[] args) {

    }

}
