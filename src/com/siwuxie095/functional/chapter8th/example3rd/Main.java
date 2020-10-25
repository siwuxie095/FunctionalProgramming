package com.siwuxie095.functional.chapter8th.example3rd;

/**
 * @author Jiajing Li
 * @date 2020-10-25 16:25:14
 */
public class Main {

    /**
     * 命令者模式
     *
     * 命令者是一个对象，它封装了调用另一个方法的所有细节，命令者模式使用该对象，可以编写出根据运行期条件，
     * 顺序调用方法的一般化代码。
     *
     * PS：命令者模式，也称 命令模式。
     *
     * 命令者模式中有四个类参与其中，如下：
     *
     * （1）命令接收者：执行实际任务。
     * （2）命令者：封装了所有调用命令执行者的信息。
     * （3）发起者：控制一个或多个命令的顺序和执行。
     * （4）客户端：创建具体的命令者实例。
     *
     * 看一个命令者模式的具体例子，是如何使用 Lambda 表达式改进该模式的。假设有一个 GUI Editor 组件，在
     * 上面可以执行 open、save 等一系列操作。现在想实现宏功能，也就是说，可以将一系列操作录制下来，日后作
     * 为一个操作执行，这就是命令接收者。Editor 接口定义如下：
     *
     * public interface Editor {
     *
     *     public void save();
     *
     *     public void open();
     *
     *     public void close();
     *
     * }
     *
     * 在该例子中，像 open、save 这样的操作称为命令，所以需要一个统一的接口来概括这些不同的操作，这个接口
     * 叫作 Action，它代表了一个操作。所有的命令都要实现该接口，Action 接口定义如下：
     *
     * public interface Action {
     *
     *     public void perform();
     *
     * }
     *
     * 现在让每个操作都实现该接口，这些类要做的只是在 Action 接口中调用 Editor 类中的一个方法。这里将遵循
     * 恰当的命名规范，用类名代表操作，比如 save 方法对应 Save 类。
     *
     * 每个操作对应的类参见 Save、Open、Close 类。
     *
     * 现在可以实现 Macro 类了，该类录制（record）操作，然后一起运行（run）。这里使用 List 保存操作序列，
     * 然后调用 forEach 方法按顺序执行每一个 Action，这就是命令发起者。
     *
     * 在构建宏时，将每一个命令实例加入 Macro 对象的列表，然后运行宏，就会按顺序执行每一条命令。这样，就将
     * 通用的工作流定义成宏，提高了工作效率。
     *
     * 使用普通的类来构建宏，如下：
     *
     *         Macro macro = new Macro();
     *         macro.record(new Open(editor));
     *         macro.record(new Save(editor));
     *         macro.record(new Close(editor));
     *         macro.run();
     *
     * Lambda 表达式能做点什么呢？事实上，所有的命令类，Save、Open 都是 Lambda 表达式，只是暂时藏在类的
     * 外壳下。它们是一些行为，上面的代码是通过创建类将它们在对象之间传递。而 Lambda 表达式能让这个模式变
     * 得非常简单，就可以扔掉这些类。
     *
     * 使用 Lambda 表达式构建宏，如下：
     *
     *         Macro macro = new Macro();
     *         macro.record(() -> editor.open());
     *         macro.record(() -> editor.save());
     *         macro.record(() -> editor.close());
     *         macro.run();
     *
     * 事实上 ，如果意识到这些 Lambda 表达式的作用只是调用了一个方法，还能让问题变得更简单。可以使用方法
     * 引用将命令和宏对象关联起来。
     *
     * 使用方法引用构建宏，如下：
     *
     *         Macro macro = new Macro();
     *         macro.record(editor::open);
     *         macro.record(editor::save);
     *         macro.record(editor::close);
     *         macro.run();
     *
     * 命令者模式只是一个可怜的程序员使用 Lambda 表达式的起点。使用 Lambda 表达式或是方法引用，能让代码
     * 更简洁，去除了大量样板代码，让代码意图更加明显。
     *
     * 宏只是使用命令者模式的一个例子，事实上，它被大量用在实现组件化的图形界面系统、撤销功能、线程池、事务
     * 和向导中。
     *
     * PS：在核心 Java 中，已经有一个和 Action 接口结构一致的函数式接口 Runnable。 可以在实现上述宏程序
     * 的过程中直接使用该接口，但在这个例子中，似乎 Action 是一个更符合待解问题的词汇，因此创建了自己的接口。
     */
    public static void main(String[] args) {

    }

}
