package com.siwuxie095.functional.chapter4th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:05:38
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 默认方法
     *
     * Collection 接口中增加了新的 stream 方法，如何能让其子类在不知道该方法的情况下通过编译？Java 8
     * 通过如下方法解决该问题：
     *
     * Collection接口告诉它所有的子类："如果你没有实现 stream 方法，就使用我的吧"。接口中这样的方法叫
     * 作默认方法，在任何接口中，无论函数式接口还是非函数式接口，都可以使用该方法。
     *
     * PS：子类可能是在 Java 8 之前的版本实现的，为了能够在 Java 8 中编译通过，所以增加了默认方法。
     *
     * Iterable 接口中也新增了一个默认方法：forEach，该方法功能和 for 循环类似，但是允许程序员使用一
     * 个 Lambda 表达式作为循环体。
     *
     * 如下是 forEach 的实现方式：
     *
     *     default void forEach(Consumer<? super T> action) {
     *         Objects.requireNonNull(action);
     *         for (T t : this) {
     *             action.accept(t);
     *         }
     *     }
     *
     * 如果已经习惯了通过调用接口方法来使用 Lambda 表达式的方式，那么这段代码理解起来就相当简单。它使用
     * 一个常规的 for 循环遍历 Iterable 对象，然后对每个值调用 accept 方法。
     *
     * 既然如此简单，为何还要单独提出来呢？重点就在于代码段前面的新关键字 default。这个关键字告诉 javac
     * 真正需要的是为接口添加一个新方法。
     *
     * 除了添加了一个新的关键字，默认方法在继承规则上和普通方法也略有区别。和类不同，接口没有成员变量，因
     * 此默认方法只能通过调用子类的方法来修改子类本身，避免了对子类的实现做出各种假设（即 各种 if 判断）。
     *
     *
     *
     * 默认方法和子类
     *
     * 默认方法的重写规则也有一些微妙之处。从最简单的情况开始来看：没有重写。
     *
     * 以 Parent、ParentImpl 类为例，Parent 接口定义了一个默认方法 welcome，调用该方法时，发送一条
     * 信息。ParentImpl 类没有实现 welcome 方法，因此它自然继承了该默认方法。如下：
     *
     * public interface Parent {
     *
     *     void message(String body);
     *
     *     default void welcome() {
     *         message("Parent: Hi!");
     *     }
     *
     *     String getLastMessage();
     *
     * }
     *
     *
     * public class ParentImpl implements Parent {
     *
     *     private String body;
     *
     *     @Override
     *     public void message(String body) {
     *         this.body = body;
     *     }
     *
     *     @Override
     *     public String getLastMessage() {
     *         return body;
     *     }
     * }
     *
     * 然后调用默认方法，如下：
     *
     *     private static void parentDefaultUsed() {
     *         Parent parent = new ParentImpl();
     *         parent.welcome();
     *         Assert.assertEquals("Parent: Hi!", parent.getLastMessage());
     *     }
     *
     * 运行代码，可以看到断言正确。最终调用的是 Parent 的 welcome 方法。
     *
     * 然后再新建一个接口 Child，继承自 Parent 接口。Child 接口实现了自己的默认 welcome 方法，凭直觉
     * 判断可知，该方法重写了 Parent 的方法。同样的，ChildImpl 类不会实现 welcome 方法，因此它自然也
     * 继承了接口的默认方法。如下：
     *
     * public interface Child extends Parent {
     *
     *     @Override
     *     default void welcome() {
     *         message("Child: Hi!");
     *     }
     * }
     *
     *
     * public class ChildImpl extends ParentImpl implements Child {
     *
     * }
     *
     * 然后调用默认方法，如下：
     *
     *     private static void childOverrideDefault() {
     *         Child child = new ChildImpl();
     *         child.welcome();
     *         Assert.assertEquals("Child: Hi!", child.getLastMessage());
     *     }
     *
     * 运行代码，可以看到断言正确。最终调用的是 Child 而不是 Parent 的 welcome 方法。
     *
     * 然后重写 welcome 默认实现的父类，如下：
     *
     * public class OverridingParent extends ParentImpl {
     *
     *     @Override
     *     public void welcome() {
     *         message("OverridingParent: Hi!");
     *     }
     * }
     *
     * 然后调用默认方法，如下：
     *
     *     private static void concreteBeatsDefault() {
     *         Parent parent = new OverridingParent();
     *         parent.welcome();
     *         Assert.assertEquals("OverridingParent: Hi!", parent.getLastMessage());
     *     }
     *
     * 运行代码，可以看到断言正确。最终调用的是 OverridingParent 而不是 Parent 的 welcome 方法。即
     * 调用的是类中的具体方法，而不是默认方法。
     *
     * 这说明任何时候，一旦与类中定义的方法产生冲突，都要优先选择类中定义的方法。
     *
     * 然后再来看另一种情况，子类重写了父接口中的默认方法，如下：
     *
     * public class OverridingChild extends OverridingParent implements Child {
     *
     * }
     *
     * 可以看到，OverridingChild 本身并没有任何操作，只是继承了 Child 和 OverridingParent 中的
     * welcome 方法。
     *
     * 然后调用默认方法，如下：
     *
     *     private static void concreteBeatsCloserDefault() {
     *         Child child = new OverridingChild();
     *         child.welcome();
     *         Assert.assertEquals("OverridingParent: Hi!", child.getLastMessage());
     *     }
     *
     * 运行代码，可以看到断言正确。最终调用的是 OverridingParent 而不是 Child 的 welcome 方法。原因
     * 在于，与接口中定义的默认方法相比，类中重写的方法更具体。
     *
     * 也就是说，类中重写的方法优先级高于接口中定义的默认方法。
     *
     * 简言之，类中重写的方法胜出。这样的设计主要是由增加默认方法的目的所决定的，增加默认方法主要是为了在
     * 接口上向后兼容。让类中重写方法的优先级高于默认方法能简化很多继承问题。
     *
     * 假设已实现了一个定制的列表 MyCustomList（实现自 List 接口），该类中有一个 addAll 方法。如果新
     * 的 List 接口也增加了一个默认方法 addAll，且该方法将对列表的操作代理到 add 方法。如果类中重写的
     * 方法没有默认方法的优先级高，那么就会破坏已有的实现。
     */
    public static void main(String[] args) {

    }

}
