package com.siwuxie095.functional.chapter2nd.example1st;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Jiajing Li
 * @date 2020-10-10 05:08:28
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 第一个 Lambda 表达式
     *
     * Java 8 最大的变化就是引入了Lambda表达式，一种紧凑的、传递行为的方式。下面就来了解一下什么是 Lambda 表达式。
     *
     * Swing 是一个与平台无关的 Java 类库，用来编写图形用户界面(GUI)。该类库有一个常见用法：为了响应用户操作，需要
     * 注册一个事件监听器。用户一输入，监听器就会执行一些操作。如下是使用匿名内部类将行为和按钮单击进行关联：
     *
     *         button.addActionListener(new AbstractAction() {
     *             @Override
     *             public void actionPerformed(ActionEvent event) {
     *                 System.out.println("button clicked");
     *             }
     *         });
     *
     * 在这个例子中，创建了一个新对象，它实现了 ActionListener 接口。这个接口只有一个方法 actionPerformed，当用
     * 户点击屏幕上的按钮时，button 就会调用这个方法。匿名内部类实现了该方法。这里执行的只是输出一条信息，表明按钮
     * 已被点击。
     *
     * PS：这实际上是一个代码即数据的例子。这里给按钮传递了一个代表某种行为的对象。（代码即数据，是 Lisp 语言中的概
     * 念，参考链接：https://zh.wikipedia.org/wiki/%E5%90%8C%E5%83%8F%E6%80%A7。这里的代码其实就是指行为，
     * 数据本来是通过函数参数来传递的，结果这里的函数参数是行为，所以说代码即数据。也可以理解为将语言中的所有代码当成
     * 数据，来存取以及转换）
     *
     * 设计匿名内部类的目的，就是为了方便 Java 程序员将代码作为数据传递。不过，匿名内部类还是不够简便。为了调用一行
     * 重要的逻辑代码，不得不加上 4 行冗繁的样板代码。而且这些代码还相当难读，因为它没有清楚地表达程序员的意图。这里
     * 并不想传入对象，只想传入行为。
     *
     * 在 Java 8 中，上述代码可以写成一个 Lambda 表达式，如下是使用 Lambda 表达式将行为和按钮单击进行关联：
     *
     *         button.addActionListener(event -> System.out.println("button clicked"));
     *
     * 和传入一个实现某接口的对象不同，这里传入了一段代码块，即 一个没有名字的函数。event 是参数名，和上面匿名内部类
     * 示例中的是同一个参数。-> 将参数和 Lambda 表达式的主体分开，而主体是用户点击按钮时会运行的一些代码。
     *
     * 和使用匿名内部类的另一处不同在于声明参数 event 的方式：
     * 使用匿名内部类时需要显式地声明参数类型 ActionEvent event，而在 Lambda 表达式中无需指定类型，程序依然可以
     * 编译。这是因为 javac 根据程序的上下文（addActionListener 方法的签名）在后台推断出了参数 event 的类型。这
     * 意味着如果参数类型不言而明，则无需显式指定。
     *
     * PS：尽管与之前相比，Lambda 表达式中的参数需要的样板代码很少，但是 Java 8 仍然是一种静态类型语言。为了增加可
     * 读性并迁就已有的习惯，声明参数时也可以包括类型信息，而且有时编译器不一定能根据上下文推断出参数的类型。
     */
    public static void main(String[] args) {
        clickByAnonymous();
        clickByLambda();
    }

    private static void clickByAnonymous() {
        JButton button = new JButton();
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("button clicked");
            }
        });
    }

    private static void clickByLambda() {
        JButton button = new JButton();
        button.addActionListener(event -> System.out.println("button clicked"));
    }

}
