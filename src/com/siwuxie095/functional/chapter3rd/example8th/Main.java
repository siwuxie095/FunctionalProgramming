package com.siwuxie095.functional.chapter3rd.example8th;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-14 08:25:31
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 正确使用 Lambda 表达式
     *
     * 以能够输出一些信息的回调函数为例，如下：
     *
     *         button.addActionListener(event -> System.out.println("button clicked"));
     *
     * 回调函数是一个合法的 Lambda 表达式，但并不能真正帮助程序员写出更简单、更抽象的代码，因为它仍然在指挥计算机
     * 执行一个操作。虽然对清理掉样板代码很有帮助，但 Java 8 引入的 Lambda 表达式的作用远不止这些。
     *
     * 下面要介绍的概念能够帮助程序员写出更简单的代码，因为这些概念描述了数据上的操作，明确了要达成什么转化，而不是
     * 说明如何转化。这种方式写出的代码，潜在的缺陷更少，更直接地表达了程序员的意图。
     *
     * 第一个概念就是副作用（Side Effect）。
     *
     * 明确要达成什么转化，而不是说明如何转化的另外一层含义在于写出的函数没有副作用。这一点非常重要，这样只通过函数
     * 的返回值就能充分理解函数的全部作用。
     *
     * 第二个概念就是纯函数（Pure Function），即 无副作用的函数。
     *
     * 没有副作用的函数不会改变程序或外界的状态。而上面示例中的 Lambda 表达式是有副作用的，它向控制台输出了信息，即
     * 一个可观测到的副作用。那么下面的代码有没有副作用呢？
     *
     *     private ActionEvent lastEvent;
     *
     *     private void registerHandler() {
     *         button.addActionListener(event -> this.lastEvent = event);
     *     }
     *
     * 这里将参数 event 保存至成员变量 lastEvent。给变量赋值也是一种副作用，而且更难察觉。在程序的输出中可能很难
     * 直接观察到，但是它的确更改了程序的状态。
     *
     * Java 在这方面有局限性，例如下面这段代码，赋值给一个局部变量 localEvent：
     *
     *         ActionEvent localEvent = null;
     *         button.addActionListener(event -> localEvent = event);
     *
     * 这段代码试图将 event 赋给一个局部变量，它无法通过编译，但绝非编写错误。这实际上是语言的设计者有意为之，用以
     * 鼓励程序员使用 Lambda 表达式获取值而不是变量。获取值使程序员更容易写出没有副作用的代码。
     *
     * PS：普通成员变量在 Lambda 表达式中可以被赋值（不推荐），而普通局部变量在 Lambda 表达式中不可以被赋值。
     * （成员变量 即 类/对象的成员变量，局部变量 即 方法中的局部变量）
     *
     * 在 Lambda 表达式中使用局部变量，可以不使用 final 关键字，但局部变量在既成事实上必须是 final 的。无论何时，
     * 将 Lambda 表达式传给 Stream 上的高阶函数，都应该尽量避免副作用。唯一的例外是 forEach 方法，它是一个终结
     * 方法。
     */
    public static void main(String[] args) {

    }

    private JButton button = new JButton();

    private void clickByLambda() {
        button.addActionListener(event -> System.out.println("button clicked"));
    }


    private ActionEvent lastEvent;

    private void registerHandler() {
        button.addActionListener(event -> this.lastEvent = event);
    }

    private void compileError() {
        //ActionEvent localEvent = null;
        //button.addActionListener(event -> localEvent = event);
    }

}
