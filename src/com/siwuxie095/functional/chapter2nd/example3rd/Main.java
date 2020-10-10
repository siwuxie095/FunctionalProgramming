package com.siwuxie095.functional.chapter2nd.example3rd;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Jiajing Li
 * @date 2020-10-10 07:44:32
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 引用值，而不是变量
     *
     * 如果使用过匿名内部类，也许遇到过这样的情况：需要引用它所在方法里的变量。这时，需要将变量声明为 final。如下是匿名内部类
     * 中使用 final 局部变量：
     *
     *         final String name = getUserName();
     *         button.addActionListener(new AbstractAction() {
     *             @Override
     *             public void actionPerformed(ActionEvent event) {
     *                 System.out.println("hi " + name);
     *             }
     *         });
     *
     * 将变量声明为 final，意味着不能为其重复赋值。同时也意味着在使用 final 变量时，实际上是在使用赋给该变量的一个特定的值。
     *
     * Java 8 虽然放松了这一限制，可以引用非 final 变量（即 上面的 final 可省略），但是该变量在既成事实上必须是 final。
     * 虽然无需将变量声明为 final，但在 Lambda 表达式中，也无法用作非终态变量。如果坚持用作非终态变量，编译器就会报错。
     *
     * 既成事实上的 final 是指只能给该变量赋值一次。换句话说，Lambda 表达式引用的是值，而不是变量。如下是 Lambda 表达式中
     * 引用既成事实上的 final 变量：
     *
     *         String name = getUserName();
     *         button.addActionListener(event -> System.out.println("hi " + name));
     *
     * 其中，name 就是一个既成事实上的 final 变量。
     *
     * final 就像代码中的线路噪声，省去之后代码更易读。当然，有些情况下，显式地使用 final 代码更易懂。是否使用这种既成事实
     * 上的 final 变量，完全取决于个人喜好。
     *
     * 如果试图给该变量多次赋值，然后在 Lambda 表达式中引用它，编译器就会报错。如下是未使用既成事实上的 final 变量，导致无
     * 法通过编译：
     *
     *         String name = getUserName();
     *         name = formatUserName(name);
     *         button.addActionListener(event -> System.out.println("hi " + name));
     *
     * 显然这是无法通过编译的，报错信息如下：
     *
     * Variable used in lambda expression should be final or effectively final
     *
     * 这种行为也解释了为什么 Lambda 表达式也被称为闭包。未赋值的变量与周边环境隔离起来，进而被绑定到一个特定的值。在众说纷纭
     * 的计算机编程语言圈子里，Java 是否拥有真正的闭包一直备受争议，因为在 Java 中只能引用既成事实上的 final 变量。名字虽异，
     * 功能相同，就好比把菠萝叫作凤梨，其实都是同一种水果。为了避免无意义的争论，这里使用 "Lambda 表达式" 一词。无论名字如何，
     * Lambda 表达式都是静态类型的。
     *
     * PS：Lambda 表达式中引用的局部变量必须是 final 或既成事实上的 final 变量。
     */
    public static void main(String[] args) {

    }

    private static void clickByAnonymous() {
        JButton button = new JButton();
        // final 可省略
        final String name = getUserName();
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("hi " + name);
            }
        });
    }

    private static String getUserName() {
        return "Jack";
    }

    private static String formatUserName(String name) {
        return name.toUpperCase();
    }

    private static void clickByLambda() {
        JButton button = new JButton();
        // final 被省略
        String name = getUserName();
        button.addActionListener(event -> System.out.println("hi " + name));
    }

}
